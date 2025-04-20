package src.DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.lang.Math;

public class QueryVector extends TextVector{

    private HashMap<String, Double> normalizedVector = new HashMap<String, Double>();
    private HashMap<String, Integer> queryVector;

    public QueryVector(){
        queryVector = new HashMap<String, Integer>();
    }

    public Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet(){
        return normalizedVector.entrySet();
    }

    public void normalize(DocumentCollection dc){
        double tf = 0.5;
        int doc_count = 0;
        for(String word : queryVector.keySet()){
            tf += 0.5*(queryVector.get(word)/queryVector.size());
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
