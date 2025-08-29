#Usage
This is an Eclipse java 8 project. Its uses gradle to manage dependencies. 
Most of application properties can be set in application.properties file. Running the project on Eclipse should be straightforward.

The project runs on ssl hence all urls should be accessed via https://
Api documentation can be accessed via https://localhost/swagger-ui.html

Project uses Oauth2 authentication. token can be obtained from https://localhost/oauth/token. clientid and secret for basic authentication are in the application.properties file. default user:robmoi password:password. The clientid and default user should be enough to access the token.

For queries email to robmoi2010@gmail.com