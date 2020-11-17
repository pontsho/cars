# Racing Cars

The project uses [Maven](https://maven.apache.org/) as a build tool. It already contains ./mvnw wrapper script, so there's no need to install maven.

###  Top-level directory layout

    .
    ├── src                         # Source files 
    │   ├── main
    │   │   ├── docker              # docker-compose configuration files
    │   │   ├── java                # java classes in different packages
    │   │   └── resources           # Resources spring application file and DB migration scripts for flyway
    │   └── test                    # Unit tests 
    │       ├── java                # java classes with tests in different packages
    │       └── resources           # Resources spring application file and DB migration scripts for flyway
    ├── target                      # Compiled files i.e cars.jar file
    └── README.md

### Running Spring Boot application

To run this application please use the command below on the terminal. 

```
./mvnw spring-boot:run
```
Please avoid using the IDE start/run button as it does not properly 
invoke the java.io.Console which is needed by the user to interact with the application via the console/commandline


### Packaging as jar

To build the final jar and optimize the Cars application, run:

```
./mvnw clean install
```

To run the application using the generated jar file, run:

```
java -jar target/*.jar
```

### Database setup

I use Flyway as database tool for refactoring and versioning db scripts; 
Flyway uses SQL to define database changes.

We have two sql scripts for creating and inserting data into the Database

The application has two tables: `car` and `race_track`

The datasource is configured in `application.yml` file under resources folder.
DB username must be `root` and password is not set. 
If you current DB has password and different username please update the datasource in the `application.yml` file


To add more race tracks please see `resources/db/migration/V20201111230324__RaceTrackEntities.sql`

To add more racing cars please see `resources/db/migration/V20201110230324__CarsEntities.sql`

## Using Docker to simplify development (optional)

### Database (Optional)

You can use Docker to improve your development experience. A docker-compose configuration for MYSQL DB is available in the [src/main/docker](src/main/docker) folder to launch mysql databases.
This will be useful if you dont have mysql database on you machine
To start a mysql database in a docker container, run:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mysql.yml down
```

### Code quality (Optional)

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9000) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw clean verify sonar:sonar  -DskipTests
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar  -DskipTests
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mysql.yml down
```
For more information, refer to the [Code quality page][].
