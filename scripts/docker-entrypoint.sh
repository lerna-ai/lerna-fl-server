#!/bin/bash

[[ "${JVM_DEBUG}" == "true" ]] && JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,address=8080,server=y,suspend=n"

java ${JAVA_OPTS} -jar fl-api.jar $@
