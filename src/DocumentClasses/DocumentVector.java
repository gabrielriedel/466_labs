package src.DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DocumentVector extends TextVector{

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
            tf = ((double) super.getRawFrequency(word))/super.getDistinctWordCount();
            int df = dc.getDocumentFrequency(word);
            if (df == 0) continue;
            else{
                idf = Math.log((double)dc.getSize()/df)/Math.log(2);
                normalizedVector.put(word, tf*idf);
            }
        }
    }

    public double getNormalizedFrequency(String word){
        return normalizedVector.getOrDefault(word, 0.0);
    }
}
