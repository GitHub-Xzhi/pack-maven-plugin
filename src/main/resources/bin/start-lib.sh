#!/bin/bash

export dependencyLibList=$(cat #dependencyLibList#)
APP_NAME="#appName#"
APP_JAR_PATH=$(pwd)/"$APP_NAME.jar"
PID_FILE=$APP_NAME.pid

PID=`ps -ef|grep $APP_JAR_PATH|grep -v grep|awk '{print $2}'`
if [ -z $PID ];then
    nohup java -Xms#Xms#m -Xmx#Xmx#m -cp $APP_JAR_PATH:$dependencyLibList #mainClass# >/dev/null 2>err.log &
    echo $! > $PID_FILE
    echo "start $APP_NAME successed PID=$!"
else
    echo "$APP_NAME has been running PID=$PID.Please stop it firstly."
fi
