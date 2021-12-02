# Lerna FL API
> Backend Service for Lerna FL mobile SDK

## Table of contents

- [Usage](#Usage)
  - [Installation](#installation)
  - [Testing](#Testing)
  - [Running the application](#Run)
- [Configuration](#Configuration)
- [Logging](#logging)
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

# GK: probably we need the following parameters when running: 
# -Djavax.net.ssl.keyStore=<path>/cert.jks
# -Djavax.net.ssl.keyStorePassword="password"
# -Djavax.net.ssl.trustStore=<path>/cert.jks
# -Djavax.net.ssl.trustStorePassword="password"

## Configuration

The sample configuration of the web-api spring boot application
resides in `resources/application.properties` file.

Using a separate `application-{spring.profiles.active}.properties` can enable a different configuration file per environment.

## Logging

The logs of the spring boot application can be found under `logs/`.

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
