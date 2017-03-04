# Base64-validator

A Java Web based application, used for making based64 enconded Json comparison.

### How it works:

- Providing 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints;
- Providing a endpoint for diff comparison.

## Built with

* [Spring](http://spring.io) - The web framework used
* [JUnit](http://junit.org) - A unit testing framework for Java
* [Mockito](http://site.mockito.org) - Mocking framework for unit tests written in Java.
* [Tomcat](tomcat.apache.org/) - Embedded version of a servlet container and Web server. 
* [h2](www.h2database.com) - Runtime free SQL database written in Java.
* [Maven](https://maven.apache.org/) - Dependency Management.
* [Docker](https://https://docs.docker.com/) - Used to generate containers.
* [Java](https://www.java.com) - Language used to written code. "Write once, run anywhere" 
* [Eclipse](https://eclipse.org/) - An open source IDE for Java projects.

## Installation

It requires [Java](https://www.java.com) Jdk v8+ and [Maven](https://maven.apache.org/)  to run.
or
You can test it using [Docker](https://https://docs.docker.com/)

After install Java and Maven, clone the [repository](https://github.com/erorci/base64.git) into a directory.

```sh
$ cd base64
$ mvn package
$ java -jar target/base64-0.0.1-SNAPSHOT.jar 
```


## Running unit and integration tests
After changes you can run tests using Maven command:
```sh
$ cd base64
$ mvn test
```

## Docker
Based64 is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
cd base64/
docker build -t erorci/base64 .
```

or 

Using Maven you can build a image to:

```sh
cd base64/
mvn package docker:build
```

Both will create the base64 image and pull in the necessary dependencies. 


Once done, run the Docker image and map the port to whatever you wish on your host. In this example, we simply map port 8000 of the host to port 8080 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 8000:8080 --restart="always" erorci/base64
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
http://localhost:8000/health
```
or using cUrl

```sh
$ curl http://localhost:8000/health
```
The result expected is {"status":"UP"}


When it is running you can see in the list of containers, e.g:


```sh
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                        NAMES
927451fdd8a6        erorci/base64       "sh -c 'java $JAVA..."   25 minutes ago      Up 25 minutes       0.0.0.0:8000->8080/tcp                       condescending_curie
```
and to shut it down again you can docker stop with the container ID from the listing above (yours will be different):


```sh
$ docker stop 927451fdd8a6
```


If you like you can also delete the container:
```sh
$ docker rm 927451fdd8a6
```

Debugging the application in a Docker container
```sh
$ docker run -e "JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" -p 8000:8080 -p 5005:5005 -t erorci/base64
```


## Rest API

There are three main endpoints you can call. Instructions on how to use them in your own application are linked below.

| Title | URL | Method | Params | Data Params
| ------ | ------ | ------ | ------ | ------ |
| Left Json save | host:port/v1/diff/ID/left | POST |Required: id=[integer] | { "data": "[string]"} |
| Right Json save |host:port/v1/diff/ID/right | POST | Required: id=[integer] |{ "data": "[string]"} |
| Diff Result |host:port/v1/diff/ID | GET | Required: id=[integer] | 

Example:
```sh
method: POST
url: http://localhost:8080/v1/diff/1/left
message body: 
{
  "data": "DBVXbG8gd29ybxGJK="
}
result:
Status: 200 OK
{
  "message": "OK"
}
```

## Improvements

 - Refactor Json classes for object manipulation.
 - Use a different approach to map Json on request body.
 - Improve Rest errors codes and verbs usage.
 - Using a different approach to test JMS messaging

## Development

Want to contribute? Great!

## Author

* **Emerson Rodrigo Orci** - [Profile](https://www.linkedin.com/in/emersonorci/)
