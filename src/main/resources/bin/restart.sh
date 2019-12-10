#! /bin/shell

APP_NAME="#appName#"

echo stop $APP_NAME Application...
sh stop.sh

echo start $APP_NAME Application...
sh start.sh
