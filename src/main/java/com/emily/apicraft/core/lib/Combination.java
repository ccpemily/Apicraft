package com.emily.apicraft.core.lib;

public class Combination<T> {
    private final T first;
    private final T second;

    public Combination(T first, T second){
        this.first = first;
        this.second = second;
    }

    public T getFirst(){
        return first;
    }

    public T getSecond(){
        return second;
    }

    @Override
    public int hashCode(){
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Combination<?> c)){
            return false;
        }
        Class<?> clazz = c.first.getClass();
        if(!clazz.equals(first.getClass())){
            return false;
        }
        return c.hashCode() == this.hashCode();
    }
}
