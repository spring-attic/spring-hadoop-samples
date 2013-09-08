Spring Yarn Multi Context Example
=================================

This example demonstrates the use of Spring Yarn functionality to
launch a simple application with multiple containers.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-multi-context:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-multi-context

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-multi-context -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

# Details

This example demonstrates how a simple custom container can be created.
Example launches 4 containers and each container will simply log
"Hello from MultiContextContainer" and exit.

# Quick Instructions

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-multi-context:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/multi-context /lib
		# hdfs dfs -copyFromLocal yarn/multi-context/build/libs/* /app/multi-context
		# hdfs dfs -copyFromLocal yarn/multi-context/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-multi-context

