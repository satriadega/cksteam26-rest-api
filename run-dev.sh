#!/bin/bash

read -sp "Enter ENCRYPTION_KEY: " ENCRYPTION_KEY
echo
export ENCRYPTION_KEY
export EN_PRINT=y
export EN_LOG=y
export EN_MODE=development
export EN_SHOWSQL=true
export EN_DATA_INIT=true

mvn spring-boot:run -X
