package src.DocumentClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class TextVector implements Serializable{

    private HashMap<String, Integer> rawVector; 

    public TextVector() {
        rawVector = new HashMap<String, Integer>();
    }

    public Set<Map.Entry<String, Integer>> getRawVectorEntrySet(){
        return rawVector.entrySet();

    }

    public void add(String word){
        // adds a word to the rawVector. If the word is not new, 
        // frequency is incremented by one.
        if(rawVector.containsKey(word)){
            int freq = rawVector.get(word);
            rawVector.put(word, freq+1);
        }
        else{
            rawVector.put(word, 1);
        }
    }

    public boolean contains(String word){
        // returns true if the word is in the rawVector and false otherwise.  
        return rawVector.containsKey(word);
    }

    public int getRawFrequency(String word){
        // returns the frequency of the word.
        if(rawVector.get(word) == null){
            return 0;
        }
        return rawVector.get(word);
    }

    public int getTotalWordCount(){
        // returns the total number of non-noise words that are stored 
        // for the document (e.g., if frequency =2, then count the word twice).
        int sum = 0;
        for(int freq : rawVector.values()){
            sum += freq;
        }
        return sum;
    }

    public int getDistinctWordCount(){
        // returns the number of distinct words that are stored.
        return rawVector.size();
    }

    public int getHighestRawFrequency(){
        // returns the highest word frequency.
        int max_freq = 0;

        for(int freq : rawVector.values()){
            if(freq > max_freq){
                max_freq = freq;
            }
        }
        return max_freq;
    }

    public String getMostFrequentWord(){
        // returns the word with the highest frequency.
        String max_word = null;
        int max_freq = 0;

        for (Map.Entry<String, Integer> entry : this.getRawVectorEntrySet()){
            if (entry.getValue() > max_freq){
                max_freq = entry.getValue();
                max_word = entry.getKey();
            }
        }
        return max_word;
    }

    public abstract Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet();

    public abstract void normalize(DocumentCollection dc);

    public abstract double getNormalizedFrequency(String word);

    public double getL2Norm(){
        double sumFreq = 0.0;
        for(String word : rawVector.keySet()){
            sumFreq += this.getNormalizedFrequency(word);
        }
        return Math.sqrt(sumFreq);
    }   

    public ArrayList<Integer> findClosestDocuments(DocumentCollection documents, DocumentDistance distanceAlg){
        ArrayList<Integer> result = new ArrayList<>();
        return result;
    }
 }


