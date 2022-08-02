#!/bin/bash

docker run --restart unless-stopped -d -p 9900:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin --name keycloak jboss/keycloak:12.0.3