document.addEventListener("DOMContentLoaded", initCreateIssue);
function checkAuth(){
    const token = localStorage.getItem("token");
    if(!token){
        window.location = "index.html";
    }
}

function initCreateIssue(){

    checkAuth();

    document
        .getElementById("create-issue-form")
        .addEventListener("submit", createIssue);

}

async function createIssue(event){
    event.preventDefault();
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const type = document.getElementById("type").value;
    const priority = document.getElementById("priority").value;
    const status = "todo";
    const token = localStorage.getItem("token");
    const response = await fetch(API_BASE + "/api/issues",{
        method:"POST",
        headers:{
            "Content-Type":"application/json",
            "Authorization":"Bearer " + token
        },
        body: JSON.stringify({
            title:title,
            type:type,
            description:description,
            priority:priority,
            status:status
        })
    });
    if(response.ok){
        alert("Issue creata");
    }else{
        alert("Si è verificato un errore");
    }
    window.location.href = "dashboard.html";
}