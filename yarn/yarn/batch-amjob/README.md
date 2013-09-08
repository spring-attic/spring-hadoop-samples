Spring Yarn Batch Application Master Job Example
================================================

This example demonstrates the use of Spring Yarn functionality to run
Spring Batch jobs on an Application Master without starting
any Containers.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-batch-amjob:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-batch-amjob
		# gradlew -q run-yarn-examples-batch-amjob -DjobName=job2

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-batch-amjob -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

# Details

This is a simplistic example using Spring Batch without adding any
complex configuration logic for partitioned steps on a Yarn cluster.
To verify that job was executed, see that the *Appmaster.stdout*
file contains log message "INFO [PrintTasklet] - execute: Hello1".

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-batch-amjob:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/batch-amjob /lib
		# hdfs dfs -copyFromLocal yarn/batch-amjob/build/libs/* /app/batch-amjob
		# hdfs dfs -copyFromLocal yarn/batch-amjob/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-batch-amjob
