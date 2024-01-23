package com.emily.apicraft.bee;

public class BeeProductInfo {
    private int normalProductCount;
    private int specialProductCount;

    public BeeProductInfo(){
        this.normalProductCount = 0;
        this.specialProductCount = 0;
    }

    public BeeProductInfo(int normal, int special){
        this.normalProductCount = normal;
        this.specialProductCount = special;
    }

    public boolean hasProduct(){
        return normalProductCount > 0;
    }

    public RemoveResult removeProduct(){
        if(hasProduct()){
            normalProductCount--;
            if(specialProductCount > 0){
                specialProductCount--;
                return RemoveResult.REMOVED_BOTH;
            }
            return RemoveResult.REMOVED_NORMAL;
        }
        return RemoveResult.NO_PRODUCT;
    }

    public void addNormal(){
        this.normalProductCount++;
    }

    public void addNormal(int count){
        this.normalProductCount += count;
    }

    public void addSpecial(){
        this.normalProductCount++;
        this.specialProductCount++;
    }

    public void addSpecial(int count){
        this.normalProductCount += count;
        this.specialProductCount += count;
    }

    public int getNormalProductCount(){
        return normalProductCount;
    }

    public int getSpecialProductCount(){
        return specialProductCount;
    }

    public static enum RemoveResult {
        NO_PRODUCT, REMOVED_NORMAL, REMOVED_BOTH
    }
}
