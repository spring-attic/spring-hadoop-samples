drop table if exists passwords;
drop table if exists grpshell;
create table if not exists passwords (username string, passwd string, uid int, gid int, userinfo string, home string, shell string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ':' LINES TERMINATED BY '10';
load data inpath '${hiveconf:inputFile}' into table passwords;
create table if not exists grpshell (shell string, count int);
INSERT OVERWRITE TABLE grpshell SELECT p.shell, count(*) FROM passwords p GROUP BY p.shell;

