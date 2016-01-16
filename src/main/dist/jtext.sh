#!/bin/bash

cd "${0%/*}"

JAVA=`which java` || "$JAVA_HOME/bin/java"

if [ ! -f "$JAVA" ]; then
  echo "Cannot find java executable"
  exit 1
fi

$JAVA -cp lib/* org.jtext.Main

