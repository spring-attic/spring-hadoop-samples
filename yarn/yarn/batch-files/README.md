Spring Yarn Batch Files Example
===============================

This example demonstrates the use of Spring Yarn functionality to process simple text files
via Spring Batch Job utilising Yarn cluster resources by partitioning steps to run
on different containers.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-batch-files:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-batch-files
		# gradlew -q run-yarn-examples-batch-files -Dfiles=/path/hdfs/files

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-batch-files -Dhd.fs=hdfs://192.168.223.170:8020 -Dhd.rm=192.168.223.170:8032 -Dlocalresources.remote=hdfs://192.168.223.170:8020


# Details

Processing a simple text files in map reduce has always been a little cumbersome especially if only
thing you need from it is to just read lines and then do something about that data. Doing something
non map reduce in hadoop involves creation of dummy tasks. You need to create map tasks order
to utilize hadoop's functionality to split hdfs files into different processing units and then
forget even using reduce tasks.

In this example we use 3 different data files in hdfs.
```
/syarn-tmp/batch-files/set1/data1.txt
/syarn-tmp/batch-files/set1/data2.txt
/syarn-tmp/batch-files/set1/data3.txt
```

There are 100000 lines of data on each file and content is prefixed to have unique
set of data throughout the files.
```
data1.txt:
data1-line-1
data1-line-2
data1-line-3
...
data2.txt:
data2-line-1
data2-line-2
data2-line-3
...
```

You can create these files using a simple bash command.
```
# for i in $(seq 1 100000); do echo "data1-line-$i"; done >> data1.txt
```

This example is using normal Spring Yarn Batch functionality to parallelise processing
of these data files. For simplify in this batch example we just passthrough processed lines
and write those back to a log file. This is enough to verify correct processing of data
using real spring batch job.

Input file set is defined in *application-context.xml* which you can simply override
using *-Dfiles=/path/to/files* command line parameter.
```xml
<util:properties id="customJobParameters">
  <prop key="input">${files:/syarn-tmp/batch-files/set1/*}</prop>
</util:properties>
```

On default file input split equals to HDFS block size but because these files are
so small, we force 2 splits in *appmaster-context.xml*. Effectively because we have 3 files,
3 nodes and 2 splits, we should end up having 9 containers split evenly in our cluster.
```
centos:
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000010/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000004/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000007/Container.stdout
centos1:
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000003/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000009/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000006/Container.stdout
centos2:
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000008/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000005/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000002/Container.stdout
hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_000001/Appmaster.stdout
```

If we now grep writes to those files it should equal 300000.
```
[root@centos hadoop]# grep writing hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_0000*/Container.stdout | wc
  99999  799992 18988706
[root@centos1 hadoop]# grep writing hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_0000*/Container.stdout | wc
  100654  805232 19102048
[root@centos2 hadoop]# grep writing hadoop/logs/userlogs/application_1368793613271_0063/container_1368793613271_0063_01_0000*/Container.stdout | wc
  99347  794776 18875931
```

This quick command set assumes local hadoop cluster with default settings.

		# gradlew :yarn-examples-common:yarn-examples-batch-files:build -x test
		# hdfs dfs -rm -R /app /lib
		# hdfs dfs -mkdir -p /app/batch-files /lib
		# hdfs dfs -copyFromLocal yarn/batch-files/build/libs/* /app/batch-files
		# hdfs dfs -copyFromLocal yarn/batch-files/build/dependency-libs/* /lib
		# gradlew -q run-yarn-examples-batch-files
