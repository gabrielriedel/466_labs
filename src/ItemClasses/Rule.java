package src.ItemClasses;

public class Rule {

    public ItemSet left, right;

    public Rule(){
        left = new ItemSet();
        right = new ItemSet();
    }

    public Rule(ItemSet left1, ItemSet right1){
        left = left1;
        right = right1;
    }

    public boolean equals(Rule rule2)
    {
        if(left.equals(rule2.right) && right.equals(rule2.left)){
            return true;
        }
        else if(left.equals(rule2.left) && right.equals(rule2.right)){
            return true;
        }
        return false;
    }
    
}
