#!/usr/bin/env bash
set -o xtrace
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: 04b4ecc6-c337-fb2a-4b33-2892ed123b17" "http://www.mathieupoitras.com/appdirect/subscription/notification/cancel?eventUrl=https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03?stubAccountIdentifier=$1"