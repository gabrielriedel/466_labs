package src.ItemClasses;

import java.util.ArrayList;

public class ItemSet {

    public ArrayList<Integer> itemList;

    public ItemSet(){

        itemList = new ArrayList<>();
    }

    public void add(int item){
        itemList.add(item);
    }

    public int size(){
        return itemList.size();
    }

    public ArrayList<Integer> asList(){
        return itemList;
    }

    public boolean contains(int item){
        if(itemList.contains(item)){
            return true;
        }
        return false;
    }

    public boolean containsAll(ItemSet itemSubset){
        if(itemList.containsAll(itemSubset.asList())){
            return true;
        }
        return false;
    }

    public boolean listContains(ArrayList<ItemSet> itemSets){
        for(ItemSet otherItems : itemSets){
            if(itemList.containsAll(otherItems.asList())){
                return true;
            }
        }
        return false;
    }

    public ItemSet union(ItemSet otherList){
        ItemSet unionSet = new ItemSet();
        for(int i : itemList){
            unionSet.add(i);
        }
        for(int j : otherList.asList()){
            if(!unionSet.contains(j)){
                unionSet.add(j);
            }
        }
        return unionSet;
    }



    
}
