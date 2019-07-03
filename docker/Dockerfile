FROM openjdk:8u212-jre

WORKDIR /knuca-schedule

ENV PROFILE prod
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

COPY knuca-schedule.tgz $WORKDIR/app.tgz

RUN mkdir $WORKDIR/app \
        && tar -xvzf $WORKDIR/app.tgz --strip-components=1 -C $WORKDIR/app

EXPOSE 8080

ENV NAME knuca-platform

ENTRYPOINT export KNUCA_SCHEDULE_START_OPTS="-Dspring.profiles.active=${PROFILE}" && \
$WORKDIR/app/bin/knuca-schedule-start
