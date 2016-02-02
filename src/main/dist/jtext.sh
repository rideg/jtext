#!/bin/bash

cd "${0%/*}"

JAVA_CMD=`which java` || "$JAVA_HOME/bin/java"

if [ ! -f "$JAVA_CMD" ]; then
  echo "Cannot find java executable"
  exit 1
fi

#DEBUG=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=7843


clear; $JAVA_CMD -cp lib/* org.jtext.Main

