package com.emily.apicraft.bee;

import com.emily.apicraft.genetics.alleles.AlleleSpecies;
import com.emily.apicraft.interfaces.genetics.IAllele;
import com.emily.apicraft.registry.Registries;
import com.emily.apicraft.utils.Tags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class BeeProductData {
    private final Map<IAllele<AlleleSpecies>, BeeProductInfo> productData;
    private final int capacity;

    public BeeProductData(int capacity){
        productData = new HashMap<>();
        this.capacity = capacity;
    }
    @SuppressWarnings("unchecked")
    public BeeProductData(ItemStack container, CompoundTag tag){
        productData = new HashMap<>();
        ListTag list = tag.getList(Tags.TAG_PRODUCT_DATA, Tag.TAG_COMPOUND);
        if(!list.isEmpty()){
            for(int i = 0; i < list.size(); i++){
                CompoundTag infoTag = list.getCompound(i);
                IAllele<AlleleSpecies> species = (IAllele<AlleleSpecies>) Registries.ALLELES.get(infoTag.getString(Tags.TAG_PRODUCT_SPECIES));
                int normal = infoTag.getInt(Tags.TAG_NORMAL_COUNT);
                int special = Math.min(infoTag.getInt(Tags.TAG_SPECIAL_COUNT), normal);
                productData.put(species, new BeeProductInfo(normal, special));
            }
        }
        capacity = container.getMaxDamage();
    }

    public CompoundTag writeToTag(CompoundTag tag){
        ListTag list = new ListTag();
        int i = 0;
        for(IAllele<AlleleSpecies> species : productData.keySet()){
            if(species != null){
                CompoundTag infoTag = new CompoundTag();
                infoTag.putString(Tags.TAG_PRODUCT_SPECIES, species.toString());
                infoTag.putInt(Tags.TAG_NORMAL_COUNT, productData.get(species).getNormalProductCount());
                infoTag.putInt(Tags.TAG_SPECIAL_COUNT, productData.get(species).getSpecialProductCount());
                list.add(i, infoTag);
                i++;
            }
        }
        tag.put(Tags.TAG_PRODUCT_DATA, list);
        return tag;
    }

    private void add(IAllele<AlleleSpecies> species, boolean special){
        if(!productData.containsKey(species)){
            productData.put(species, new BeeProductInfo());
        }
        BeeProductInfo info = productData.get(species);
        if(special){
            info.addSpecial();
        }
        else {
            info.addNormal();
        }
    }

    private Optional<Tuple<IAllele<AlleleSpecies>, Boolean>> remove(){
        Iterator<IAllele<AlleleSpecies>> iterator = productData.keySet().iterator();
        if(iterator.hasNext()){
            IAllele<AlleleSpecies> species = iterator.next();
            BeeProductInfo.RemoveResult result = productData.get(species).removeProduct();
            if(result == BeeProductInfo.RemoveResult.NO_PRODUCT){
                productData.remove(species);
                return Optional.empty();
            }
            else{
                if(!productData.get(species).hasProduct()){
                    productData.remove(species);
                }
                return Optional.of(new Tuple<>(species, result == BeeProductInfo.RemoveResult.REMOVED_BOTH));
            }
        }
        return Optional.empty();
    }

    public boolean tryAdd(IAllele<AlleleSpecies> species, boolean special){
        if(getTotalStored() >= capacity){
            return false;
        } else {
            add(species, special);
            return true;
        }
    }

    public Optional<Tuple<IAllele<AlleleSpecies>, Boolean>> tryRemove(){
        if(getTotalStored() <= 0){
            return Optional.empty();
        } else {
            return remove();
        }
    }

    public int getTotalStored(){
        int total = 0;
        for(var info : productData.values()){
            total += info.getNormalProductCount();
        }
        return total;
    }

    public boolean isFull(){
        return getTotalStored() >= capacity;
    }
}
