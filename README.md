# appdirectdemo
My AppDirect Coding Challenge demo

In order to present my code I have done the following:
* Register a domain name (mathieupoitras.com)
* Deployed a new server (using Linode)
* Deployed a centOS 7.2 image to that server
* Redirected the DNS records for the domain
* Installed nginx to listen to port 80 with a non-root user (including installing the official nginx yum repository)
* Register nginx to automatically start at startup
* Configured a nginx forwarding from port 80 to port 8080
* Configured the firewall to automatically start at startup
* Opened port 80 from public
* Ensured that port 8080 could not be reached from outside
* Installed Oracle JDK 1.8 (More stable than OpenJDK)
* Added a user appdirectdemo to run the application
* Added a service to systemctl to run the application
* Configured logging to go under /var/log/appdirectdemo/
* Created a new github account with a separate email address
* Generated a new SSH key
* Configured github to use the key
* Configured the authorized_keys on the server to use that key
* Configured my development machine to use that key
* Created this new Github repository


