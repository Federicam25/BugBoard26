async function doLogin(event){
    event.preventDefault();
    const username = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    try{
        const response = await login(username,password);
        localStorage.setItem("token", response.token);
        localStorage.setItem("role", response.role);
        localStorage.setItem("username", response.username);
        window.location = "dashboard.html";
    }catch(e){
        document.getElementById("error-msg").innerText =
            "Email o password errati";
    }
}
