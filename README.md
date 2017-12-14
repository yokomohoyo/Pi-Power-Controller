# Pi Remote Power Controller
## RaspberryPi based remote relay management system
Currently only supported on *RaspberryPi 3 model B*

Enables control of an 8 relay module via http calls. Java based service.

#### Building
```bash
$ ./gradlew clean assembleDist
```

#### Copying artifacts
```bash
$ scp build/distributions/power-controller-0.2.zip pi@$THE_PI:~/
```

#### Starting the service
```bash
$ unzip power-controller-0.2.zip
$ ./power-controller-0.2/bin/power-controller 
05:59:50.236 [main] INFO  PowerController - Initializing Services...
05:59:50.246 [main] INFO  PowerController - Initializing GpioFactory
05:59:50.432 [main] INFO  PowerController - GpioFactory initialization complete
05:59:50.433 [main] INFO  PowerController - Initializing http listener on port: 8000 for path: /api
05:59:51.485 [main] INFO  PowerController - Ready for command...
```

#### Test toggle a random relay 1000 times
```bash
$ for k in {1..1000}; do curl "http://$THE_PI:8000/api/gpio/"$((1 + RANDOM % 8)); done
```
