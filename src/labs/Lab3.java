package src.labs;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import src.DocumentClasses.DocumentCollection;
import src.DocumentClasses.OkapiDistance;
import src.DocumentClasses.TextVector;

public class Lab3 {
    
    public static src.DocumentClasses.DocumentCollection documents;
    public static src.DocumentClasses.DocumentCollection queries;
    public static HashMap<Integer, ArrayList<Integer>> cosineResults;

    public static void main(String[] args){
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./files/docvector")))) {
            documents = (DocumentCollection) is.readObject();
        } 
        catch (Exception e) {
            System.out.println(e);
        }

        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./files/cosinemap")))) {
            cosineResults = (HashMap<Integer, ArrayList<Integer>>) is.readObject();
        } 
        catch (Exception e) {
            System.out.println(e);
        }

        queries = new DocumentCollection("./files/queries.txt", "query");

        OkapiDistance dist = new OkapiDistance();

        HashMap<Integer, ArrayList<Integer>> okapiResults = new HashMap<>();
        int i = 0;
        for(Map.Entry<Integer, TextVector> entry : queries.getEntrySet()){
            i++;
            TextVector query = entry.getValue();
            okapiResults.put(entry.getKey(), query.findClosestDocuments(documents, dist));
            if(i>=20)
            break;
        }

        // for(Map.Entry<Integer, ArrayList<Integer>> result : okapiResults.entrySet()){
        //     System.out.println(result.getKey() + " " + result.getValue());
        // }

        HashMap<Integer, ArrayList<Integer>> humanJudgement = new HashMap<>();
        String filepath = "./files/human_judgement.txt";

        try {
            Scanner scanner = new Scanner(new File(filepath));
            int prevQueryId = -1;
            ArrayList<Integer> rel_docs = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] values = line.split("\\s+");
                int queryId = Integer.parseInt(values[0]);
                int docId = Integer.parseInt(values[1]);
                int relevance = Integer.parseInt(values[2]);

                if (queryId != prevQueryId && prevQueryId != -1) {
                    humanJudgement.put(prevQueryId, new ArrayList<>(rel_docs));
                    rel_docs.clear();
                }

                if (relevance >= 1 && relevance <= 3) {
                    rel_docs.add(docId);
                }

                prevQueryId = queryId;
            }

            // Add the last query's documents
            if (prevQueryId != -1) {
                humanJudgement.put(prevQueryId, new ArrayList<>(rel_docs));
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
        }

        System.out.println("Cosine MAP = " + computeMAP(humanJudgement, cosineResults));
        System.out.println("Okapi MAP = " + computeMAP(humanJudgement, okapiResults));

    }

    public static double computeMAP(HashMap<Integer, ArrayList<Integer>> human, HashMap<Integer, ArrayList<Integer>> results){

        double correct_docs;
        double doc_iter;
        double sum_prec = 0;
        double tot_prec = 0;
        double num_correct;
        int i = 0;

        for(Map.Entry<Integer, ArrayList<Integer>> entry : results.entrySet()){
            i++;
            if(i > 20)
            break;
            correct_docs = 0;
            doc_iter = 0;
            sum_prec = 0;

            ArrayList<Integer> relevantDocs = human.get(entry.getKey());
            // System.out.println("Query ID: " + entry.getKey());
            // System.out.println("Retrieved docs: " + entry.getValue());
            // System.out.println("Relevant docs: " + relevantDocs);
            if (relevantDocs == null || relevantDocs.isEmpty()) continue;
            for(int doc : entry.getValue()){
                doc_iter += 1;
                if(human.get(entry.getKey()).contains(doc)){
                    correct_docs += 1;
                    sum_prec += ((double) correct_docs)/doc_iter;
                }
            }
            num_correct = human.get(entry.getKey()).size();
            tot_prec += ((double) sum_prec)/num_correct;

        }

        return tot_prec/20.0;

    }

}

