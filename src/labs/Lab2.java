package src.labs;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;

import src.DocumentClasses.CosineDistance;
import src.DocumentClasses.DocumentCollection;
import src.DocumentClasses.TextVector;

public class Lab2 {
    
    public static src.DocumentClasses.DocumentCollection documents;
    public static src.DocumentClasses.DocumentCollection queries;

    public static void main(String[] args){
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./files/docvector")))) {
            documents = (DocumentCollection) is.readObject();
        } 
        catch (Exception e) {
            System.out.println(e);
        }

        queries = new DocumentCollection("./files/queries.txt", "query");
        queries.normalize(documents);
        documents.normalize(documents);

        CosineDistance distanceAlg = new CosineDistance();
        HashMap<Integer, ArrayList<Integer>> closeDocs = new HashMap<>();
        for(Map.Entry<Integer, TextVector> entry : queries.getEntrySet()){
            TextVector query = entry.getValue();
            closeDocs.put(entry.getKey(), query.findClosestDocuments(documents, distanceAlg));
        }

        for(Map.Entry<Integer, ArrayList<Integer>> entry : closeDocs.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }

}
