//requires outputDir

// use the shell (made available under variable fsh)
println "RESULTS from " + outputDir
old = new File('results.txt')
if( old.exists() ) {
    old.delete()
}
fsh.get(outputDir + '/*', 'results.txt');
String fileContents = new File('results.txt').text
println fileContents
