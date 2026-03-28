document.addEventListener("DOMContentLoaded", initPage);

function checkAuth(){
    const token = localStorage.getItem("token");
    if(!token){
        window.location = "index.html";
    }
}

function initPage(){
    checkAuth();
    const role = localStorage.getItem("role");

    document
        .getElementById("issue-form")
        .addEventListener("submit", updateIssue);

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    const issue = loadIssue(id);

    if(role.toUpperCase() === "ADMIN"){
        loadUsers(issue.assignedTo);
        loadHistory(id);
    }

    if(role.toUpperCase() !== "ADMIN"){
        document.getElementById("history-container").style.display = "none";
        document.getElementById("assign-container").style.display = "none";
    }

}

async function loadIssue(id){
    const response = await fetchWithAuth(API_BASE + "/api/issues/" + id);
    const issue = await response.json();
    document.getElementById("id").value = issue.id;
    document.getElementById("title").value = issue.title;
    document.getElementById("description").value = issue.description;
    document.getElementById("type").value = issue.type;
    document.getElementById("priority").value = issue.priority;
    document.getElementById("status").value = issue.status;
    document.getElementById("assignedTo").value = issue.assignedTo;
    if (issue.type != "BUG"){
        document.getElementById("assign-container").style.display = "none";
        document.getElementById("history-container").style.display = "none";
    }
    return issue;
}

async function loadUsers(assignedTo){
    const response = await fetchWithAuth(API_BASE + "/api/users");
    const users = await response.json();
    const select = document.getElementById("assignedTo");
    const currentUser = localStorage.getItem("username");
    let html = `<option value="">Selezionare utente</option>`;
    users.forEach(u => {
        const username = u.username || u;
        // mostra tutti oppure filtra qui se vuoi
        // if(username !== currentUser || username === assignedTo)
        const selected = (username === assignedTo) ? "selected" : "";
        html += `<option value="${username}" ${selected}>${username}</option>`;
    });
    select.innerHTML = html;
}

async function loadHistory(id){
    const response = await fetchWithAuth(API_BASE + "/api/issues/" + id + "/history");
    const history = await response.json();
    let html = "";
    history.forEach(h => {
        html += "<tr>";
        html += "<td>"+h.changedAt+"</td>";
        html += "<td>"+h.changedBy+"</td>";
        html += "<td>"+h.fieldName+"</td>";
        /*if(h.oldValue != "undefined" && h.oldValue != null && h.oldValue != "")
            html += "<td>" + h.oldValue + "</td>";
        else
            html += "<td></td>";*/
        if(h.newValue != "undefined" && h.newValue != null && h.newValue != "")
            html += "<td>" + h.newValue + "</td>";
        else
            html += "<td></td>";
        html += "</tr>";
    });
    document.getElementById("history-table").innerHTML = html;
}

async function updateIssue(event){
    event.preventDefault();
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    console.log("UPDATE ISSUE ID:", id); // debug
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const type = document.getElementById("type").value;
    const priority = document.getElementById("priority").value;
    const status = document.getElementById("status").value;
    const assignedTo = document.getElementById("assignedTo").value;
    const commento = document.getElementById("commento").value;
    console.log("assignedTo =", assignedTo);
    await fetchWithAuth(API_BASE + "/api/issues/" + id, {
        method: "PUT",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify({
            title,
            description,
            type,
            priority,
            status,
            assignedTo,
            commento
        })
    });
    alert("Issue aggiornata");

    setTimeout(() => {
        window.location.href = "dashboard.html";
    }, 100);
}