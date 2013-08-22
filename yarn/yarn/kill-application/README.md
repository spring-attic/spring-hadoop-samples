Spring Yarn Kill Application Example
====================================

This example demonstrates the use of Spring Yarn functionality to
kill an running application.

To test this example:

		./gradlew clean :yarn-examples-common:yarn-examples-kill-application:build

To run this example against local Hadoop cluster:

		./gradlew -q run-yarn-examples-kill-application

To run this example against remote Hadoop cluster:

		./gradlew -q run-yarn-examples-kill-application -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

# Details

This example launches an simple application with a container whose only task
is to sleep sleep 2 minutes and log a message if Application wasn't killed.
Making sure application is killed successfully, check either Hadoop scheduler GUI
or *Container.stdout* file without a log message
"Hello from KillApplicationContainer, it seems I wasn't killed.".