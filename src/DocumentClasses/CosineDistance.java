package src.DocumentClasses;

import java.util.Map;

public class CosineDistance implements DocumentDistance{

    public double findDistance(TextVector query, TextVector document, DocumentCollection documents){
        double dot_sum = 0.0;
        double q_norm = 0.0;
        double d_norm = 0.0;
        for (Map.Entry<String, Double> entry : query.getNormalizedVectorEntrySet()) {
            String word = entry.getKey();
            Double q_frequency = entry.getValue();    
            q_norm += Math.pow(q_frequency, 2);
            if(document.contains(word)){
                dot_sum += document.getNormalizedFrequency(word)*q_frequency;
            }
        }
        for (Map.Entry<String,Double> entry : document.getNormalizedVectorEntrySet()){
            d_norm += Math.pow(entry.getValue(),2);
        }
        double cosine_dist = dot_sum/(Math.sqrt(q_norm)*Math.sqrt(d_norm));
        return cosine_dist;
    }
    
}