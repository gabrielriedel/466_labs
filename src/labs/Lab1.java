package src.labs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import src.DocumentClasses.DocumentCollection;
import src.DocumentClasses.TextVector;

public class Lab1 {

    public static void main(String args[]){

        DocumentCollection docs = new DocumentCollection("./files/documents.txt");

        int maxFreq = 0;
        String mostFreqWord = "";
        int distinctWordCount = 0;
        int totalWordCount = 0;
        for(TextVector text: docs.getDocuments()){
            distinctWordCount += text.getDistinctWordCount(); 
            totalWordCount += text.getTotalWordCount();
            if(text.getHighestRawFrequency() > maxFreq){
                maxFreq = text.getHighestRawFrequency();
                mostFreqWord = text.getMostFrequentWord();
            }
        }

        System.out.println("Word = " + mostFreqWord);
        System.out.println("Frequency = " + maxFreq);
        System.out.println("Distinct Number of Words = " + distinctWordCount);
        System.out.println("Total word count = " + totalWordCount);


        try(ObjectOutputStream os = new ObjectOutputStream(new
                    FileOutputStream(new File("./files/docvector")))){
        os.writeObject(docs);
        } catch(Exception e){
            System.out.println(e);
            }
    }
    
}
