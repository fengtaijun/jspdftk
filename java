#!/bin/bash

JAVACMD=java
JAVAOPTS=-Xmx2000m
export IFS=$'\n'

if [ -z "$PRG_HOME" -o ! -d "$PRG_HOME" ] ; then
  ## resolve links - $0 may be a link to program's home
  PRG="$0"
  progname=`basename "$0"`

  # need this for relative symlinks
  while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
      PRG="$link"
    else
      PRG=`dirname "$PRG"`"/$link"
    fi
  done

  PRG_HOME=`dirname "$PRG"`

  # make it fully qualified
  PRG_HOME=`cd "$PRG_HOME" && pwd`
fi

DIRLIBS=${PRG_HOME}/lib/*.jar
for i in ${DIRLIBS}
do
    # if the directory is empty, then it will return the input string
    # this is stupid, so case for it
    if [ "$i" != "${DIRLIBS}" ] ; then
      if [ -z "$LOCALCLASSPATH" ] ; then
        LOCALCLASSPATH=$i
      else
        LOCALCLASSPATH="$i":$LOCALCLASSPATH
      fi
    fi
done

exec "$JAVACMD" $JAVAOPTS $LOGCHOICE $LOGLEVEL -classpath "$LOCALCLASSPATH" $*
