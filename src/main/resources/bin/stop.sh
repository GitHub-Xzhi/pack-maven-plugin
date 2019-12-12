#!/bin/bash

APP_NAME=#appName#
PID_FILE=$APP_NAME\.pid

if [ -f $PID_FILE ];then
    PID=$(cat $PID_FILE)
else
    PID=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
fi

if [ -z $PID ];then
    echo "$APP_NAME has been stoped."
else
    echo "kill $PID"
    kill $PID
    sleep 2
    rm -rf $PID_FILE
    PID=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
    if [ $PID ];then
        echo "kill -9 $PID"
        kill -9 $PID
    fi
    echo "$APP_NAME stopped successfully."
fi