#!/usr/bin/env bash
gradle clean build
scp -i /c/Users/M/.ssh/mathieupoitras@yahoo.com.key build/libs/appdirectdemo-1.0-SNAPSHOT.war root@66.228.36.52:/var/appdirectdemo/
scp -i /c/Users/M/.ssh/mathieupoitras@yahoo.com.key application.properties root@66.228.36.52:/var/appdirectdemo/application.properties
ssh -i /c/Users/M/.ssh/mathieupoitras@yahoo.com.key root@66.228.36.52 'systemctl restart appdirectdemo'