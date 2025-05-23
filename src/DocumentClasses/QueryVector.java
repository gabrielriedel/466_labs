package src.DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.lang.Math;

public class QueryVector extends TextVector{

    private HashMap<String, Double> normalizedVector = new HashMap<String, Double>();

    public Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet(){
        return normalizedVector.entrySet();
    }

    public void normalize(DocumentCollection dc){
        double tf = 0.0;
        double idf = 0.0;
        String word;
        for(Map.Entry<String,Integer> entry : super.getRawVectorEntrySet()){
            word = entry.getKey();
            tf = 0.5+0.5*((double) super.getRawFrequency(word)/super.getHighestRawFrequency());
            int df = dc.getDocumentFrequency(word);
            if (df == 0) continue;
            else{
                idf = Math.log((double)dc.getSize()/df)/Math.log(2);
                normalizedVector.put(word, tf*idf);
            }
        }
    }

    public double getNormalizedFrequency(String word){
        return normalizedVector.get(word);
    }

}
