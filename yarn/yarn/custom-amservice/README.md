Spring Yarn Custom Application Master Service Example
=====================================================

This example demonstrates the use of Spring Yarn functionality to create
a custom application master service utilised by containers.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-custom-amservice:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-custom-amservice

Adding parameters for job count and average count of containers

		# gradlew -q run-yarn-examples-custom-amservice -Dca.jb=10 -Dca.cc=2

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-custom-amservice -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020

# Details

Majority of other examples are just launching containers and possibly passing some extra information
either using environment variables or command line parameters. This is perfectly suiteable if task or
job container is responsible is known prior the container launch operation.

This example is using customised container, application master and application master service order to
run simple dummy jobs. Application master is setup to execute a number of jobs on number of containers.
Communication between application master and container is done via customised application master service.
Containers are homing back to application master for instruction which can either be job run requests,
requests to wait or requests to die. Container also tries to simulate error conditions by just randomly
exiting itself.

# Quick Instructions

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-custom-amservice:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/custom-amservice /lib
		# hdfs dfs -copyFromLocal yarn/custom-amservice/build/libs/* /app/custom-amservice
		# hdfs dfs -copyFromLocal yarn/custom-amservice/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-custom-amservice
