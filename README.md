Introduction
============
A java version of mysql broker for cloudfoundry.  It is ported from cf-mysql-broker.

How To Build and Run
====================
To build the project
```
./gradlew build
```

The build command creates jar file with embedded tomcat container.
```
java -jar build/libs/cf-mysql-java-broker-0.1.0.jar
```

Configuration
=============
By default,
* the tomcat server is listening at port `9000`
* requires local mysql server root user password must be `root`

The configuration can be changed by modifying the file under `resources\application.yml`

Routes
======
|Routes|Method|Description|
|------|------|-----------|
|/v2/catalog|GET|Service and its plan details by this broker|
|/v2/service_instances/:id|PUT|create a dedicated database for this service|
|/v2/service_instances/:id|DELETE|delete previously created database for this service|
|/v2/service_instances/:id/service_bindings/:id|PUT|create user and grant privilege for the database associated with service.|
|/v2/service_instances/:id/service_bindings/:id|DELETE|delete the user created previously for this binding.|

