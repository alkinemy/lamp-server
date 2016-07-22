#!/bin/bash

JARFile="lamp-agent.jar"
PIDFile="lamp-agent.pid"
JVM_OPTS="-Xms256m -Xmx1024m"
SPRING_OPTS="--spring.pidfile=lamp-agent.pid --spring.config.name=lamp-agent"

function check_if_pid_file_exists {
    if [ ! -f $PIDFile ]
    then
        return 1
    else
        PID=$(print_process)
        return 0
    fi
}

function check_if_process_is_running {
 if ps -p $PID > /dev/null
 then
     return 0
 else
     return 1
 fi
}

function print_process {
    echo $(<"$PIDFile")
}

case "$1" in
  status)
    if check_if_pid_file_exists && check_if_process_is_running
    then
      echo $PID" is running"
    else
      echo "Process not running"
    fi
    ;;
  stop)
    if ! check_if_pid_file_exists || ! check_if_process_is_running
    then
      echo "Process $PID already stopped"
      exit 0
    fi
    kill -TERM $PID
    echo -ne "Waiting for process to stop"
    NOT_KILLED=1
    for i in {1..60}; do
      if  check_if_process_is_running
      then
        echo -ne "."
        sleep 1
      else
        NOT_KILLED=0
      fi
    done
    echo
    if [ $NOT_KILLED = 1 ]
    then
      echo "Cannot kill process $PID"
      exit 1
    fi
    echo "Process stopped"
    ;;
  start)
    if check_if_pid_file_exists && check_if_process_is_running
    then
      echo "Process $PID already running"
      exit 1
    fi
    nohup java $JVM_OPTS -jar $JARFile $SPRING_OPTS 1>stdout.log 2>stderr.log &
    echo "Process started"
    ;;
  restart)
    $0 stop
    if [ $? = 1 ]
    then
      exit 1
    fi
    $0 start
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 1
esac

exit 0