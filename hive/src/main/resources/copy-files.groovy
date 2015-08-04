//requires three variables, localSourceFile and hdfsInputDir, hdfsOutputDir 

// use the shell (made available under variable fsh)

if (!fsh.test(inputDir)) {
   fsh.mkdir(inputDir);
   fsh.chmod(700, inputDir)
}
fsh.rm(inputDir+"/*");
fsh.copyFromLocal(localSourceFile, inputDir);
