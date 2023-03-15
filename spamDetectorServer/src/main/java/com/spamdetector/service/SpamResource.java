package com.spamdetector.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spamdetector.domain.TestFile;
import com.spamdetector.util.SpamDetector;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import jakarta.ws.rs.core.Response;

@Path("/spam")
public class SpamResource {

    @GET
    @Produces("text/html")
    /**
     * Endpoint URL: http://localhost:8080/spamDetector-1.0/api/spam
     * **/
    public String rootEndpoint() {
        String res = "Available endpoints are: <br>"+
                "api/read/{colName} -- return the average grade for the given column name <br>"+
                "api/read/book -- returns frequency of words in the text documents";
        return res;

    }
//    your SpamDetector Class responsible for all the SpamDetecting logic
    SpamDetector detector = new SpamDetector();
    ObjectMapper mapper = new ObjectMapper();

    SpamResource(){
//        TODO: load resources, train and test to improve performance on the endpoint calls
        System.out.print("Training and testing the model, please wait");

//      TODO: call  this.trainAndTest();

    }
    @GET
    @Produces("application/json")
    public Response getSpamResults() {
//       TODO: return the test results list of TestFile, return in a Response object

//        Response myResp = Response.status(200).header("Access-Control-Allow-Origin","http://localhost:63342")
//                .header("Content-Type","application/json")
//                .entity(val)
//                .build();
        return null;
    }

    @GET
    @Path("/accuracy")
    @Produces("application/json")
    public Response getAccuracy() {
//      TODO: return the accuracy of the detector, return in a Response object

        return null;
    }

    @GET
    @Path("/precision")
    @Produces("application/json")
    public Response getPrecision() {
       //      TODO: return the precision of the detector, return in a Response object

        return null;
    }

    @GET
    @Produces("application/json")
    @Path("/view")
    /**
     * Endpoint URL: http://localhost:8080/spamDetector-1.0/api/spam/view
     * **/
    public Response readBook() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("/data/test/ham_data");
        File data = null;
        try {
            data = new File(url.toURI());
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

        return myResp;
    }

    private List<TestFile> trainAndTest()  {
        if (this.detector==null){
            this.detector = new SpamDetector();
        }

//        TODO: load the main directory "data" here from the Resources folder
        File mainDirectory = null;
        return this.detector.trainAndTest(mainDirectory);
    }
}