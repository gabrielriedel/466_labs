package src.DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DocumentVector {

    private HashMap<String, Double> normalizedVector = new HashMap<String, Double>();
    private HashMap<String, Integer> docVector;

    public DocumentVector(){
        docVector = new HashMap<String, Integer>();
    }

    public Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet(){
        return normalizedVector.entrySet();
    }

    public void normalize(DocumentCollection dc){
        double tf = 0;
        int doc_count = 0;
        for(String word : docVector.keySet()){
            tf = docVector.get(word)/docVector.size();
            for(TextVector doc : dc.getDocuments()){
                if(doc.contains(word))
                doc_count += 1;
            }
            double idf = Math.log(doc_count/dc.getSize());
            normalizedVector.put(word, tf*idf);

        }
    }

    public double getNormalizedFrequency(String word){
        return normalizedVector.get(word);
    }
    
}
