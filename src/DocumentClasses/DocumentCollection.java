package src.DocumentClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DocumentCollection implements Serializable{

    private HashMap<Integer, TextVector> documents;

    public static  String noiseWordArray[] = {"a", "about", "above", "all", "along",
        "also", "although", "am", "an", "and", "any", "are", "aren't", "as", "at",
        "be", "because", "been", "but", "by", "can", "cannot", "could", "couldn't",
        "did", "didn't", "do", "does", "doesn't", "e.g.", "either", "etc", "etc.",
        "even", "ever", "enough", "for", "from", "further", "get", "gets", "got", "had", "have",
        "hardly", "has", "hasn't", "having", "he", "hence", "her", "here",
        "hereby", "herein", "hereof", "hereon", "hereto", "herewith", "him",
        "his", "how", "however", "i", "i.e.", "if", "in", "into", "it", "it's", "its",
        "me", "more", "most", "mr", "my", "near", "nor", "now", "no", "not", "or", "on", "of", "onto",
        "other", "our", "out", "over", "really", "said", "same", "she",
        "should", "shouldn't", "since", "so", "some", "such",
        "than", "that", "the", "their", "them", "then", "there", "thereby",
        "therefore", "therefrom", "therein", "thereof", "thereon", "thereto",
        "therewith", "these", "they", "this", "those", "through", "thus", "to",
        "too", "under", "until", "unto", "upon", "us", "very", "was", "wasn't",
        "we", "were", "what", "when", "where", "whereby", "wherein", "whether",
        "which", "while", "who", "whom", "whose", "why", "with", "without",
        "would", "you", "your", "yours", "yes"};


    public DocumentCollection(){
        documents = new HashMap<>();
    }

    public TextVector getDocumentsById(int id){
        return documents.get(id);
    }

    public int getAverageDcoumentLength(){
        int word_count = 0;
        for(TextVector document: documents.values()){
            word_count += document.getTotalWordCount();
        }
        return word_count/documents.size();
    }

    public int getSize(){
        return documents.size();
    }

    public Collection<TextVector> getDocuments(){
        return documents.values();
    }

    public Set<Map.Entry<Integer, TextVector>> getEntrySet(){
        return documents.entrySet();
        
    }

    public int getDocumentFrequency(String word){
        int count = 0;
        for(TextVector document: documents.values()){
            if(document.contains(word)){
                count += 1;
            }
        }
        return count;
    }

    private boolean isNoiseWord(String word){
        for(String noise: noiseWordArray){
            if(word.equals(noise) || word.length() <= 1){
                return true;
            }
        }
        return false;
    }

    public DocumentCollection(String filepath) {
        documents = new HashMap<>();
        int dId = 0;
        StringBuilder docText = new StringBuilder();
        boolean collectDoc = false;
    
        try {
            Scanner scanner = new Scanner(new File(filepath));
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith(".I")) {
                    if(dId != 0){
                        String[] words = docText.toString().split("[^a-zA-Z]+");
                        TextVector rawVector = new TextVector();
                        for (String word : words) {
                            word = word.toLowerCase();
                            if (!isNoiseWord(word)) {
                                rawVector.add(word);
                            }
                        }
                        documents.put(dId, rawVector);
                        docText = new StringBuilder();
                    }
                    dId = Integer.parseInt(line.substring(3));
                    collectDoc = false;
                }
                else if(line.startsWith(".W")){
                    collectDoc = true;
                }
                else if(collectDoc){
                    docText.append(line).append(" ");
                }
                }
            if (docText.length() > 0) {
                String[] words = docText.toString().split("[^a-zA-Z]+");
                TextVector rawVector = new TextVector();
                for (String word : words) {
                    word = word.toLowerCase();
                    if (!isNoiseWord(word)) {
                        rawVector.add(word);
                    }
                }
                documents.put(dId, rawVector);
            }
    
            scanner.close();
    
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
        }
    }

}
