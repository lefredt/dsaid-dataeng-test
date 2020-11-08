FROM openjdk:11

ENV SBT_VERSION 1.4.1
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  cd && \
  sbt sbtVersion

WORKDIR /DemoETL
ADD . /DemoETL

RUN rm -r /DemoETL/database/ /DemoETL/system-design /DemoETL/dataset_clean.csv

CMD sbt pipeline/run