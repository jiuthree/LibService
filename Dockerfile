FROM anapsix/alpine-java:8
MAINTAINER root
ADD LibService-0.0.1-SNAPSHOT.jar ~/LibService.jar
EXPOSE 27023
ENTRYPOINT ["java","-jar", "~/LibService.jar"]
