#!/bin/bash

read -sp "Enter ENCRYPTION_KEY: " ENCRYPTION_KEY
echo
export ENCRYPTION_KEY
export EN_PRINT=y
export EN_LOG=y
export EN_MODE=testing
export EN_SHOWSQL=false
export EN_DATA_INIT=false
export EN_TESTING=y

mvn clean test -e -X
