package com.melnikyura.model;

import lombok.ToString;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@ToString
public class I extends Node{
    private Node left;
    private Node right;

    public I(Node left, Node right) {
        this.left = left;
        this.right = right;
    }



    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public L getRightLeaf() {
        while (true) {
            Node children = this.getRight();
            if (children instanceof L) return (L)children;
        }
    }

}
