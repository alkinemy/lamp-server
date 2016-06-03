USE `lamp`;

INSERT INTO `lamp_script` (`id`, `name`, `description`, `version`, `script_type`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	(1, 'DEFAULT', '', '', 'INSTALL', 'admin', '2016-03-16 21:42:51', 'admin', '2016-03-16 21:42:51');

INSERT INTO `lamp_app_install_script` (`id`, `template_id`)
VALUES
	(1, 'ba06f07e-5f5a-46ab-b929-5dec9bde9409');


INSERT INTO `lamp_script_command` (`id`, `script_id`, `seq`, `name`, `description`, `command_type`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date`)
VALUES
	(1, 1, 0, 'lamp-agent.properties', NULL, 'FILE_CREATE', 'admin', '2016-03-16 21:53:06', 'admin', '2016-03-16 21:53:06'),
	(2, 1, 1, 'lamp-agent.sh', NULL, 'FILE_CREATE', 'admin', '2016-03-16 21:53:06', 'admin', '2016-03-16 21:53:06');

INSERT INTO `lamp_script_file_create_command` (`id`, `filename`, `content`, `charset`, `expression_parser`, `readable`, `writable`, `executable`)
VALUES
	('1', 'lamp-agent.properties', 'server.port=9090\n\n# Lamp Server\nlamp.server.type=rest\nlamp.server.url=http://localhost:8080\n\n# Lamp Agent\nlamp.agent.id=dc386fc0-1319-4a7d-b218-07b5c55784ff\n\nlamp.agent.mount-point-path=/lamp/agent\nlamp.agent.secret-key=\n\nlamp.agent.metrics-sigar-enabled=true\n\nlamp.agent.monitor=true\nlamp.agent.monitor-period=5000\n\n#\n#metrics.diskspace.enabled=false\n#metrics.diskspace.path=/lamp\n\n# File Upload\n# File size limit\nmultipart.maxFileSize = 1024Mb\n# Total request size for a multipart/form-data\nmultipart.maxRequestSize = 2048Mb\n\n# Logging\nlogging.level.=INFO\nlogging.level.lamp.agent=INFO\nlogging.file=${lamp.agent.mount-point-path}/logs/lamp-agent.log\n\n# Endpoints\nendpoints.shutdown.enabled=false\n\n# Endpoints\nendpoints.shutdown.enabled=false', NULL, 'SPEL', 1, 1, 0),
	('2', 'lamp-agent.sh', '#!/bin/sh\r\n\r\nJARFile=\"lamp-agent.jar\"\r\nPIDFile=\"lamp-agent.pid\"\r\nJVM_OPTS=\"-Xms128m -Xmx256m\"\r\nSPRING_OPTS=\"-spring.config.name=lamp-agent\"\r\n\r\nfunction check_if_pid_file_exists {\r\n    if [ ! -f $PIDFile ]\r\n    then\r\n echo \"PID file not found: $PIDFile\"\r\n        exit 1\r\n    fi\r\n}\r\n\r\nfunction check_if_process_is_running {\r\n if ps -p $(print_process) > /dev/null\r\n then\r\n     return 0\r\n else\r\n     return 1\r\n fi\r\n}\r\n\r\nfunction print_process {\r\n    echo $(<\"$PIDFile\")\r\n}\r\n\r\ncase \"$1\" in\r\n  status)\r\n    check_if_pid_file_exists\r\n    if check_if_process_is_running\r\n    then\r\n      echo $(print_process)\" is running\"\r\n    else\r\n      echo \"Process not running: $(print_process)\"\r\n    fi\r\n    ;;\r\n  stop)\r\n    check_if_pid_file_exists\r\n    if ! check_if_process_is_running\r\n    then\r\n      echo \"Process $(print_process) already stopped\"\r\n      exit 0\r\n    fi\r\n    kill -TERM $(print_process)\r\n    echo -ne \"Waiting for process to stop\"\r\n    NOT_KILLED=1\r\n    for i in {1..20}; do\r\n      if [ ! -f $PIDFile ] && check_if_process_is_running\r\n      then\r\n        echo -ne \".\"\r\n        sleep 1\r\n      else\r\n        NOT_KILLED=0\r\n      fi\r\n    done\r\n    echo\r\n    if [ $NOT_KILLED = 1 ]\r\n    then\r\n      echo \"Cannot kill process $(print_process)\"\r\n      exit 1\r\n    fi\r\n    echo \"Process stopped\"\r\n    ;;\r\n  start)\r\n    if [ -f $PIDFile ] && check_if_process_is_running\r\n    then\r\n      echo \"Process $(print_process) already running\"\r\n      exit 1\r\n    fi\r\n    nohup java $JVM_OPTS -jar $JARFile $SPRING_OPTS 1>nohup.log 2>&1 &\r\n    echo \"Process started\"\r\n    ;;\r\n  restart)\r\n    $0 stop\r\n    if [ $? = 1 ]\r\n    then\r\n      exit 1\r\n    fi\r\n    $0 start\r\n    ;;\r\n  *)\r\n    echo \"Usage: $0 {start|stop|restart|status}\"\r\n    exit 1\r\nesac\r\n\r\nexit 0', NULL, 'SPEL', 1, 1, 1);
