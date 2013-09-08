Spring Yarn Restart Context Example
===================================

This example demonstrates the use of Spring Yarn functionality to
use custom application master and container handling restart
of failed container.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-restart-context:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-restart-context

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-restart-context -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

# Details

This example demonstrates how a failed container with abnormal
exit code can be handled in custom application master and
request a new container launch.

# Quick Instructions

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-restart-context:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/restart-context /lib
		# hdfs dfs -copyFromLocal yarn/restart-context/build/libs/* /app/restart-context
		# hdfs dfs -copyFromLocal yarn/restart-context/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-restart-context
