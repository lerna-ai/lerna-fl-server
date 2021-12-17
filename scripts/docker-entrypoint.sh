#!/bin/bash

[[ "${JVM_DEBUG}" == "true" ]] && JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,address=8080,server=y,suspend=n"

java ${JAVA_OPTS} -Djavax.net.ssl.keyStore=./cert.jks -Djavax.net.ssl.keyStorePassword="${SSL_CERTIFICATE_PASSWORD}" -Djavax.net.ssl.trustStore=./cert.jks -Djavax.net.ssl.trustStorePassword="${SSL_CERTIFICATE_PASSWORD}" -jar fl-api.jar $@
