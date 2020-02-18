# Fun7Service

Google app engine standard REST API application.

The application provides the status (availability) of three services (multiplayer, user support and ads).

It should accept three query parameters.
 - timezone: timezone of the user
 - userId 
 - cc: country code


The service returns three statuses based upon the availability of a service. A service can either be `enabled` or `disabled`.
```json
{
    "multiplayer": "disabled",
    "user-support": "disabled",
    "ads": "enabled"
}
```

If the parameters are not provided, the service returns a
>400 Bad Request 

### Requirements

* Java 8 (1.8 JRE)
* Maven
* Google cloud SDK

### Build

To build the project run
 - mvn clean
 - mvn install
 - mvn appengine:run - to run locally

### Additional information

The API was build with the Spring framework, and also uses Datastore for data persistance.
Additional dependencies include
 - The low level persistance API objectify
 - Junit
 - Vadin (for json formation)

