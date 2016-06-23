# appdirectdemo
My AppDirect Coding Challenge demo

# What was integrated?
This application will integrate with AppDirect. More specifically, with the following events:
* Subscription Create
* Subscription Change
* Subscription Cancel
* User Assign
* User Unassign
* OAuth 1.0 Signature verification

# Running using the already deployed instance
The application is currently running at http://www.mathieupoitras.com. It is running with appdirect stubs and OAuth Verification filter off, please see

The easiest way to exercise the code is simply to run the curl scripts (either on a unix bash or a windows git bash) as follows:
```bash
src/test/resources/mathieupoitrascom/subscriptionCreate.sh
```

OUTPUT:
```bash
{"success":true,"accountIdentifier":"714d2dab-6956-4bc4-8f17-9c8b8a4e8d23"}
```
Take the resulting JSON accountIdentifier, and pass it in the next scripts (in this case 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23)

```bash
src/test/resources/mathieupoitrascom/subscriptionChange.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23
src/test/resources/mathieupoitrascom/assignUser.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23 ABCFGFDFGFDGH superuser@hello.com
src/test/resources/mathieupoitrascom/unassignUser.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23 ABCFGFDFGFDGH superuser@hello.com
src/test/resources/mathieupoitrascom/subscriptionCancel.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23
```


# Running using a local development instance
Alternatively the code can be checked out from:
```bash
git clone git@github.com:mpoitrasgmail/appdirectdemo.git
```

Then build with:
```bash
gradlew clean build
```

Ran with (use run.sh for linux, run.bat for windows):
```bash
run.sh
run.bat
```

Finally it can be tested with:
The easiest way to exercise the code is simply to run the curl scripts (either on a unix bash or a windows git bash) as follows:
```bash
src/test/resources/mathieupoitrascom/subscriptionCreate.sh
```
OUTPUT:
OUTPUT:
```bash
{"success":true,"accountIdentifier":"714d2dab-6956-4bc4-8f17-9c8b8a4e8d23"}
```
Take the resulting JSON accountIdentifier, and pass it in the next scripts (in this case 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23)

```bash
src/test/resources/local/subscriptionChange.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23
src/test/resources/local/assignUser.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23 ABCFGFDFGFDGH superuser@hello.com
src/test/resources/local/unassignUser.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23 ABCFGFDFGFDGH superuser@hello.com
src/test/resources/local/subscriptionCancel.sh 714d2dab-6956-4bc4-8f17-9c8b8a4e8d23
```


# KNOWN LIMITATION NOTE:
The test instance on AppDirect.com  (https://mathieupoitras-test.byappdirect.com, product Demo) does not send event urls or authentication headers.
Thus the Ping And Integration tests fail at this time (I had them working, but then As I implemented more according to documentation, they failed).
Running the application with no Spring profile (default) and executing actual valid calls from AppDirect.com should be successful but could not be tested.



# Work Explained
Below I have documented some of the things I have done to

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
* Coded the actual application
* Added utility scripts
* Promoted it to my server