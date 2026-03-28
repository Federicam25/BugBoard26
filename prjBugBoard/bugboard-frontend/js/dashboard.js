function logout(){
    localStorage.removeItem("token");
    window.location = "index.html";
}

function checkAuth(){
    const token = localStorage.getItem("token");
    if(!token){
        window.location = "index.html";
    }
}

async function loadIssues(){
    const issues = await getIssues();
    renderIssues(issues);
}

const filterForm = document.getElementById("filter-form");
if(filterForm){
    filterForm.addEventListener("submit", applyFilters);
}

/*
    const role = localStorage.getItem("role");
    if(role !== "ADMIN"){
        const adminForm = document.getElementById("create-user-panel");
        const adminFormStat = document.getElementById("global-stats-container");
        if(adminForm){
            adminForm.style.display = "none";
        }
        if(adminFormStat){
            adminFormStat.style.display = "none";
        }

    }
*/
async function applyFilters(event){
    event.preventDefault();
    const status = document.getElementById("status").value;
    const type = document.getElementById("type").value;
    const priority = document.getElementById("priority").value;
    const orderBy = document.getElementById("orderBy").value;
    let url = "http://localhost:8080/api/issues?";
    if(status) url += "status=" + status + "&";
    if(type) url += "type=" + type + "&";
    if(priority) url += "priority=" + priority + "&";
    if(orderBy) url += "orderBy=" + orderBy;

    const response = await fetchWithAuth(url);
    const issues = await response.json();
    renderIssues(issues);

}
function renderIssues(issues){
    const role = localStorage.getItem("role");
    const currentUser = localStorage.getItem("username");
    let html = "";
    issues.forEach(issue => {

        let isClickable = false;

        if(role === "ADMIN"){
            isClickable = true;
        } else if(issue.assignedTo === currentUser){
            isClickable = true;
        }

        if(isClickable){
            html += `<tr onclick="window.location='issueDetails.html?id=${issue.id}'" class="clickable-row">`;
        } else {
            html += `<tr class="disabled-row">`;
        }

        html += `<td>#${issue.id}</td>`;
        html += `<td>${issue.title}</td>`;
        html += `<td>${issue.description}</td>`;
        html += `<td>${issue.type}</td>`;
        html += `<td><span class="priority ${issue.priority}">${issue.priority}</span></td>`;
        html += `<td><span class="status ${issue.status}">${issue.status}</span></td>`;
        html += `<td>${issue.createdBy || ""}</td>`;
        html += `<td>${issue.assignedTo || ""}</td>`;
        html += `<td>${issue.createdAt}</td>`;
        html += `</tr>`;

    });
    document.getElementById("issues-table").innerHTML = html;
}

function resetFilters(){
    document.getElementById("filter-form").reset();
    loadIssues();
}
/*
window.addEventListener("focus", () => {
    loadIssues();
    loadStats();
});
*/

document.addEventListener("DOMContentLoaded", initDashboard);
async function initDashboard(){
    checkAuth();
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");
    document.getElementById("welcome-title").innerText =
                "Benvenuto " + username + " (" + role + ")";
    const adminPanel = document.getElementById("create-user-panel");
    const adminFormStat = document.getElementById("global-stats-container");

    if(adminPanel && role !== "ADMIN" && role !== "admin"){
        adminPanel.style.display = "none";
        adminFormStat.style.display = "none";

    }
    const filterForm = document.getElementById("filter-form");
    if(filterForm){
        filterForm.addEventListener("submit", applyFilters);
    }
    await loadStats();
    await loadIssues();

}

async function loadStats(){

    const stats = await getStats();

    // bug aperti
    document.getElementById("stat-open").innerText = stats.openIssues;

    // tempo medio (converti secondi → ore)
    document.getElementById("stat-avg").innerText =
        (stats.avgResolution / 3600).toFixed(2) + " h";

    // bug per utente
    let html = "";
    stats.byUser.forEach(u => {
        html += `<tr><td>${u.assignedTo}</td><td>${u.total}</td></tr>`;
    });
    document.getElementById("stats-users").innerHTML = html;

    // tempo per utente
    let html2 = "";
    stats.avgResolutionByUser.forEach(u => {
        html2 += `<tr><td>${u.assignedTo}</td><td>${(u.avg / 3600).toFixed(2)} h</td></tr>`;
    });
    document.getElementById("stats-time-users").innerHTML = html2;

}