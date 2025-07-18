#!/bin/bash

read -sp "Enter ENCRYPTION_KEY: " ENCRYPTION_KEY
echo
export ENCRYPTION_KEY
export EN_PRINT=y

mvn spring-boot:run
