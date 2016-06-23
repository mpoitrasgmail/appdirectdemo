#!/usr/bin/env bash
set -o xtrace
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: c024eac6-0a96-7f2b-0c66-22b5fde61924" "http://www.mathieupoitras.com/appdirect/subscription/notification/create?eventUrl=https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03"