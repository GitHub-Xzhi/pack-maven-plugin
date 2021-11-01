#!/bin/bash

APP_NAME="#appName#"
BASE_PATH="#basePath#"
APP_JAR_PATH=${BASE_PATH}/"$APP_NAME.jar"
PID_FILE=${BASE_PATH}/$APP_NAME.pid
cd ${BASE_PATH}

# 获取程序PID
getPid(){
    PID=`ps -ef|grep $APP_JAR_PATH|grep -v grep|awk '{print $2}'`
}

getPid
if [ -z $PID ];then
    echo "$APP_NAME has been stoped."
else
    echo "kill $PID"
    kill $PID
    sleep 2
    rm $PID_FILE
    getPid
    if [ $PID ];then
        echo "kill -9 $PID"
        kill -9 $PID
    fi
    echo "$APP_NAME stopped successfully."
fi