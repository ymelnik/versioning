package com.melnikyura.model;

import lombok.ToString;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@ToString
public class TreeLevel {
    private byte[] nodes;

    public TreeLevel(int nodesInLevel) {
        nodes = new byte[nodesInLevel];
    }


    public void add(int i, byte node) {
        nodes[i] = node;
    }

    public byte[] getNodes() {
        return nodes;
    }
}
