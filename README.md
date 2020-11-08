## Task 1 Data Pipelines

#### Copy of transformed csv
See `dataset_clean.csv`

#### Running the service

Build docker image with Dockerfile on the root path
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

## Task 2: Databases
The initial create table sql scripts are found in `database/database-script/CreateFirst.sql`  
An ER Diagram auto generated with [DBeaver](https://dbeaver.io/) can be seen at `database/er-diag.png`

Build docker image with Dockerfile on the `database` folder
```
docker build -t demo_postgres .
```
Start Postgres
```
docker run -d --name pg-demo -p 5432:5432 demo_postgres
```

## Task 3: System Design
Powerpoint diagram is located at `system-design/SystemArch.pptx`