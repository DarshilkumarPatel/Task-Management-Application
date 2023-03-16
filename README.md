### Assignment 01 - Spam Detector 
> Course: CSCI 2020U: Software Systems Development and Integration

##Group Members

Mohammad Tasmim Abrar,
Darshil Patel,
Muhammad Ali

## Project Information
Our program should allow the user to efficiently sample through a dataset, in order to filter out spam files from ham files. Using the functions defined in our API can allow the user to use the program in order to create a data set for each individual files, and that dataset can be further combed in order to find out the frequency of occurances of each word in each file. It's really not complicated! 

## Improvements to Interface

![image](https://user-images.githubusercontent.com/90935765/225521803-b1bb21fd-06d8-4ec6-823a-c74e208459a7.png)


# Interface
We've created a sidebar, and a header, along with a footer as asked for, in the assignment pdf. We've decided to go through added trouble in order to put a shadow on the header, although seemingly a simple task, it took me over an hour to figure it out. As for the js and css, we've added functions that were related to our lab, in order to make the table import data from a json file. It may or may not have worked out. Alongside the sidebar and header shadow, we have attempted to create a pop up upon clicking the 'About Us' button, however it was a vain attempt, so I've decided to make it transluscent. Our names are now permanently fixated on the homepage :(
On the bright side, clicking on our names directly takes you to our github pages!

We've added the table, and the accuracy and precision boxes accordingly as well.

# Model

Our model is satisfactorily fast, and processing a dataset with 2751 files takes roughly 6 seconds. We've extensively researched and tried different ways to approach the problem, however the other implementations appeared to be quite long and ineffective, so we've decided to use the primary model that we came up with, which involves the processing all the files individually and getting their corresponding text, and saving them into a folder. We'll discuss all the necessary steps required to run our application in the next section.

## Instructions on Running the Program

Running our program is straightforward, and should *technically* allow you to painlessly comb through your required datasets. The procedure goes as follows:

INSTRUCTIONS FOR FILTERING FILES ON ALREADY TRAINED DATASET

- Clone our repository
- Obtain a set of data in text form, which you wish to train our program on (optional: we have our own set of trained data)
- Obtain data for testing the model.
- Once you have your testing data, paste it into 'src/main/resources/data/test/ham'.
- Run our API using IntelliJ (we assume you have already configured 'Glassfish') which is located in the 'spamDetectorServer' folder. Use the endpoints that are available, to carry out your desired tasks. (Side Note: Please refer to 'http://localhost:8080/spamDetector-1.0/api/spam' for a list of endpoints that you may access. We highly suggest going over the code in 'SpamResource.java' to have an extensive idea of how our programs work, and all the other endpoints you can access!)
- Run the Client that has been provided in the 'SpamDetectorClient' folder, by running the 'index.html' file. Our website is very user-friendly. 
- The Client should *technically* view all the Files, along with their Spam Probability Values, along with their Classifications.

INSTRUCTIONS FOR TRAINING THE MODEL ON YOUR DATASET

- Clone our repository
- Obtain a set of data in text form (you need a dataset for ham and spam individually), which you wish to train our program on
- Once you have your training data, paste it into 'src/main/resources/data/train/ham' and 'src/main/resources/data/train/spam' respectively.
- After you have the files in the required location, make sure you change the file directory inside the SpamResources.java (198) --> 'Url url' to either "/data/train/ham" or "/data/train/spam" to make sure you are looping through either ham or spam. 
-Now, deploy the server, and load the url 'http://localhost:8080/spamDetector-1.0/api/spam/createtxt' once you've done so, you will have all your txt word count data into a text file located in 'spamDetectorServer\target\spamDetector-1.0\WEB-INF\classes\data\train\ham\output'  or 'spamDetectorServer\target\spamDetector-1.0\WEB-INF\classes\data\train\spam\output' which will correspond to your modification in the previous step.
-Copy those output files and paste them into the 'src/main/resources/data/train/ham_data' or 'src/main/resources/data/train/spam_data' accordingly. 
NOTE: It is quite easy to lose track of what you are doing so please follow the steps properly.
-Now use the link "http://localhost:8080/spamDetector-1.0/api/spam/createjson" on your browser, which will return a json object. Save this file into 'train/ham_json' or 'train/spam_json'. 
-All these steps will provide the program with a reference and it'll work it's magic to show you the output.



## References 
[1] geeksforgeeks.org/

[2] https://fonts.google.com/icons

[3] https://www.w3resource.com/
