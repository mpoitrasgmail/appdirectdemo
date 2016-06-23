#!/usr/bin/env bash
set -o xtrace
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: 589926a6-8199-2161-0d2f-fa32e1f0cebb" "http://www.mathieupoitras.com/appdirect/user/notification/unassign?eventUrl=https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03?stubAccountIdentifier=$1&stubUserUuid=$2&stubUserEmail=$3"