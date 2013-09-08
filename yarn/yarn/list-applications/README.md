Spring Yarn List Applications Example
=====================================

This example demonstrates the use of Spring Yarn functionality to
use Client and list running applications.

To test this example:

		# gradlew clean :yarn-examples-common:yarn-examples-list-applications:build

To run this example against local Hadoop cluster:

		# gradlew -q run-yarn-examples-list-applications

To run this example against remote Hadoop cluster:

		# gradlew -q run-yarn-examples-list-applications -Dhd.rm=192.168.223.170:8032

# Details

With a successful execution you should see something like this
printed in a console:

```
  Id                              User      Name              Queue    StartTime         FinishTime        State     FinalStatus
  ------------------------------  --------  ----------------  -------  ----------------  ----------------  --------  -----------
  application_1377856222179_0005  jvalkeal  kill-application  default  8/30/13 12:20 PM  8/30/13 12:20 PM  KILLED    KILLED
  application_1377856222179_0001  jvalkeal  multi-context     default  8/30/13 10:57 AM  8/30/13 10:58 AM  FINISHED  SUCCEEDED
  application_1377856222179_0009  jvalkeal  kill-application  default  8/30/13 12:57 PM  8/30/13 12:58 PM  KILLED    KILLED
  application_1377856222179_0002  jvalkeal  multi-context     default  8/30/13 11:02 AM  8/30/13 11:03 AM  FINISHED  SUCCEEDED
```

# Quick Instructions

This quick command set assumes local hadoop cluster with default settings.

		# gradlew -q run-yarn-examples-list-applications
