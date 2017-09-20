There is a possibility to choose the language of the output as a second argument in line command:
 By default is English
 De_de : german
 Fr_fr : french


The maven command for generating sudoku_validator-1.0-SNAPSHOT-jar-with-dependencies.jar is:
 compile assembly:single

 The maven command for generating the surfire report is:
 mvn surefire-report:report
 The report will be generated in target/surfire-reports