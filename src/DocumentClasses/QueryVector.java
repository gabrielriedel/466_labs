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
        double doc_count;
        String word;
        for(Map.Entry<String,Integer> entry : super.getRawVectorEntrySet()){
            doc_count = 0.0;
            word = entry.getKey();
            tf = 0.5+0.5*(super.getRawFrequency(word)/super.getDistinctWordCount());
            for(TextVector doc : dc.getDocuments()){
                if(doc.contains(word))
                doc_count += 1;
            }
            if(doc_count == 0){
                normalizedVector.put(word, 0.0);
            }
            else{
                idf = Math.log((double)dc.getSize()/doc_count);
                normalizedVector.put(word, tf*idf);
            }
        }
    }

    public double getNormalizedFrequency(String word){
        return normalizedVector.get(word);
    }

}
