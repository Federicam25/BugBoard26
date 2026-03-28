document.addEventListener("DOMContentLoaded", initCreateUser);

function checkAuth(){
    const token = localStorage.getItem("token");
    if(!token){
        window.location = "index.html";
    }
}

function initCreateUser(){
    checkAuth();
    const role = localStorage.getItem("role");
    if(role !== "ADMIN" && role !== "admin"){
        alert("Accesso negato");
        window.location = "dashboard.html";
    }
    document
        .getElementById("create-user-form")
        .addEventListener("submit", createUser);
}
async function createUser(event){
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;
    const token = localStorage.getItem("token");
    const response = await fetch(API_BASE + "/api/users",{
        method:"POST",
        headers:{
            "Content-Type":"application/json",
            "Authorization":"Bearer " + token
        },
        body: JSON.stringify({
            email: email,
            password: password,
            role: role
        })
    });
    const data = await response.text();

    if (data.trim() === "OK") {
        alert("Utente creato con successo");
    } else {
        alert("L'utente è già presente");
    }
    window.location.href = "dashboard.html";
}