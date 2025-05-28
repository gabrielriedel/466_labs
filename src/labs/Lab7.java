package src.labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.MatrixClasses.Matrix;

public class Lab7 {

    public static void main(String args[]){
        int[][] data = process("./files/data.txt");
        Matrix dataMatrix = new Matrix(data);
        ArrayList<Integer> attributes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            attributes.add(i);
        }
        ArrayList<Integer> rows = new ArrayList<>();
        for(int i = 0; i < 150; i++){
            rows.add(i);
        }
        printDecisionTree(dataMatrix, attributes, rows, 0, 100);
    }
    
    public static int[][] process(String filepath){
        int[][] data = new int[150][5];
        int row = 0;
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.isEmpty()) continue;

                String[] values = line.split(",");
                String [] at1 = values[0].split("\\.");
                String [] at2 = values[1].split("\\.");
                String [] at3 = values[2].split("\\.");
                String [] at4 = values[3].split("\\.");
                int one = Integer.parseInt(at1[0]);
                int two = Integer.parseInt(at2[0]);
                int three = Integer.parseInt(at3[0]);
                int four = Integer.parseInt(at4[0]);
                data[row][0] = one;
                data[row][1] = two;
                data[row][2] = three;
                data[row][3] = four;
                data[row][4] = Integer.parseInt(values[4]);
                row++;
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
        }

        return data;
    }

    public static void printDecisionTree(Matrix data, ArrayList<Integer> attributes, ArrayList<Integer> rows, int level, double currentIGR) {
        if (currentIGR < 0.01 || attributes.isEmpty()) {
            int commonVal = data.findMostCommonValue(rows);
            System.out.print("\t".repeat(level));
            System.out.println("value = " + commonVal);
            return;
        }

        double maxIGR = 0.0;
        int maxAT = -1;
        for (Integer attribute : attributes) {
            double IGR = data.computeIGR(attribute, rows);
            if (IGR > maxIGR) {
                maxIGR = IGR;
                maxAT = attribute;
            }
        }

        if (maxIGR < 0.01) {
            int label = data.findMostCommonValue(rows);
            System.out.print("\t".repeat(level));
            System.out.println("value = " + label);
            return;
        }

        HashMap<Integer, ArrayList<Integer>> split = data.split(maxAT, rows);

        for (Map.Entry<Integer, ArrayList<Integer>> entry : split.entrySet()) {
            int atDisplay = maxAT + 1;
            System.out.print("\t".repeat(level));
            System.out.println("When attribute " + atDisplay + " has value " + entry.getKey());

            ArrayList<Integer> newAttributes = new ArrayList<>(attributes);
            newAttributes.remove(Integer.valueOf(maxAT));

            printDecisionTree(data, newAttributes, entry.getValue(), level + 1, maxIGR);
        }
}


}



            