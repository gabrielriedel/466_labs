package src.labs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.GraphClasses.Graph;

public class Lab4 {

    public static void main(String args[]){

        Graph pageGraph = new Graph();
        String filepath = "./files/graph.txt";
        pageGraph.populateGraph(filepath);

        HashMap<Integer, Double> pageRankOld = new HashMap<>();
        HashMap<Integer, Double> pageRankNew = new HashMap<>();
        double d = 0.9;
        int num_nodes = pageGraph.nodeSet.size();
        double pageRank = 0.0;
        double pageSum = 0.0;

        for(int i : pageGraph.nodeSet){
            pageRankOld.put(i, (double) 1/num_nodes);
        }

        // int z = 0;
        // for(Map.Entry<Integer, ArrayList<Integer>> entry : pageGraph.adjacencyList.entrySet()){
        //     System.out.println(entry.getKey() + " " + entry.getValue());
        // }

        for(int i : pageGraph.adjacencyList.keySet()){
                double out_sum = 0.0;
                for(int j : pageGraph.adjacencyList.get(i))
                {
                    if(pageGraph.outgoingLinks.get(j) != 0){
                        out_sum += pageRankOld.get(j)/pageGraph.outgoingLinks.get(j);
                    }
                }
                pageRank = (1-d)*(1/num_nodes)+d*out_sum;
                pageRankNew.put(i, pageRank);
            }
        for(Map.Entry<Integer, Double> entry : pageRankNew.entrySet()){
                pageSum += entry.getValue();
            }
        for(Map.Entry<Integer, Double> entry : pageRankNew.entrySet()){
                pageRankNew.put(entry.getKey(), entry.getValue()/pageSum);
            }


        while(findDistance(pageRankOld, pageRankNew) > 0.001){
            pageSum = 0.0;
            for(int i : pageGraph.adjacencyList.keySet()){
                double out_sum = 0.0;
                for(int j : pageGraph.adjacencyList.get(i))
                {
                    if(pageGraph.outgoingLinks.get(j) != 0){
                        out_sum += pageRankOld.get(j)/pageGraph.outgoingLinks.get(j);
                    }
                }
                pageRank = (1-d)*(1/num_nodes)+d*out_sum;
                pageRankNew.put(i, pageRank);
            }
            for(Map.Entry<Integer, Double> entry : pageRankNew.entrySet()){
                pageSum += entry.getValue();
            }
            for(Map.Entry<Integer, Double> entry : pageRankNew.entrySet()){
                pageRankNew.put(entry.getKey(), entry.getValue()/pageSum);
            }
            pageRankOld = (HashMap<Integer, Double>) pageRankNew.clone();

        }

        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(pageRankNew.entrySet());
        entryList.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        int i = 0;
        for(Map.Entry<Integer, Double> entry: entryList){
            i++;
            if (i > 20)
            break;
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        
    }

    public static double findDistance(HashMap<Integer, Double> pageRankOld, HashMap<Integer, Double> pageRankNew){
        double dist = 0.0;
        for(Map.Entry<Integer, Double> entry : pageRankOld.entrySet()){
            int node_id = entry.getKey();
            if(pageRankNew.get(node_id) == null){
                dist += Math.abs(pageRankOld.get(node_id));
            }
            else{
                dist += Math.abs(pageRankOld.get(node_id)-pageRankNew.get(node_id));
            }
        }

        return dist;
    }
    
}
