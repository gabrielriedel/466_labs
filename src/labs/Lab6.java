package src.labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.ItemClasses.ItemSet;
import src.ItemClasses.Rule;

public class Lab6 {

    public static ArrayList<Rule> rules;
    public static ArrayList<ItemSet> transactions;
    public static ArrayList<Integer> items;
    public static HashMap<Integer, ArrayList<ItemSet>> frequentItemSet;

    public static void main(String[] args){
        rules = new ArrayList<>();
        transactions = new ArrayList<>();
        items = new ArrayList<>();
        frequentItemSet = new HashMap<>();

        process("./files/shopping_data.txt");
        findFrequentSingleItemSets();
        boolean hastItemSets = true;
        int k = 2;
        while(hastItemSets){
            hastItemSets = findFrequentItemSets(k);
            k++;
        }

        generateRules();



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

    public static ArrayList<Rule> split(ItemSet itemSet) {
    ArrayList<Rule> ruleSet = new ArrayList<>();
    int n = itemSet.size();
    
    for (int mask = 1; mask < (1 << n) - 1; mask++) {
        ItemSet left = new ItemSet();
        ItemSet right = new ItemSet();
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                left.add(itemSet.get(i));
            } else {
                right.add(itemSet.get(i));
            }
        }
        Rule rule1 = new Rule(new ItemSet(left), new ItemSet(right));
        if (!ruleSet.contains(rule1)) {
            ruleSet.add(rule1);
        }
    }

    return ruleSet;
}


    public static void generateRules(){
        for(Map.Entry<Integer, ArrayList<ItemSet>> entry : frequentItemSet.entrySet()){
            if(entry.getKey() >= 2){
                for(ItemSet itemSet : entry.getValue()){
                    ArrayList<Rule> ruleList = split(itemSet);
                    for(Rule rule : ruleList){
                        if(isMinConfidenceMet(rule)){
                        System.out.println(rule.left.asList() + " " + rule.right.asList());
                        rules.add(rule);
                        }
                    }
                }
            }

        }
    }

    public static boolean isMinConfidenceMet(Rule r){
        ItemSet left = r.left;
        ItemSet right = r.right;
        int success = 0;
        int total = 0;
        for(ItemSet transaction : transactions){
            if(transaction.containsAll(left)){
                total++;
                if(transaction.containsAll(right)){
                    success++;
                }
            }
        }
        if((1.0*success)/total >= 0.99){
            return true;
        }
        return false;
    }
    
}
