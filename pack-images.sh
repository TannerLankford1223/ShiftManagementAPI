#!/bin/bash

echo "Performing a clean Maven build"
./mvnw clean package -DskipTests=true

echo "Building the Discovery Server"
cd discovery-service
./mvnw jib:dockerBuild
cd ..

echo "Building the Gateway Server"
cd gateway-service
./mvnw jib:dockerBuild
cd ..

echo "Building the Address Server"
cd address-service
./mvnw jib:dockerBuild
cd ..

echo "Building the Employee Server"
cd employee-service
./mvnw jib:dockerBuild
cd ..

echo "Building the Shift Server"
cd shift-service
./mvnw jib:dockerBuild
cd ..

echo "Building the Email Server"
cd email-service
./mvnw jib:dockerBuild
cd ..


