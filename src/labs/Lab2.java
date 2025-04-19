package src.labs;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import src.DocumentClasses.DocumentCollection;

public class Lab2 {
    
    public static src.DocumentClasses.DocumentCollection documents;
    public static src.DocumentClasses.DocumentCollection queries;

    public static void main(String[] args){
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./files/docvector")))) {
            DocumentCollection documentVectors = (DocumentCollection)is.readObject();
        } 
        catch (Exception e) {
            System.out.println(e);
        }

    }

}
