package com.wangjie.seizerecyclerview.example.vm;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class VM<T> implements Serializable {
    private T obj;

    public VM(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public int getViewType(){
        return 0;
    }
}
