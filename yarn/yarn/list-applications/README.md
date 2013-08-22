Spring Yarn List Applications Example
=====================================

This example demonstrates the use of Spring Yarn functionality to
use Client and list running applications.

To test this example:

		./gradlew clean :yarn-examples-common:yarn-examples-list-applications:build

To run this example against local Hadoop cluster:

		./gradlew -q run-yarn-examples-list-applications

To run this example against remote Hadoop cluster:

		./gradlew -q run-yarn-examples-list-applications -Dhd.rm=192.168.223.170:8032

# Details

With a successful execution you should see something like this
in a console:

```
Listing Applications:
 Id | User | Name | Queue | StartTime | FinishTime | State | FinalStatus
------------------------------------------------------------------------
application_1377153160480_0003 | jvalkeal | batch-amjob | default | 8/22/13 7:56 AM | 8/22/13 7:56 AM | FINISHED | SUCCEEDED
application_1377153160480_0002 | jvalkeal | batch-amjob | default | 8/22/13 7:44 AM | 8/22/13 7:44 AM | FINISHED | SUCCEEDED
application_1377153160480_0008 | jvalkeal | batch-files | default | 8/22/13 2:04 PM | 8/22/13 2:07 PM | FINISHED | SUCCEEDED
application_1377153160480_0001 | jvalkeal | batch-amjob | default | 8/22/13 7:40 AM | 8/22/13 7:40 AM | FAILED | FAILED
application_1377153160480_0007 | jvalkeal | batch-files | default | 8/22/13 12:45 PM | 8/22/13 12:46 PM | FINISHED | SUCCEEDED
application_1377153160480_0006 | jvalkeal | simple-command | default | 8/22/13 8:57 AM | 8/22/13 8:58 AM | FINISHED | SUCCEEDED
application_1377153160480_0005 | jvalkeal | simple-command | default | 8/22/13 8:53 AM | 8/22/13 8:53 AM | FAILED | FAILED
```
