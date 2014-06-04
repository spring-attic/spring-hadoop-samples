// Setup hive directories and permissions in case they aren't there already

// use the shell (made available under variable fsh)
if (!fsh.test("/tmp")) {
  fsh.mkdir("/tmp")
  fsh.chmod("a+w", "/tmp")
}
if (!fsh.test("/user/hive/warehouse")) {
  fsh.mkdir("/user/hive/warehouse")
  fsh.chmod("a+w", "/user/hive/warehouse")
}
