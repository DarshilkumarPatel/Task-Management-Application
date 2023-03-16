package com.spamdetector.util;

import com.spamdetector.domain.TestFile;

import java.io.*;
import java.util.*;


/**
 * TODO: This class will be implemented by you
 * You may create more methods to help you organize you strategy and make you code more readable
 */
public class SpamDetector {
    public List<TestFile> trainAndTest(File mainDirectory) throws FileNotFoundException {
//        TODO: main method of loading the directories and files, training and testing the model
        File filedir = new File(mainDirectory.getAbsolutePath() + "/data/train");
        File file1 = new File(filedir + "/ham.json");
        File file2 = new File(filedir + "/spam.json");

        Scanner myReader = new  Scanner(file1);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();

        return new ArrayList<TestFile>();
    }

}
