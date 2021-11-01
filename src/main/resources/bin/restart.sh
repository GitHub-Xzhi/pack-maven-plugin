#! /bin/shell

APP_NAME="#appName#"
BASE_PATH="#basePath#"
cd ${BASE_PATH}

echo stop $APP_NAME Application...
sh stop.sh

echo start $APP_NAME Application...
sh start.sh
