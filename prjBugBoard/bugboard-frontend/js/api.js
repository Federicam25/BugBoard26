const API_BASE = "http://localhost:8080";

async function getIssues(){
    const response = await fetchWithAuth(API_BASE + "/api/issues");
    return await response.json();
}

async function login(username,password){
    const response = await fetch(API_BASE + "/api/login",{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            username:username,
            password:password
        })
    });
    if(!response.ok){
        throw new Error("Credenziali non valide");
    }
    return await response.json();
}

async function getStats(){
    const response = await fetchWithAuth(API_BASE + "/api/issues/stats");
    return await response.json();
}

function getAuthHeaders(){
    const token = localStorage.getItem("token");
    return {
        "Authorization": "Bearer " + token
    };
}
/*
async function fetchWithAuth(url, options = {}){
    const token = localStorage.getItem("token");
    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token
    };
    const response = await fetch(url, options);
    if(response.status === 401){
        localStorage.clear();
        alert("Sessione scaduta");
        window.location.href = "index.html";
        throw new Error("Unauthorized");
    }
    return response;
}*/

async function fetchWithAuth(url, options = {}){
    try{
        const token = localStorage.getItem("token");
        options.headers = {
            ...options.headers,
            "Authorization": "Bearer " + token
        };
        const response = await fetch(url, options);
        if(response.status === 401){
            localStorage.clear();
            window.location.href = "index.html";
            return;
        }
        return response;
    }catch(error){
            alert("Sessione scaduta");
            window.location.href = "index.html";
    }
}