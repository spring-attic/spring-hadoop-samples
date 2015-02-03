# What you'll build

You'll build a simple Hadoop YARN application with Spring Hadoop and Spring Boot.
This application is showing how simple text file is configured and written within
a container and how containers can be controlled as groups.

# Build

Run a gradle or maven build command.

```text
$ ./gradlew clean build
```

```text
$ mvn clean package
```

# Run the application

Now that you've successfully compiled and packaged your application, it's time to do the fun part and execute it on a Hadoop YARN.

Below listing shows files after a succesfull gradle build.

```text
$ ls -lt dist/target/dist/
total 173936
-rw-rw-r-- 1 jvalkealahti jvalkealahti 61415572 Feb  3 17:48 container-0.1.0.jar
-rw-rw-r-- 1 jvalkealahti jvalkealahti 59192135 Feb  3 17:48 client-0.1.0.jar
-rw-rw-r-- 1 jvalkealahti jvalkealahti 57494159 Feb  3 17:48 appmaster-0.1.0.jar
```

Simply run your executable client jar with a shell.

```text
$ java -jar dist/target/dist/client-0.1.0.jar shell
Spring YARN Cli (v2.1.0.RELEASE)
Hit TAB to complete. Type 'help' and hit RETURN for help, and 'exit' to quit.
$
```
Push new application package into hdfs.
```text
$ push
New version installed
```

Submit new application instance.
```text
$ submit
New instance submitted with id application_1422453318707_0014
```

List running instances.
```text
$ submitted 
  APPLICATION ID                  USER          NAME               QUEUE    TYPE  STARTTIME       FINISHTIME  STATE    FINALSTATUS  ORIGINAL TRACKING URL
  ------------------------------  ------------  -----------------  -------  ----  --------------  ----------  -------  -----------  -------------------------
  application_1422453318707_0014  jvalkealahti  yarn-store-groups  default  BOOT  03/02/15 17:52  N/A         RUNNING  UNDEFINED    http://172.16.101.1:41353
```

See clusters within the app.
```text
$ clustersinfo -a application_1422453318707_0014
  CLUSTER ID
  ----------
  store
```

List cluster info.
```text
$ clusterinfo -a application_1422453318707_0014 -c store
  CLUSTER STATE  MEMBER COUNT
  -------------  ------------
  RUNNING        1
```

Modify cluster member count.
```text
$ clustermodify -a application_1422453318707_0014 -c store -w 2
Cluster store modified.
```

List cluster info.
```text
$ clusterinfo -a application_1422453318707_0014 -c store
  CLUSTER STATE  MEMBER COUNT
  -------------  ------------
  RUNNING        2
```
Checking data in hdfs i.e. using XD shell.
```text
server-unknown:>hadoop fs ls --recursive true --dir /tmp/store
drwxrwx---   - root supergroup          0 2015-02-03 18:11 /tmp/store/2015
drwxrwx---   - root supergroup          0 2015-02-03 18:11 /tmp/store/2015/02
drwxrwx---   - root supergroup          0 2015-02-03 18:11 /tmp/store/2015/02/03
drwxrwx---   - root supergroup          0 2015-02-03 18:13 /tmp/store/2015/02/03/18
drwxrwx---   - root supergroup          0 2015-02-03 18:13 /tmp/store/2015/02/03/18/11
-rw-r--r--   3 root supergroup        700 2015-02-03 18:13 /tmp/store/2015/02/03/18/11/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt
drwxrwx---   - root supergroup          0 2015-02-03 18:12 /tmp/store/2015/02/03/18/12
-rw-r--r--   3 root supergroup          0 2015-02-03 18:12 /tmp/store/2015/02/03/18/12/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt.tmp
drwxrwx---   - root supergroup          0 2015-02-03 18:13 /tmp/store/2015/02/03/18/13
-rw-r--r--   3 root supergroup          0 2015-02-03 18:13 /tmp/store/2015/02/03/18/13/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt.tmp
```

Shutdown the application.
```text
$ shutdown -a application_1422453318707_0014
shutdown requested
```
See data again.
```text
server-unknown:>hadoop fs ls --recursive true --dir /tmp/store
drwxrwx---   - root supergroup          0 2015-02-03 18:11 /tmp/store/2015
drwxrwx---   - root supergroup          0 2015-02-03 18:11 /tmp/store/2015/02
drwxrwx---   - root supergroup          0 2015-02-03 18:11 /tmp/store/2015/02/03
drwxrwx---   - root supergroup          0 2015-02-03 18:14 /tmp/store/2015/02/03/18
drwxrwx---   - root supergroup          0 2015-02-03 18:13 /tmp/store/2015/02/03/18/11
-rw-r--r--   3 root supergroup        700 2015-02-03 18:13 /tmp/store/2015/02/03/18/11/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt
drwxrwx---   - root supergroup          0 2015-02-03 18:14 /tmp/store/2015/02/03/18/12
-rw-r--r--   3 root supergroup       1200 2015-02-03 18:14 /tmp/store/2015/02/03/18/12/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt
drwxrwx---   - root supergroup          0 2015-02-03 18:14 /tmp/store/2015/02/03/18/13
-rw-r--r--   3 root supergroup       1180 2015-02-03 18:14 /tmp/store/2015/02/03/18/13/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt
drwxrwx---   - root supergroup          0 2015-02-03 18:14 /tmp/store/2015/02/03/18/14
-rw-r--r--   3 root supergroup        600 2015-02-03 18:14 /tmp/store/2015/02/03/18/14/data-7b441173-c6d7-4c65-bfcf-409ef94975b8-0.txt
```

