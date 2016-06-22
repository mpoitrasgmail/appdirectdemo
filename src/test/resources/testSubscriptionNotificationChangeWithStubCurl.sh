#!/usr/bin/env bash
#!/usr/bin/env bash
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: 4ef15b97-7958-ad14-cdec-283d8b25f9b2" "http://localhost:8080/appdirect/subscription/notification/change?eventUrl=https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03?stubAccountIdentifier=$1"