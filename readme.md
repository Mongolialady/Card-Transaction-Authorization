1. Download source code, run git clone https://github.com/Mongolialady/Card-Transaction-Authorization.git
2. cd Card-Transaction-Authorization, run mvn clean install
3. To run the application, run (1) or (2)
   (1) import the project to IDE, right click and run /CardTransactionProcessing/src/main/java/com/hhy/cardtransactionprocessing/App.java
   (2) mvn exec:java -Dexec.mainClass=com.hhy.cardtransactionprocessing.App
4. From console window or terminal, input test file with full path. There is no default location for the input file
5. Output pop up on console window/terminal and current directory as output.txt

Assumption: The message type indicator of input string is always "0100"