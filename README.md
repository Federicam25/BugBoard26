BugBoard26
Piattaforma web per la gestione delle issue — progetto universitario di Ingegneria del Software.

Prerequisiti
Docker Compose installato

Step
1. Copia url del repository git <repo-url>
2. Aprire cmd dos e digitare: git clone <repo-url>
3. spostarsi nella cartella backend con il seguente comando:
   cd prjBugBoard/bugboard-backend
4. Per compilare il progetto backend eseguire il seguente comando:
   mvn clean package -DskipTests
5. Per startare tutti i container eseguire il seguente comando:
   docker compose up -d --build  (attendere il termine della compilazione e dell'avvio)
6. Per riavere il prompt del dos digitare "d"
7. Per visualizzare tutti i containers docker startati eseguire il seguente comando:
   docker ps   

L'applicazione sarà fruibile su http://localhost:8081

Utenti di default
Al primo avvio l'applicativo crea automaticamente il DB e i seguenti utenti:

Ruolo	Email				Password
Admin	admin1@bb26.com	 	admin
Admin	admin2@bb26.com	 	admin
User	luigi@bb26.com		password
User	sara@bb26.com		password
User	mario@bb26.com		password
User	paolo@bb26.com		password
User	anna@bb26.com		password
