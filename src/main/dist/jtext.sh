#!/bin/bash

cd "${0%/*}"

JAVA_CMD=`which java` || "$JAVA_HOME/bin/java"

if [ ! -f "$JAVA_CMD" ]; then
  echo "Cannot find java executable"
  exit 1
fi


while getopts ":d:k" opt; do
  case $opt in
    d)
      DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=7843
      ;;
    k)
      kill -9 `ps -ef | grep "org.jtext.Main" | grep -v  "grep" | cut -d" " -f4`
      exit 0
      ;;
  esac
done


clear; $JAVA_CMD -cp lib/* $DEBUG org.jtext.Main

