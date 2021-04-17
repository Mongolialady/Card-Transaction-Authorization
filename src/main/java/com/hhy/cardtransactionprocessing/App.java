package com.hhy.cardtransactionprocessing;

import processor.MessageProcessor;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        App app = new App();
        System.out.println("Please input file name, then press ENTER key .....");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        String fileName = reader.readLine();
        app.readFile(fileName);
    }

    private void readFile(String fileName) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resource/output.txt"));
        String s = bufferedReader.readLine();
        while(s != null) {
            MessageProcessor msgprocessor = new MessageProcessor();
            String msg = msgprocessor.processMessage(s);
            System.out.println(msg);
            bufferedWriter.write(msg + "\n");
            s = bufferedReader.readLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
