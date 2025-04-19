package src.DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DocumentVector {

    private HashMap<String, Double> normalizedVector = new HashMap<String, Double>();

    public Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet(){
        return normalizedVector.entrySet();
    }

    public void normalize(DocumentCollection dc){
        // normalize
    }

    public double getNormalizedFrequency(String word){
        return 0.0;
    }
    
}
