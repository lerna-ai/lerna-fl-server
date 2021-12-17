FROM openjdk:8-jre-slim

EXPOSE 8080 8080

COPY scripts/docker-entrypoint.sh .

RUN chmod a+x docker-entrypoint.sh

COPY scripts/*.jks cert.jks

COPY build/libs/fl-api-*.jar fl-api.jar

ENTRYPOINT ["./docker-entrypoint.sh"]
