#### Copy of transformed csv
dataset_clean.csv

#### Running the service

Build docker image with Dockerfile
```
docker build -t dsaid:v1 .
```

Run first time (Output file to `~/Data-Test/dataset_clean.csv`)
```
docker run --name dsaid-test -v $HOME/Data-Test:/data dsaid:v1
```

Copy docker restart bash script
```
cp cron-dsaid-test.sh <Your folder of choice>
```

Set up cron job to run daily at 2am
```
crontab -e
0 2 * * * <Absolute path to folder of choice>/cron-dsaid-test.sh
```
