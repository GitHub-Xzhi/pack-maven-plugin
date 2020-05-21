#!/bin/bash

export dependencyLibList=$(cat #dependencyLibList#)
APP_NAME=#appName#
PID_FILE=$APP_NAME\.pid

PID=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
if [ -z $PID ];then
    nohup java -Xms#Xms#m -Xmx#Xmx#m -cp $APP_JAR_NAME:$dependencyLibList #mainClass# >/dev/null 2>err.log &
    echo $! > $PID_FILE
    echo "start $APP_NAME successed PID=$!"
else
    echo "$APP_NAME has been running PID=$PID.Please stop it firstly."
fi
