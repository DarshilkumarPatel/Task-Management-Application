package com.spamdetector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spamdetector.domain.TestFile;
import com.spamdetector.util.SpamDetector;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import jakarta.ws.rs.core.Response;

@Path("/spam")
public class SpamResource {

    private String readFileContents(String filename) {

        String fp = SpamResource.class.getResource(filename).toString();
        fp = fp.substring(fp.indexOf('/') + 1);

        try {
            java.nio.file.Path file = java.nio.file.Path.of(fp);
            return Files.readString(file);
        } catch (IOException e) {
            // something went wrong
            return "Did you forget to create the file?\n" +
                    "Is the file in the right location?\n" + e;
        }
    }
    @GET
    @Produces("text/html")
    /**
     * Endpoint URL: http://localhost:8080/spamDetector-1.0/api/spam
     * **/
    public String rootEndpoint() {
        String res = "Available endpoints are: <br>"+
                "api/spam/createtxt -- creates a text file for individual files containing word counts<br>"+
                "api/read/createjson -- returns a json file on the server, containing the number of files that contains each individual words. This is saved and placed into the 'data/train/ham_json' directory.";

        return res;

    }
//    your SpamDetector Class responsible for all the SpamDetecting logic
    SpamDetector detector = new SpamDetector();
    ObjectMapper mapper = new ObjectMapper();

    List<TestFile> SpamResource() throws FileNotFoundException, URISyntaxException {
//        TODO: load resources, train and test to improve performance on the endpoint calls
        System.out.print("Training and testing the model, please wait");

//      TODO: call  this.trainAndTest();
        return this.trainAndTest();
    }
    @GET
    @Produces("application/json")
    public Response getSpamResults() {
//       TODO: return the test results list of TestFile, return in a Response object

        String content = this.readFileContents("/data/test/test_spam.json");

        Response myResp = Response.status(200).header("Access-Control-Allow-Origin","http://localhost:63342")
                .header("Content-Type","application/json")
                .entity(content)
                .build();
        return null;
    }


    @GET
    @Path("/accuracy")
    @Produces("application/json")
    public <JsonObject> Response getAccuracy() throws IOException {
        // TODO: return the accuracy of the detector, return in a Response object

        URL url = this.getClass().getClassLoader().getResource("/data/train/spam_data");
        File data = null;
        try {
            data = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        FileParser myParser = new FileParser();
        // call function from parser to calculate the freq of words in text
        Map<String, Integer> freq = myParser.wordFrequencyDir(data);

        // TODO: calculate the accuracy of the detector based on the frequency of words in the file
//        double accuracy = calculateAccuracy(freq);

        // Construct a JSON object with the accuracy value
//        JsonObject responseJson = Json.createObjectBuilder()
//                .add("accuracy", accuracy)
//                .build();

        // Construct a Response object with the JSON object as the entity
        Response myResp = Response.status(200)
                .header("Access-Control-Allow-Origin", "http://localhost:8448")
                .header("Content-Type", "application/json")
                .build();

        return myResp;
    }

    private double calculateAccuracy(String spamFilePath, String hamFilePath) {
        FileParser parser = new FileParser();
        Map<String, Integer> spamWordFreq = parser.parse("/data/train/spam_data");
        Map<String, Integer> hamWordFreq = parser.parse("/data/train/ham_data");

        // Calculate the total number of spam and ham messages
        int totalSpam = spamWordFreq.size();
        int totalHam = hamWordFreq.size();

        // Calculate the number of messages correctly classified as spam and ham
        int spamCount = 0;
        int hamCount = 0;

        // Calculate the number of ham messages correctly classified
        for (Map.Entry<String, Integer> entry : hamWordFreq.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();

            // Check if the word is in the spam messages
            if (spamWordFreq.containsKey(word)) {
                hamCount += Math.min(freq, spamWordFreq.get(word));
            }
        }

        // Calculate the accuracy
        double accuracy = (double) (spamCount + hamCount) / (totalSpam + totalHam);

        return accuracy;
    }

    @GET
    @Path("/precision")
    @Produces("application/json")
    public <JsonObject> Response getPrecision() {
        // TODO: return the precision of the detector, return in a Response object

        // Path to the spam and ham files
        String spamFilePath = "/data/train/spam_data";
        String hamFilePath = "/data/train/ham_data";

        // Calculate the precision
        double precision = calculatePrecision(spamFilePath, hamFilePath);

        // Create a JSON object with the precision value
//        JsonObject jsonObject = Json.createObjectBuilder()
//                .add("precision", precision)
//                .build();

        // Return the JSON object in a Response
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:8448")
                .build();
    }

    private double calculatePrecision(String spamFilePath, String hamFilePath) {
        FileParser parser = new FileParser();
        Map<String, Integer> spamWordFreq = parser.parse("/data/train/spam_data");
        Map<String, Integer> hamWordFreq = parser.parse("/data/train/ham_data");

        // Calculate the number of spam messages
        int spamCount = spamWordFreq.size();

        // Calculate the number of spam messages correctly classified
        int truePositiveCount = 0;
        for (Map.Entry<String, Integer> entry : spamWordFreq.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();

            // Check if the word is in the ham messages
            if (hamWordFreq.containsKey(word)) {
                truePositiveCount += freq;
            }
        }

        // Calculate the precision
        double precision = (double) truePositiveCount / spamCount;

        return precision;
    }

    @GET
    @Produces("application/json")
    @Path("/createtxt")
    /**
     * Endpoint URL: http://localhost:8080/spamDetector-1.0/api/spam/createtxt
     * **/
    public Response readData() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("/data/train/ham");
        File data = null;
        try {
            data = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        FileParser myParser = new FileParser();
        // call function from parser to calculate the freq of words in text
        Map<String, Integer> freq = myParser.wordFrequencyIndividualDir(data);

        Response myResp = Response.status(200).header("Access-Control-Allow-Origin", "http://localhost:8448")
                .header("Content-Type", "application/json")
                .entity(mapper.writeValueAsString(freq))
                .build();

        return myResp;
    }

    @GET
    @Produces("application/json")
    @Path("/createjson")
    /**
     * Endpoint URL: http://localhost:8080/spamDetector-1.0/api/spam/createjson
     * **/
    public Response readJsonData() throws IOException {
        URL resource = this.getClass().getClassLoader().getResource("/data/train/ham_data/");
        File data = null;
        //try catch block
        try {
            data = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        FileParser myParser = new FileParser();
        // call function from parser to calculate the freq of words in text
        Map<String, Integer> freq = myParser.wordFrequencyDir(data);

        Response myResp = Response.status(200).header("Access-Control-Allow-Origin", "http://localhost:8448")
                .header("Content-Type", "application/json")
                .entity(mapper.writeValueAsString(freq))
                .build();

        return myResp; //return myResp with CORS enabled in the header
    }

    private List<TestFile> trainAndTest() throws URISyntaxException, FileNotFoundException {
        if (this.detector==null){
            this.detector = new SpamDetector();
        }

//        TODO: load the main directory "data" here from the Resources folder
        //we get the resource file location (resource/data)
        URL resource = getClass().getClassLoader().getResource("/data");

        return this.detector.trainAndTest(new File(resource.toURI()));
    }
}