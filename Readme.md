### Repository scraping-github-api

API for scraping data from GitHub. This API will return, from a valid URL, data from all files in the repository, grouped by extension.

The application was developed using Spring Boot with VS CODE. It is published on Heroku, but it is possible to run locally.
 
Local build requirements:
- Maven;
- Java 11;

#### Heroku
To use Heroku, the url of the desired repository must be passed by parameter. Ex.:

https://scraping-github-api.herokuapp.com/api/files?url=https://github.com/rikes/DataMiningCAGED/

Or using Swagger at:

https://scraping-github-api.herokuapp.com/swagger-ui/#/

#### Docker

Finally, it is possible to test the application using Docker. The application image was made available on the Docker Hub, and is accessible.

To do this, make sure you have the docker installed on your machine, and then download the file 'docker-compose.yaml'

Open a terminal and run the command

``` unix
docker-compose up
```

The previous command will download all necessary dependencies that do not exist on your machine. At the end of the command, the application will be available on port 8080 and the database on 5432.

Example:
http://127.0.0.1:8080/api/files?url=https://github.com/rikes/scraping-github-api


