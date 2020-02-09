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

4 - Using the API:

- POST /TODO/ - Insert new employee task
- PUT /TODO/  - Update existing task
- GET /TODO/  - Get all tasks
- GET /TODO/<taskId>  - Get specific task
- DELETE /TODO/ - Delete all tasks
- DELETE /TODO/<taskId> - Delete specific task

/employee
```bash
curl -X POST -H "Content-Type: application/json" -d '{"id":"1","description":"mytask"}' http://localhost:8080/employee/

curl -X PUT -H "Content-Type: application/json" -d '{"id":"2","description":"mytask", "status":"Completed"}' http://localhost:8080/employee/

curl -X GET -H "Content-type: application/json" http://localhost:8080/employee/
[
   {
      "id":"1",
      "description":"mytask",
      "status":"Pending"
   },
   {
      "id":"2",
      "description":"mytask",
      "status":"Completed"
   }
]

curl -X DELETE -H "Content-type: application/json" http://localhost:8080/employee/2

curl -X DELETE -H "Content-type: application/json" http://localhost:8080/employee/
```
TASK - METRIC

- GET /taskmetrics/ - Will return every request made to the API
- GET /taskmetrics/stats - Will return a statistic metric for every request made, those metrics are: totaltime, totalcount and average response time in ms

```bash
curl -X GET -H "Content-type: application/json" http://localhost:8080/taskmetrics/
{
   "id":"02d43fbf-e18a-482b-a088-7580f422626d",
   "route":"/employee/",
   "method":"GET",
   "time":165
},
{
   "id":"163698b9-1b4a-4a39-9bce-a8e9c7a49b2b",
   "route":"/employee/",
   "method":"POST",
   "time":278
},
{
   "id":"659bfcaa-bec2-451a-a2a8-022683c507d7",
   "route":"/employee/",
   "method":"GET",
   "time":21
},
{
   "id":"1bf66100-eb9b-46fc-aa4a-bc9fd57e72a5",
   "route":"/employee/",
   "method":"DELETE",
   "time":14
},
{
   "id":"63e107e1-5e10-41ba-bd46-fe8039cdf615",
   "route":"/employee/",
   "method":"POST",
   "time":9
},
{
   "id":"f6e1ac72-40db-42e3-bd02-e12d67cce081",
   "route":"/employee/",
   "method":"PUT",
   "time":6
},
{
   "id":"9aeb00fd-f920-49e8-affe-fe94feeb38fb",
   "route":"/employee/",
   "method":"GET",
   "time":9
},
{
   "id":"7ce5489f-82c3-4886-ad37-0c9e1ae0582a",
   "route":"/employee/2",
   "method":"DELETE",
   "time":21
},
{
   "id":"1705680e-d09e-40ed-86c2-5120200c95f1",
   "route":"/employee/",
   "method":"GET",
   "time":11
},
{
   "id":"43d31395-92f1-4e7c-b0de-e2ecdbbc2459",
   "route":"/employee/",
   "method":"DELETE",
   "time":4
},
{
   "id":"6087eea8-dfc1-4535-ba35-b4fbd2a640fd",
   "route":"/employee/",
   "method":"GET",
   "time":7
},
{
   "id":"6277b6fd-7cc9-4969-90a6-289644f39309",
   "route":"/taskmetrics",
   "method":"GET",
   "time":7
},
{
   "id":"5334a547-648f-4795-bc81-0ddfd9c9c260",
   "route":"/taskmetrics/stats",
   "method":"GET",
   "time":150
},
{
   "id":"9b1152aa-f713-452e-ac68-5de18a123d0e",
   "route":"/taskmetrics",
   "method":"GET",
   "time":6
}
]
curl -X GET -H "Content-type: application/json" http://localhost:8080/taskmetrics/stats
[
   {
      "method":"GET",
      "route":"/taskmetrics/",
      "total":1,
      "time":38,
      "average":38.0
   },
   {
      "method":"GET",
      "route":"/taskmetrics/stats",
      "total":1,
      "time":150,
      "average":150.0
   },
   {
      "method":"GET",
      "route":"/taskmetrics",
      "total":2,
      "time":13,
      "average":6.5
   },
   {
      "method":"POST",
      "route":"/employee/",
      "total":2,
      "time":287,
      "average":143.5
   },
   {
      "method":"DELETE",
      "route":"/employee/2",
      "total":1,
      "time":21,
      "average":21.0
   },
   {
      "method":"PUT",
      "route":"/employee/",
      "total":1,
      "time":6,
      "average":6.0
   },
   {
      "method":"GET",
      "route":"/employee/",
      "total":5,
      "time":213,
      "average":42.6
   },
   {
      "method":"DELETE",
      "route":"/employee/",
      "total":2,
      "time":18,
      "average":9.0
   }
]
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
curl -X GET -H "Content-type: application/json" http://localhost:8080/actuator/info
{
   "app":{
      "name":"taskmanager",
      "description":"A employee list API",
      "version":"0.0.1-SNAPSHOT",
      "encoding":"UTF-8",
      "java":{
         "version":"1.8.0_212"
      }
   }
}
curl -X GET -H "Content-type: application/json" http://localhost:8080/actuator/health
{
   "status":{
      "code":"UP",
      "description":""
   },
   "details":{
      "diskSpace":{
         "status":{
            "code":"UP",
            "description":""
         },
         "details":{
            "total":491180957696,
            "free":297104801792,
            "threshold":10485760
         }
      },
      "mongo":{
         "status":{
            "code":"UP",
            "description":""
         },
         "details":{
            "version":"4.0.5"
         }
      }
   }
}
curl -X GET -H "Content-type: application/json" http://localhost:8080/actuator/metrics/jvm.memory.used
{
   "name":"jvm.memory.used",
   "description":"The amount of used memory",
   "baseUnit":"bytes",
   "measurements":[
      {
         "statistic":"VALUE",
         "value":2.27852872E8
      }
   ],
   "availableTags":[
      {
         "tag":"area",
         "values":[
            "heap",
            "nonheap"
         ]
      },
      {
         "tag":"id",
         "values":[
            "Compressed Class Space",
            "PS Survivor Space",
            "PS Old Gen",
            "Metaspace",
            "PS Eden Space",
            "Code Cache"
         ]
      }
   ]
}
```
