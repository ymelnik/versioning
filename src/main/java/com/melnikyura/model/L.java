package com.melnikyura.model;

import com.google.common.hash.Hashing;
import lombok.ToString;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on May 17, 2020
 */
@ToString
public class L extends Node {
    private int data;
    private int hash;
    private boolean isRemoved = false;


    public L(int data) {
        this.data = data;
        this.hash = Hashing.sha256().hashInt(data).hashCode();
    }


    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public int getData() {
        return data;
    }
}
