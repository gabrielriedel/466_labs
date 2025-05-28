package src.labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import src.MatrixClasses.Matrix;

public class Lab8 {
    private static final Scanner scanner = new Scanner(System.in); 
    public static void main(String[] args){
        Matrix dataMatrix = new Matrix(readInput("./files/data.txt"));
        int[] attributes = {0,1,2,3};
        int[] row = new int[4];
        int i = 0;
        for(int attribute : attributes){
            String value = getCustomerInput(attribute);
            row[i] = Integer.parseInt(value);
            i++;
        }
        closeScanner();
        System.out.println("For value 1: Probability is: " + dataMatrix.findProb(row, 1));
        System.out.println("For value 2: Probability is: " + dataMatrix.findProb(row, 2));
        System.out.println("For value 3: Probability is: " + dataMatrix.findProb(row, 3));
        System.out.println("Expected Category: " + dataMatrix.findCategory(row));

    }

    public static int[][] readInput(String filepath){
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

    public static String getCustomerInput(int attribute) {
        System.out.print("Enter value for attribute " + attribute + " : ");
        return scanner.nextLine();
    }

    public static void closeScanner() {
        scanner.close();
    }
    

        //     System.out.print("Enter value for attribute 1: ");
        // String attribute2 = scanner.nextLine();
        // System.out.print("Enter value for attribute 2: ");
        // String attribute3 = scanner.nextLine();
        // System.out.print("Enter value for attribute 3: ");
        // String attribute4 = scanner.nextLine();
}
