package com.spamdetector.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FileParser {
    public Map<String, Integer> wordFrequencyIndividualDir(File dir) throws IOException { //function for individual files
        // create a directory to store the output files
        File outputDir = new File(dir.getAbsolutePath() + "");
        outputDir.mkdir();

        File[] filesInDir = dir.listFiles();
        int numFiles = filesInDir.length;

        // iterate over each file in the dir and count their words
        for (int i = 0; i < numFiles; i++) {
            File inputFile = filesInDir[i];
            File outputFile = new File(outputDir.getAbsolutePath() + "/" + inputFile.getName() + ".txt");

            Map<String, Integer> wordMap = countWordFile(inputFile);

            // write the file wordMap to the output file
            try (PrintWriter writer = new PrintWriter(outputFile)) {
                for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
                    writer.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        }
        return null;
    }

    public Map<String, Integer> wordFrequencyDir(File dir) throws IOException { //function for collective files
        Map<String, Integer> frequencies = new TreeMap<>();

        File[] filesInDir = dir.listFiles();
        int numFiles = filesInDir.length;

        // iterate over each file in the dir and count their words
        for (int i = 0; i<numFiles; i++){
            Map<String, Integer> wordMap = countWordFile(filesInDir[i]);

            // merge the file wordMap into the global frequencies
            Set<String> keys = wordMap.keySet();
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()){
                String word  = keyIterator.next();
                int count = wordMap.get(word);

                if(frequencies.containsKey(word)){
                    // increment
                    int oldCount = frequencies.get(word);
                    frequencies.put(word, count + oldCount);
                }
                else{
                    frequencies.put(word, count);
                }
            }

        }

        return frequencies;
    }


    private Map<String, Integer> countWordFile(File file) throws IOException {
        Map<String, Integer> wordMap = new TreeMap<>();
        if(file.exists()){
            // load all the data and process it into words
            Scanner scanner  = new Scanner(file);
            while(scanner.hasNext()){
                // ignore the casing for words
                String word = (scanner.next()).toLowerCase();
                if (isWord(word)){
                    // add the word if not exists yet
                    if(!wordMap.containsKey(word)){
                        wordMap.put(word, 1);
                    }
                    // increment the count if exists
                    else{
                        int oldCount = wordMap.get(word);
                        wordMap.put(word, oldCount+1);
                    }
                }
            }
        }
        return wordMap;
    }

    private Boolean isWord(String word){
        if (word == null){
            return false;
        }

        String pattern = "^[a-zA-Z]*$";
        if(word.matches(pattern)){
            return true;
        }

        return false;

    }
}
