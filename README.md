# Task-Manager using SpringBoot and MongoDB

1 - Requirements:

- Java 8
- Maven 3.6+
- Docker
- Docker-compose

2 - Explanations:

- The configuration for mongodb connection is on config/MongoConfig.java, if you have a database accessed to localhost for example, just change there. The default value is mongodb:27017 (used in docker-compose)
- The MetricFilter class will gather every request made to the api, and save it on mongodb
- Integration tests is on src/tests
- The logging is configured also on MetricFilter

3 - Compile & Use API:

To compile tests: mvn test or mvn package - Remember to configure your mongodb correct host

To compile without tests: mvn package -Dmaven.test.skip=true

To run in standalone: mvn spring-boot:run - will run on localhost:8080

To run with docker-compose: docker-compose up (need to be on same folder as docker-compose.yml) will bind to 8080 for application and 27017 for mongodb on 0.0.0.0

A simple example with whole workflow:

```bash
sudo docker-compose down && mvn package -Dmaven.test.skip=true && sudo docker-compose up --build
```


4 - Using the API:

A instance in AWS was launched at public ip 54.161.4.37

- POST /employee/ - Insert new employee
- PUT /employee/  - Update existing employee
- GET /employee/  - Get all employee
- GET /employee/<organization>  - Get employee by organization with Regex and Insentive
- DELETE /employee/ - Delete all employee

/employee
```bash
curl -X GET -H "Content-type: application/json" --resolve 'swiss.com:80:54.161.4.37' http://swiss.com/employee/dev -u swisscom:pass
[{
	"id": "4927a684-d19b-4ff1-be92-bcd52ecd5fcf",
	"firstname": "Dora",
	"lastname": "Doorbell",
	"organization": "Development",
	"birthDate": "Oct 20, 1992 12:00:00 AM",
	"costcenter": "00334280"
}, {
	"id": "b0f1a328-3888-4581-a094-b2d02e00b7be",
	"firstname": "Ernest",
	"lastname": "Evilness",
	"organization": "dev",
	"birthDate": "Sep 9, 1999 12:00:00 AM",
	"costcenter": "00192900"
}, {
	"id": "cfc97de0-2159-4ae1-955b-63bccd33e8fd",
	"firstname": "Bert",
	"lastname": "Broom",
	"organization": "Development",
	"birthDate": "Oct 1, 1980 12:00:00 AM",
	"costcenter": "00300200"
}, {
	"id": "14d81c67-361e-4616-bd09-1ce0242b8655",
	"firstname": "Fred",
	"lastname": "Fox",
	"organization": "Development",
	"birthDate": "Oct 13, 1985 12:00:00 AM",
	"costcenter": "00293940"
}, {
	"id": "290632ec-695d-4b12-927a-20150f546363",
	"firstname": "George",
	"lastname": "Golfball",
	"organization": "development",
	"birthDate": "Jun 10, 1978 12:00:00 AM",
	"costcenter": "0093109"
}]
```
EMPLOYEE API - METRIC

- GET /employeemetrics/ - Will return every request made to the API
- GET /employeemetrics/stats - Will return a statistic metric for every request made, those metrics are: totaltime, totalcount and average response time in ms

```bash
curl -X GET -H "Content-type: application/json" --resolve 'swiss.com:80:54.161.4.37' http://swiss.com/employeemetrics/stats -u swisscom:pass
[{
	"method": "GET",
	"route": "/employee/Development",
	"total": 2,
	"time": 589,
	"average": 294.5
}, {
	"method": "GET",
	"route": "/employee/finan",
	"total": 1,
	"time": 14,
	"average": 14.0
}, {
	"method": "GET",
	"route": "/employee",
	"total": 2,
	"time": 24,
	"average": 12.0
}, {
	"method": "DELETE",
	"route": "/employee/",
	"total": 4,
	"time": 276,
	"average": 69.0
}, {
	"method": "DELETE",
	"route": "/employee/dev",
	"total": 2,
	"time": 29,
	"average": 14.5
}, {
	"method": "GET",
	"route": "/employee/",
	"total": 47,
	"time": 1564,
	"average": 33.276595744680854
}, {
	"method": "GET",
	"route": "/employee/dev",
	"total": 6,
	"time": 483,
	"average": 80.5
}, {
	"method": "GET",
	"route": "/employee/human",
	"total": 1,
	"time": 18,
	"average": 18.0
}, {
	"method": "GET",
	"route": "/employee/taskmetrics/stats",
	"total": 1,
	"time": 14,
	"average": 14.0
}]

```
ACTUATOR
/actuator/

- /actuator/auditevents - Exposes audit events (e.g. auth_success, order_failed) for your application
- /actuator/info - Displays information about application.
- /actuator/health - Displays application’s health status.
- /actuator/metrics - Shows various metrics information of application.
- /actuator/loggers -	Displays and modifies the configured loggers.
- /actuator/logfile -	Returns the contents of the log file (if logging.file or logging.path properties are set.)
- /actuator/httptrace -	Displays HTTP trace info for the last 100 HTTP request/response.
- /actuator/env - Displays current environment properties.
- /actuator/flyway - Shows details of Flyway database migrations.
- /actuator/liquidbase - Shows details of Liquibase database migrations.
- /actuator/shutdown - Lets you shut down the application gracefully.
- /actuator/mappings - Displays a list of all @RequestMapping paths.
- /actuator/scheduledtasks - Displays the scheduled tasks in your application.
- /actuator/threaddump - Performs a thread dump.
- /actuator/heapdump - Returns a GZip compressed JVM heap dump

```bash
curl -X GET -H "Content-type: application/json" --resolve 'swiss.com:80:54.161.4.37' http://swiss.com/actuator/info -u swisscom:pass
{
	"app": {
		"name": "employeemanager",
		"description": "A employee API",
		"version": "0.0.1-SNAPSHOT",
		"encoding": "UTF-8",
		"java": {
			"version": "1.8.0_222"
		}
	}
}
curl -X GET -H "Content-type: application/json" --resolve 'swiss.com:80:54.161.4.37' http://swiss.com/actuator/health -u swisscom:pass
{
	"status": {
		"code": "UP",
		"description": ""
	},
	"details": {
		"diskSpace": {
			"status": {
				"code": "UP",
				"description": ""
			},
			"details": {
				"total": 8577331200,
				"free": 4520038400,
				"threshold": 10485760
			}
		},
		"mongo": {
			"status": {
				"code": "UP",
				"description": ""
			},
			"details": {
				"version": "4.2.3"
			}
		}
	}
}
```
