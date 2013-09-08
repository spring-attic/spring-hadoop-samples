Spring Yarn Kill Application Example
====================================

This example demonstrates the use of Spring Yarn functionality to
kill an running application.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-kill-application:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-kill-application

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-kill-application -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

To run this example against local Hadoop cluster and not automatically killing the application:

		# gradlew -q run-yarn-examples-kill-application -Pnokill=true

To run this example against local Hadoop cluster and kill existing application. You can get the application id
either from Hadoop Resource Manager ui or using list-applications example:

		# gradlew -q run-yarn-examples-kill-application -Pappid=application_1377856222179_0008

# Details

This example launches an simple application with a container whose only task
is to sleep sleep 2 minutes and log a message if Application wasn't killed.
Making sure application is killed successfully, check either Hadoop scheduler GUI
or *Container.stdout* file without a log message
"Hello from KillApplicationContainer, it seems I wasn't killed.".

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-kill-application:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/kill-application /lib
		# hdfs dfs -copyFromLocal yarn/kill-application/build/libs/* /app/kill-application
		# hdfs dfs -copyFromLocal yarn/kill-application/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-kill-application

		# gradlew -q run-yarn-examples-kill-application -Pnokill=true
		# gradlew -q run-yarn-examples-list-applications
		# gradlew -q run-yarn-examples-kill-application -Pappid=<from list-applications>
