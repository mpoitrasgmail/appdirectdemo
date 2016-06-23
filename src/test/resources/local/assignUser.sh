#!/usr/bin/env bash
set -o xtrace
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: 02775314-0667-57fb-fe20-5cd6c2dfc088" "http://localhost:8080/appdirect/user/notification/assign?eventUrl=https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03?stubAccountIdentifier=$1&stubUserUuid=$2&stubUserEmail=$3"