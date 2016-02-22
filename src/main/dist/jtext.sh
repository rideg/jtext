#!/bin/bash

cd "${0%/*}"

JAVA_CMD=`which java` || "$JAVA_HOME/bin/java"

if [ ! -f "$JAVA_CMD" ]; then
  echo "Cannot find java executable"
  exit 1
fi

set_ld_library_path() {
    if [ "$(uname -s)" = Linux ]; then
        if [ "$(uname -i)" = x86_64 ]; then
            NATIVE_LIB=native/linux_64
        else
           NATIVE_LIB=native/linux_32
        fi
    elif [ "$(uname -s)" = Darwin ]; then
        NATIVE_LIB=native/mac_64
    fi
}



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

set_ld_library_path
export LD_LIBRARY_PATH=$NATIVE_LIB:$LD_LIBRARY_PATH

$JAVA_CMD -cp "lib/*" $DEBUG -Dlogback.configurationFile=config/logging.xml org.jtext.Main

