#!/bin/bash
mvn clean package
java -jar target/hystrix-demo-0.0.1-SNAPSHOT.jar
