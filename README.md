# Developer Notes

1. Create a folder in a preferred location of your local machine and name it as preferred.
2. Clone the repository via HTTPS. 
    a. Clone using HTTPS - git clone https://github.com/KCoreDev/tokyo-olympic-management-app-backend.git

## Setup MySQL with Docker Compose

1. Docker compose is used to setup a MySQL instance. Hence you need to have Docker and Docker compose installed in your machine to setup MySQL this way. 
2. Go to the root project folder. 
3. Open up a command terminal and nvaigate to the ci folder.
4. Type docker-compose up.
5. Docker will now run a MySQL instance on port 3306. 
6. Open up MySQL Workbench.
7. Click on Database and select Manage Connections.
8. Configure the new database with the following values,
    a. Hostname - localhost (given by default).
    b. Port - 3306
    c. Username - user (as provided in the docker-compose.yml file).
9. Now click on Test Connection.
10. Enter 'password' as the password in the prompted window (the password is provided in the docker-compose.yml) file.

That's it. 

If MySQL is available locally. Follow the steps starting from step 6. 

## Setup the Sprint Boot Application and Generate the database tables. 

1. Open the Spring Boot application in your IDE.
2. Let the dependencies download. 
3. Maven clean and then Maven install the project. 
4. Then run the Spring Boot Project.
5. Database tables will be generated automatically as configured in the application.yml (spring.jpa.hibernate.ddl-auto is set to update).
6. Navigate to the db_scripts folder and open the db_queries.sql file.
7. Copy the SQL scripts to the query tool in the MySQL Workbench.
8. First add the data to the results table with the given set of queries. 
9. Then add the events to the events table with the given queries.  

## Deliverables

### Available Functionality

#### Athlete Management Endpoints

1. Get all athletes.
2. Retrieve an athlete by athlete id.
3. Create an athlete.
4. Update an athlete. 
5. Delete athlete.

#### Event Management

1. Get all events.
2. Retrieve an event by event id.
3. Create an event.
4. Update an event. 
5. Delete event.

Endpoints to manage Results are available as well. 

### Testing the endpoints with Postman. 

1. Navigate to the postman_collection folder.
2. Import the file with Postman client application. 
3. Four requests will be available. 
    a. To add an athlete.
    b. To retrieve all the existing athletes' records.
    c. To retrieve an athlete record by athlete id.
    d. To retrieve events to display in the athlete add form. 
4. Use accordingly. 

### Testing the endpoints with the Frontend ReactJS Application.

1. Instructions provided in the Frontend repository.

#### Please note that Swagger 2.0 has been added to the project wih the intention of using it as the API documentation tool but could not be utilized. 

