# Lerna FL API
> Backend Service for Lerna FL mobile SDK

## Table of contents

- [Usage](#Usage)
  - [Installation](#installation)
  - [Testing](#Testing)
  - [Running the application](#Run)
- [Configuration](#Configuration)
- [Database Migration](#database-migration)
- [Logging](#logging)
- [Rest API](#rest-api)
  - [Documentation](#documentation)
- [Recommendation API](#recommendation-api)
- [Licenses](#licenses)

## Usage

### Installation

Gradle is the dependency and build management tool that the FL-api project uses.

```bash
# build project
#
./gradlew build
```

### Testing

Unit tests run with the following command.

```bash
# Run unit tests
#
./gradlew test
```

### Run

fl-api is a Spring Boot application, in order to run application use the following command.

```bash
# Run using default profile
#
./gradlew bootRun
```

To run application with local profile use the following command. 

```bash
# Run using local as active profile
#
./gradlew bootRun --args='--spring.profiles.active=local'
```

> NOTE:
> 
> Probably we need the following parameters when running: 
> 
> ```bash
> -Djavax.net.ssl.keyStore=<path>/cert.jks
> -Djavax.net.ssl.keyStorePassword="password"
> -Djavax.net.ssl.trustStore=<path>/cert.jks
> -Djavax.net.ssl.trustStorePassword="password"```


### Running in Docker

All Lerna applications are dockerized.

#### Building the Docker images

Every component has its own Docker image, and a Dockerfile exists in each
directory to build that image.

To build each module image, change to the root directory of each repository and run the
`docker build` command, optionally specifying a tag. For example, to
build the fl-api module Docker image and tag it as version 1.0, do:

```
$ docker build -t lerna/fl-api:1.0
```

After creating all the Docker images, you can spin up a local Docker
environment for further development and testing.

#### Running with Docker Compose

Docker Compose is a tool for defining and running multi-container Docker
applications.

In the root of the repository you can find the `docker-compose.yaml` file
describing the  PostgreSQL and Redis services as well
as the Lerna modules that need those services.

To successfully work with Docker Compose, you need to have a `local.env` file
in the root of the repository that defines the Environment Variables passed
to the spawned containers:

```
$ cp local.env.sample local.env
```

To run Lerna fl-api we need to provide valid java keystore file under the following paths

```bash
# Certificate file
./scripts/cert.jks
```

To start a local environment with all the components, execute the
following from the root of the repository:

```bash
$ docker-compose up
```

> Note: In order to start all modules through `docker-compose` we need to located in the same folder. For example a typical module structure under dev folder should be:
> ```bash
> $ ls -la ~/dev/
> total 0
> drwxr-xr-x 3 miltos miltos 512 Dec  1 10:35  .
> drwxr-xr-x 6 miltos miltos 512 Dec  1 10:07  ..
> drwxr-xr-x 2 miltos miltos 512 Dec 17 13:10  fl-api
> drwxr-xr-x 2 miltos miltos 512 Dec 14 15:00  mpc
>```

If you only need specific components, then specify only the one(s) you need.
For example, to start only mpc, execute:

```bash
$ docker-compose up mpc
```

To start mpc and DB
```bash
$ docker-compose up db mpc
# or to force rebuild of images
$ docker-compose up --build db mpc
```

You can override the default environment variables shipped in `local.env`
file by editing the `docker-compose.override.yaml` file in the root of the
repository. This file is automatically read by `docker-compose` (no need to
specify it in the CLI using `-f <file>`) and gets higher priority than the
values of `docker-compose.yaml` file. _Moreover, this override file is
ignored by Git, so you don't have to worry about untracked changes in your
working tree_.

It is useful to create Docker images and tag them with a version that matches
the project version. To do so, first commit your working directory,
and then run:

```
$ LERNA_SRC_VERSION=`git rev-parse --short HEAD` docker-compose up
```

#### Verifying the local environment

Use the Docker CLI to inspect the running containers and get the `fl-api`
endpoint:

```
$ docker container ls
```

Then, verify that you can access the `fl-api`, for example requesting
a new job ID by test endpoint:

```
$ curl http://localhost:8080/test/lerna
```

## Configuration

The sample configuration of the web-api spring boot application
resides in `resources/application.properties` file.

Using a separate `application-{spring.profiles.active}.properties` can enable a different configuration file per environment.

## Database migration

We use Flyway to perform database migrations.

The database migration scripts reside in the
`src/main/resources/db/migration/` directory.

The name of these SQL scripts must follow the pattern:

```
V<TIMESTAMP>__<SQL_SCRIPT_DESCRIPTION>.sql
```

For example:

```
V202112161318397__CREATE_NEW_TABLE.sql
```

To generate a new SQL script, execute core project gradle task flywayMigration:

```
$ ./gradlew generateMigration -PflywayScriptName=<SQL_SCRIPT_DESCRIPTION>
```

For example:

```
$ ./gradlew generateMigration -PflywayScriptName=CREATE_NEW_TABLE
```

Note: DDL statements are used to build and modify the structure of database entities e.g. CREATE or ALTER TABLE.
In order to avoid database locks due to long-running scripts, we should create separate scripts for DDL statements.

## Logging

The logs of the spring boot application can be found under `logs/`.

## Rest API

### Documentation
In order to document the API, we use the OpenAPI Specification (formerly Swagger Specification).
OpenAPI Specification is an API description format for REST APIs. In order to access the documentation via the
Swagger UI, access the below url

```
http://localhost:8080/swagger-ui.html
```

## Recommendation API

For Recommendation API documentation follow [this](RecommendationAPI.md) link. 

## Licenses
`fl-api` depends on OSS code under the following licenses:
* [Apache License 1.1](https://www.apache.org/licenses/LICENSE-1.1)
* [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)
* [BSD-2-Clause](https://opensource.org/licenses/BSD-2-Clause)
* [BSD-3-Clause](https://opensource.org/licenses/BSD-3-Clause)
* [CC0](https://creativecommons.org/publicdomain/zero/1.0/)
* [CDDL-1.0](https://opensource.org/licenses/CDDL-1.0)
* [CDDL-1.1](https://javaee.github.io/glassfish/LICENSE)
* [EDL-1.0](http://www.eclipse.org/org/documents/edl-v10.php)
* [EPL-1.0](https://www.eclipse.org/legal/epl-v10.html)
* [GNU LGPL-2.1](http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html)
* [MIT Licence](https://opensource.org/licenses/mit-license)
* [MPL-1.1](https://www.mozilla.org/en-US/MPL/1.1/)
* [The PostgreSQL License](https://opensource.org/licenses/postgresql)
* [The SAX License](http://www.saxproject.org/copying.html)
* [The W3C License](https://www.w3.org/Consortium/Legal/2015/copyright-software-and-document)

You can create a report with all dependencies along with their licenses as follows:

```sh
./gradlew generateLicenseReport
```
