package src.ItemClasses;

import java.util.ArrayList;

public class ItemSet {

    public ArrayList<Integer> itemList;

    public ItemSet(){
        itemList = new ArrayList<>();
    }

    public ItemSet(int item){
        itemList = new ArrayList<>();
        itemList.add(item);
    }

    public ItemSet(ItemSet other){
        this.itemList = new ArrayList<>(other.itemList);
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

    public ItemSet subsetElse(ItemSet left){
        ItemSet sub = new ItemSet();
        for(int k = 0; k < itemList.size(); k++){
            if(!left.contains(itemList.get(k))){
                sub.add(itemList.get(k));}
        }
        return sub;
    }

    public int get(int i){
        return itemList.get(i);
    }

    public void set(int i, int item){
        itemList.set(i, item);
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
