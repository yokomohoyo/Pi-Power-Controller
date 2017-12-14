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
$ 
```

#### Test toggle a random relay 1000 times
```bash
$ for k in {1..1000}; do curl "http://$THE_PI:8000/api/gpio/"$((1 + RANDOM % 8)); done
```
