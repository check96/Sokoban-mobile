package model;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    private List<T> array = new ArrayList<T>();

    public void push(T obj){
    	array.add(obj);
    }

    public T pop(){
        return array.remove(array.size()-1);
    }
    
    public void clear() {
    	array.clear();
    }
    
    public T get() {
    	return array.get(array.size()-1);
    }

    @Override
    public String toString() {
        String string = "";

        for(int i=0; i<array.size(); i++)
            string += array.get(i);

        return string;
    }

    public int size(){
        return array.size();
    }

}
