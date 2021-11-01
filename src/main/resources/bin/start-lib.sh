#!/bin/bash

export dependencyLibList=$(cat #dependencyLibList#)
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
    nohup java -Xms#Xms#m -Xmx#Xmx#m -cp $APP_JAR_PATH:$dependencyLibList #mainClass# >/dev/null 2>err.log &
    sleep 2
    getPid
    if [ -z $PID ];then
      echo "!!! start $APP_NAME fail !!!"
    else
      echo $! > $PID_FILE
      echo "start $APP_NAME successed PID is $!"
    fi
else
    echo "$APP_NAME has been running PID is $PID.Please stop it firstly."
fi