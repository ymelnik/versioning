package com.melnikyura.model;

import lombok.ToString;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@ToString
public class Row {
    private ArrayElement[] nodes;


    public Row(int size) {
        nodes = new ArrayElement[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = new ArrayElement();
        }
    }


    public ArrayElement get(int index) {
        return nodes[index];
    }

    public void set(int index, ArrayElement element) {
        nodes[index] = element;
    }

    public void add(int i, Node node) {
        nodes[i].addNode(node);
    }

    public int getLength() {
        return nodes.length;
    }
}
