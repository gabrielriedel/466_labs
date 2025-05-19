package src.GraphClasses;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Graph {

    public HashSet<Integer> nodeSet;
    public HashMap<Integer, ArrayList<Integer>> adjacencyList;
    public HashMap<Integer, Integer> outgoingLinks;

    public Graph(){
        nodeSet = new HashSet<>();
        adjacencyList = new HashMap<>();
        outgoingLinks = new HashMap<>();
    }

    public void populateGraph(String filepath) {
    try {
        Scanner scanner = new Scanner(new File(filepath));
        int cur_node = -1;
        int out_count = 0;
        ArrayList<Integer> adjacent_nodes = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] values = line.split("\\s+");
            int node1 = Integer.parseInt(values[0]);
            int node2 = Integer.parseInt(values[2]);

            if (cur_node != node1 && cur_node != -1) {
                adjacencyList.put(cur_node, new ArrayList<>(adjacent_nodes));
                outgoingLinks.put(cur_node, out_count);
                adjacent_nodes.clear();
                out_count = 0;
            }

            cur_node = node1;
            adjacent_nodes.add(node2);
            out_count++;
            nodeSet.add(node1);
            nodeSet.add(node2);
        }

        if (cur_node != -1) {
            adjacencyList.put(cur_node, new ArrayList<>(adjacent_nodes));
            outgoingLinks.put(cur_node, out_count);
        }

        scanner.close();
    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + filepath);
    }
}

    
}
