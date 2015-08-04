
ADD JAR ${hiveconf:hiveContribJar};

DROP TABLE apachelog;

CREATE TABLE apachelog(remoteHost STRING, remoteLogname STRING, userName STRING, time STRING, method STRING, uri STRING, proto STRING, status STRING, bytes STRING, referer STRING,  userAgent STRING) ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.RegexSerDe' WITH SERDEPROPERTIES (  "input.regex" = "^([^ ]*) +([^ ]*) +([^ ]*) +\\[([^]]*)\\] +\\\"([^ ]*) ([^ ]*) ([^ ]*)\\\" ([^ ]*) ([^ ]*) (?:\\\"-\\\")*\\\"(.*)\\\" (.*)$", "output.format.string" = "%1$s %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s %10$s %11$s" ) STORED AS TEXTFILE;

-- If using variables and executing from HiveTemplate (vs HiveRunner), need to put quotes around the variable name.
LOAD DATA INPATH ${hiveconf:localInPath} INTO TABLE apachelog;

-- determine popular URLs
INSERT OVERWRITE DIRECTORY '/user/hive/output/uri_hits' SELECT a.uri, "\t", COUNT(*) FROM apachelog a GROUP BY a.uri ORDER BY uri;
