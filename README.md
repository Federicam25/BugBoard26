BugBoard26
Piattaforma web per la gestione delle issue — progetto universitario di Ingegneria del Software.

Prerequisiti
Docker Compose installato

Step
1. Copia url del repository git <repo-url>
2. Aprire cmd dos e digitare: git clone <repo-url>
3. spostarsi nella cartella backend con il seguente comando:
   cd BugBoard26/prjBugBoard/bugboard-backend
4. Quindi per compilare il progetto backend eseguire il seguente comando:
   mvn clean package -DskipTests
6. Per verificare l'esito dei casi di test sviluppati eseguire il seguente comando:
   mvn test
7. Posizionarsi nella cartella root del progetto tramite il seguente comando:
   cd ..
8. Per startare tutti i container eseguire il seguente comando:
   docker compose up -d --build  (attendere il termine della compilazione e dell'avvio)
9. Per riavere il prompt del dos digitare "d"
10. Per visualizzare tutti i containers docker startati eseguire il seguente comando:
   docker ps   

L'applicazione sarà fruibile su http://localhost:8081

Utenti di default
Al primo avvio l'applicativo crea automaticamente il DB e i seguenti utenti:

Ruolo	Email				Password
Admin	admin1@bb26.com	 	admin
Admin	admin2@bb26.com	 	admin
User	luigi@bb26.com		password
User	sara@bb26.com		password
User	marco@bb26.com		password
User	paolo@bb26.com		password
