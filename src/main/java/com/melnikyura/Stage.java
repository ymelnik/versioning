package com.melnikyura;

import com.melnikyura.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:melnikyura@gmail.com">Yuriy Melnik</a>
 * Created on March 21, 2021
 */
@Slf4j
public class Stage {
    private Row[] tree;
//    private TreeLevel[] tree1;


    public void initTree(int[] events, int treeSize) {
        /*
         * References to resource id to particular index of leaf in the tree
         * Temporarily key is Integer while using test data of values. As a target it should be String
         */
        Map<Integer, Integer> referencesToLeaves = new HashMap<>();

        /*
         Initialization the matrix by dummy instances to avoid check for null during running the algorithm
         Maybe using ArrayList will be more efficient than [] with such pre-init
         */
        tree = new Row[log2(treeSize)+1];
        for (int k = 0; k < tree.length; k++) {
            /* Calculating amount of i in r'th level */
            int x = (int)Math.ceil((double)treeSize/Math.pow(2, k));
            x = x + x % 2; //If i is odd then round it to upper even

            tree[k] = new Row(x);
//            log.debug("For r [{}] - [{}]", k, tree[k].getLength());
        }

        /* Current amount of leaves in the tree */
        int n = 0;

        for(int value : events) {
            /* Checking if leaf for such resource id already exists in the tree */
            Integer li = referencesToLeaves.get(value);
            if (li == null) {
                referencesToLeaves.put(value, n);
                li = n++;
            }

            /* Add leaf to the tree */
            tree[0].add(li, new L(value));

            /* Creating parents corresponded to the leaf till the root */
            int d = log2(n);
            int i = li;
//            log.debug("li: [{}]", li);
            for (int r = 1; r <= d; r++) {
                i = Math.floorDiv(i,2);
//                log.debug("r, i: [{}], [{}]", r,i);
                tree[r].add(i, new I(tree[r-1].get(2*i).getNode(), tree[r-1].get(2*i+1).getNode()));
            }
        }

        ///////////////////////
//        int d = log2(n);
//
//        ((L) tree[0].get(3).getNode()).setRemoved(true);
//        for (int r = 0; r <= d; r++) {
//            log.info("[{}]: {}", r, tree[r]);
//        }

    }


    public List<Integer> diff(int r, int v) {
        Node comparedVersion = tree[r].get(0).getNode(v);
        Node lastVersion = tree[r].get(0).getNode();

        List<Integer> result = new LinkedList<>();

        compareNodes(r, comparedVersion, lastVersion, result);

        int nextLeafIndex = (int) Math.pow(2, r);
        for (int i = nextLeafIndex; i < tree[0].getLength(); i++) {
            if (tree[0].get(i).getNode() != null) {
                result.add(((L) tree[0].get(i).getNode()).getData());
            }
        }

        return result;
    }

    private void compareNodes(int r, Node nodeA, Node nodeB, List<Integer> diff) {
        /* If nodes are identical then there leaves also the same */
        if (nodeA == nodeB) return;

        /*
        Node of younger version is null then just add data from leaves of older version node to diff
        @TODO: Seems like "instance of" and recursive traverse till children not efficient
        */
        if (nodeA == null) {
            getChildren(nodeB, diff);
            return;
        }

        /* Nodes are not identical and are leaves then just append data from either node to diff */
        if (nodeA instanceof L) {
            diff.add(((L) nodeA).getData());
            return;
        }

        /* recursively compare left children */
        compareNodes(r - 1, ((I) nodeA).getLeft(), ((I) nodeB).getLeft(), diff);

        /* recursively compare right children */
        compareNodes(r - 1, ((I) nodeA).getRight(), ((I) nodeB).getRight(), diff);
    }


    private void getChildren(Node node, List<Integer> diff) {
        if (node != null) {
            if (node instanceof L) {
                diff.add(((L) node).getData());
            } else {
                getChildren(((I) node).getLeft(), diff);
                getChildren(((I) node).getRight(), diff);
            }
        }
    }


    public List<String> getVersions() {
        List<String> result = new LinkedList<>();

        int r = 0;
        for(Row row : tree) {
            for (int v = 0; v < row.get(0).amountOfVersions(); v++) {
                result.add(String.format("%d.%d",r, v));
            }
            r++;
        }

        return result;
    }


    // The following is new implementation

    /* Un-marshaling existing tree */
    public TreeLevel[] initTree1(byte[] nodes, int treeSize) {
        TreeLevel[] tree = new TreeLevel[log2(treeSize)+1];

        int c = 0;
        for (int r = 0; r < tree.length; r++) {
            int nodeInLevel = (int) Math.pow(2, r);
            tree[r] = new TreeLevel(nodeInLevel);

            for (int i = 0; i < nodeInLevel; i++) {
                tree[r].getNodes()[i] = nodes[c++];
            }
        }

        return tree;
    }

    /* Update existing node */
    public void update(TreeLevel[] tree, int nodeIndex) {
        /* If it's update then d of new tree the same as old one, else calculate new tree depth including new element */
//        int d = tree[0].getNodes().length >= nodeIndex+1 ? tree.length : log2(nodeIndex+1)+1;
//        TreeLevel[] newTree = new TreeLevel[d];


        int i = nodeIndex;
        for (int r = tree.length-1; r >= 0; r--) {
            tree[r].getNodes()[i] += 1;
            i = Math.floorDiv(i,2);
        }
    }

    /* Add new node(s) */
//    public void add(byte[] ser, int newNodesAmount) {
//        /* If it's update then d of new tree the same as old one, else calculate new tree depth including new element */
//        int d = tree[0].getNodes().length >= nodeIndex+1 ? tree.length : log2(nodeIndex+1)+1;
//        TreeLevel[] newTree = new TreeLevel[d];
//
//
//        int i = nodeIndex;
//        for (int r = tree.length-1; r >= 0; r--) {
//            tree[r].getNodes()[i] += 1;
//            i = Math.floorDiv(i,2);
//        }
//    }


    public List<Integer> diff1(TreeLevel[] treeA, TreeLevel[] treeB) {
        List<Integer> result = new LinkedList<>();

        compareNodes1(0, treeB.length - treeA.length, 0, treeA, treeB, result);

        int nextLeafIndex = (int) Math.pow(2, treeB.length - treeA.length);
        for (int i = nextLeafIndex; i < treeB[treeB.length-1].getNodes().length; i++) {
            result.add(i);
        }

        return result;
    }


    private void compareNodes1(int rA, int rB, int i, TreeLevel[] a, TreeLevel[] b, List<Integer> diff) {
        log.debug("rA: [{}], rB: [{}], i: [{}]", rA, rB, i);
        if (a[rA].getNodes().length == i) {
            for (int l = (int) (i*Math.pow(2, rB)); l < i*Math.pow(2, rB)+Math.pow(2,rB)+1; l++) {
                diff.add(l);
            }

            return;
        }

        /* If nodes are identical then there leaves also the same */
        if (a[rA].getNodes()[i] == b[rB].getNodes()[i]) return;


        /* Nodes are not identical and are leaves then just append data from either node to diff */
        if (rA == a.length-1) {
            diff.add(i);
            return;
        }

        /* recursively compare left children */
        compareNodes1(rA+1, rB+1, 2*i, a, b, diff);

        /* recursively compare right children */
        compareNodes1(rA+1, rB+1, 2*i+1, a, b, diff);
    }


    private int log2(int n) {
        return (int) Math.ceil(Math.log(n)/Math.log(2));
    }

}
