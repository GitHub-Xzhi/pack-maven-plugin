#!/bin/bash

APP_NAME="#appName#"
APP_JAR_PATH=$(pwd)/"$APP_NAME.jar"
PID_FILE=$APP_NAME.pid

# 获取程序PID
getPid(){
    PID=`ps -ef|grep $APP_JAR_PATH|grep -v grep|awk '{print $2}'`
}

if [ -f $PID_FILE ];then
    PID=$(cat $PID_FILE)
else
    getPid
fi

if [ -z $PID ];then
    echo "$APP_NAME has been stoped."
else
    echo "kill $PID"
    kill $PID
    sleep 2
    rm -rf $PID_FILE
    getPid
    if [ $PID ];then
        echo "kill -9 $PID"
        kill -9 $PID
    fi
    echo "$APP_NAME stopped successfully."
fi