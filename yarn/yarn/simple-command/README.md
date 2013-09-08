Spring Yarn Simple Command Example
==================================

This example demonstrates the use of Spring Yarn functionality to
launch a simple application with multiple containers.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-simple-command:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-simple-command

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-simple-command -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

# Details

This example demonstrates how a simple custom container can be created.
Example launches 4 containers and each container will simply log
an output of *date* command and exit.

This is one of the most simplistic examples to run something on Yarn.
Containers which are launched are simply an placeholder to run commands
on OS.

# Quick Instructions

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-simple-command:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/simple-command /lib
		# hdfs dfs -copyFromLocal yarn/simple-command/build/libs/* /app/simple-command
		# hdfs dfs -copyFromLocal yarn/simple-command/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-simple-command
