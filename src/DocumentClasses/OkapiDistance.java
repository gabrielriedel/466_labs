package src.DocumentClasses;

import java.util.Map;

public class OkapiDistance implements DocumentDistance {

    public double findDistance(TextVector query, TextVector document, DocumentCollection documents){
        double ff = 0;
        double fq = 0;
        double dl = 0;
        double num_docs = documents.getSize();
        double avgdl = documents.getAverageDcoumentLength();
        double okapi_sum = 0;
        String word = "";
        double df = 0;
        for(Map.Entry<String,Integer> entry : query.getRawVectorEntrySet()){
            df = 0;
            word = entry.getKey();
            for(Map.Entry<Integer, TextVector> doc : documents.getEntrySet()){
                if(doc.getValue().contains(word)){df+=1;}
            }
            ff = document.getRawFrequency(word);
            fq = query.getRawFrequency(word);
            dl = document.getTotalWordCount();
            okapi_sum += Math.log((num_docs-df+0.5)/(0.5+df))*(((1.2+1)*ff)/(1.2*(1-0.75+0.75*(dl/avgdl))))*((100+1)*fq/(100+fq));
        }
        return okapi_sum;
    }
    
}
