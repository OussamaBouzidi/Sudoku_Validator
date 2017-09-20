package org.com.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private static  ResourceBundle resourceBundle =  ResourceBundle.getBundle("messages",new Locale("en"));

    public static void main(String[] args) {

        if( args.length > 1) {
             resourceBundle =  ResourceBundle.getBundle("messages",new Locale(args[1]));
        }

        // inFile line number
        int n = 0;

        Map<Integer, String[]> lineSet = new HashMap<Integer, String[]>();

        // checking args for filename
        if(args.length == 0) {
            logAndExit(ExitCode.MISSING_FILE, "missing.file");
        }

        String filename = args[0];

        // checking file discrepancy

        File inFile = new File(filename);
        if(!inFile.exists()) {
            logAndExit(ExitCode.MISSING_FILE,"file.not.exists",  filename );
        }
        if(filename.indexOf(".") < 0){
            logAndExit(ExitCode.INCORRECT_FILE_EXTENSION, "file.invalid.extension.missing");
        }
        if(!filename.endsWith(".txt")){
            logAndExit(ExitCode.INCORRECT_FILE_EXTENSION, "file.invalid.extension", filename.substring(filename.lastIndexOf(".")));
        }

        // process the input file
        try(BufferedReader br = new BufferedReader(new FileReader(inFile))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] l = line.split(",");
                if (l.length < 9) {
                    logAndExit(ExitCode.INCORRECT_FILE_SIZE,"incorrect.file.size", String.valueOf(n) , String.valueOf(l.length));
                }

                ++n; // increment to know file line number
                // process each line to validate or invalidate if duplicate exist
                checkLine(n, line, "horizontally");
                lineSet.put(n, l);
            }


            if (n != 9) {
                logAndExit(ExitCode.INCORRECT_FILE_SIZE, "incorrect.sudoku.size" );
            }

            for(int i = 0; i < n; i++) {
                String chain = transpose(lineSet.values(), i);
                checkLine(i + 1, chain, "vertically");
            }

            logAndExit(ExitCode.SUCCESS, "file.valid");
        }catch (Exception e) {
          logger.error(e.getMessage());
        }
    }

    public static String transpose(Collection<String[]> lineSet, int i) {
        String chain = "";
        for (String[] parts : lineSet) {
            chain += (chain.isEmpty() ? "" : ",") + parts[i];
        }
        return chain;
    }

    public static void checkLine(int n, String line, String direction) {
        int lineSum = 0;

        for( String x : line.split(",")) {
            if(!Character.isDigit(x.charAt(0)) || x.length() != 1) {
                logAndExit(ExitCode.DUPLICATE_DIGIT, "incorrect.digit.format");
            }

            if(line.length() - line.replace(x, "").length() != 1) {
                logAndExit(ExitCode.DUPLICATE_DIGIT, "duplicate.digit",x.toString() ,direction, String.valueOf(n));
            }
            lineSum += Integer.parseInt(x);
        }

        if(lineSum != 45) {
            logAndExit(ExitCode.DUPLICATE_DIGIT, "ERROR    Sum on line '"+ n +"' is more or less greater than possible "+ lineSum +" instead of 45.");
        }

    }

    public static void logAndExit(ExitCode status, String key, String... args){
        String message = key;
        if(resourceBundle.containsKey(key)) {
            message = resourceBundle.getString(key);
        }
        if ( ExitCode.SUCCESS.equals(status)) {
            logger.info(message, args);
        } else {
            logger.error(message, args);
        }
        System.exit(status.getValue());
    }
}