FROM anapsix/alpine-java
MAINTAINER Minhaj Mohammad
COPY macaddress-1.0-SNAPSHOT.jar /home/macaddress.jar
CMD ["java","-jar","/home/macaddress.jar"]