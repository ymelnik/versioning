package com.melnikyura.model;

import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@ToString
public class ArrayElement {
    private List<Node> nodes = new LinkedList<>();

    public ArrayElement addNode(Node node) {
        nodes.add(node);
        return this;
    }

    public Node getNode() {
        return nodes.size() == 0 ? null : nodes.get(nodes.size()-1);
    }

    public Node getNode(int i) {
        return nodes.get(i);
    }

    public int amountOfVersions() {
        return nodes.size();
    }
}
