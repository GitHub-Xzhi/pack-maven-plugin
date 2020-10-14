#! /bin/shell

APP_NAME="#appName#"
APP_JAR_PATH=$(pwd)/"$APP_NAME.jar"
PID_FILE=$APP_NAME.pid

#使用说明
usage() {
    echo "Usage:"
    echo "sh $0 [start|stop|restart|status]"
    echo "OR"
    echo "sh $0 [0|1|2|3] "
    echo "0:start 1:stop 2:restart 3:status"
    exit 1
}

# 获取程序PID
getPid(){
    PID=`ps -ef|grep $APP_JAR_PATH|grep -v grep|awk '{print $2}'`
}

# 启动
start() {
    getPid
    if [ -z $PID ];then
        nohup java -Xms#Xms#m -Xmx#Xmx#m -jar $APP_JAR_PATH >/dev/null 2>err.log &
        echo $! > $PID_FILE
        echo "start $APP_NAME successed PID=$!"
    else
        echo "$APP_NAME has been running PID=$PID.Please stop it firstly."
    fi
}

# 停止
stop() {
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
}

# 重启
restart() {
    echo stop $APP_NAME Application...
    stop
    echo start $APP_NAME Application...
    start
}

#输出运行状态
status(){
  getPid
  if [ -z $PID ]; then
    echo ">>> $APP_NAME is not running <<<"
  else
    echo ">>> $APP_NAME is running PID is $PID <<<"
  fi
}

#执行选项
execOption() {
    case "$option" in
    "start"|"0")
        start
        ;;
    "stop"|"1")
        stop
        ;;
    "restart"|"2")
        restart
        ;;
    "status"|"3")
        status
        ;;
    *)
        usage
        ;;
    esac
    exit 0
}

if [ ! -n "$1" ] ;then
    restart
else
    option=$1
    execOption
fi