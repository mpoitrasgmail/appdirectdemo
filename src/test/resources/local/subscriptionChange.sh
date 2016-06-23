 #!/usr/bin/env bash
 set -o xtrace
 curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: 257daa1d-c61b-5d3c-9bc4-cc76d2dadb16" "http://localhost:8080/appdirect/subscription/notification/change?eventUrl=https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03?stubAccountIdentifier=$1"