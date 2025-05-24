package src.labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.ItemClasses.ItemSet;

public class Lab5 {

        public static ArrayList<ItemSet> transactions;
        public static ArrayList<Integer> items;
        public static HashMap<Integer, ArrayList<ItemSet>> frequentItemSet;

    public static void main(String args[]){

        transactions = new ArrayList<>();
        items = new ArrayList<>();
        frequentItemSet = new HashMap<>();

        process("./files/shopping_data.txt");

        ItemSet subset = new ItemSet();
        subset.add(1);
        isFrequent(subset);
        findFrequentSingleItemSets();
        boolean hastItemSets = true;
        int k = 2;
        while(hastItemSets){
            hastItemSets = findFrequentItemSets(k);
            k++;
        }

        for(Map.Entry<Integer, ArrayList<ItemSet>> entry : frequentItemSet.entrySet()){
            System.out.println("Sets of size " + entry.getKey());
            for(ItemSet item : entry.getValue()){
                System.out.println(item.asList());
            }
            System.out.println();
        }

    }
    
    public static void process(String filepath){
        try{
            Scanner scanner = new Scanner(new File(filepath));
    
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String[] values = line.split(", ");

            ItemSet itemList = new ItemSet();

            for(int i = 1; i < values.length; i++){
                int item = Integer.parseInt(values[i]);
                if(!items.contains(item)){
                    items.add(item);
                }
                itemList.add(item);
            }
            transactions.add(itemList);
            
        }

        scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filepath);
        }

    }

    public static boolean findFrequentItemSets(int k){
        frequentItemSet.get(k-1);
        ArrayList<ItemSet> potentialKSets = new ArrayList<>();
        for(ItemSet list1 : frequentItemSet.get(k-1)){
            for(ItemSet list2 : frequentItemSet.get(k-1)){
                ItemSet unionSet = list1.union(list2);
                if(unionSet.size() == k && !unionSet.listContains(potentialKSets)){
                    potentialKSets.add(unionSet);
                }
        }
        }
        if(potentialKSets.size() == 0){
            return false;
        }
        ArrayList<ItemSet> kItemList = new ArrayList<>();
        for(ItemSet potentialSet : potentialKSets){
            if(isFrequent(potentialSet)){
                kItemList.add(potentialSet);
            }
        }
        frequentItemSet.put(k, kItemList);
        return true;
    }

    public static boolean isFrequent(ItemSet itemSubset){
        int total_transactions = transactions.size();
        int count = 0;
        for(ItemSet transaction : transactions){
            if(transaction.containsAll(itemSubset)){
                count++;
            }
        }

        double support = 1.0 * count/total_transactions;
        if(support > 0.01){
            return true;
        }
        return false;
    }

    public static void findFrequentSingleItemSets(){
        ArrayList<ItemSet> oneItemList = new ArrayList<>();
        for(int i : items){
            ItemSet singleItems = new ItemSet();
            singleItems.add(i);
            if(isFrequent(singleItems)){
                oneItemList.add(singleItems);
            }
        }
        frequentItemSet.put(1, oneItemList);

    }


}
