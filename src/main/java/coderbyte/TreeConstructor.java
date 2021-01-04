package coderbyte;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class TreeConstructor {

    public static String TreeConstructor(String... strArr) {
        Pattern DIGITS = Pattern.compile("(\\d+)");
        for (String onePair : strArr) {
            Matcher matcher = DIGITS.matcher(onePair);
            matcher.find();
            Integer child = Integer.valueOf(matcher.group());
            matcher.find();
            Integer parent = Integer.valueOf(matcher.group());

            if (!registerPair(parent, child)) {
                return "false";
            }
        }

        return "true";
    }

    static Map<Integer, Node> TREE_AS_LIST = new HashMap<>();

    private static boolean registerPair(Integer parent, Integer child) {
        Node parentNode = TREE_AS_LIST.get(parent);
        Node childNode = TREE_AS_LIST.get(child);

        if (isNull(parentNode)) {
            parentNode = new Node().value(parent);
            TREE_AS_LIST.put(parent, parentNode);
        }
        if (isNull(childNode)) {
            childNode = new Node().value(child);
            TREE_AS_LIST.put(child, childNode);
        }

        if (nonNull(childNode.parent)) {
            return false; // child already has parent
        }
        if (nonNull(parentNode.childOne) && nonNull(parentNode.childTwo)) {
            return false; // parent already has two children
        }
        childNode.parent(parentNode);
        if (isNull(parentNode.childOne)) {
            parentNode.childOne(childNode);
        } else {
            parentNode.childTwo(childNode);
        }
        return true;
    }

    static class Node {
        Integer value;
        Node parent;
        Node childOne;
        Node childTwo;

        public Node value(Integer value) {
            this.value = value;
            return this;
        }

        public Node parent(Node parent) {
            this.parent = parent;
            return this;
        }

        public Node childOne(Node childOne) {
            this.childOne = childOne;
            return this;
        }

        public Node childTwo(Node childTwo) {
            this.childTwo = childTwo;
            return this;
        }
    }

    public static void main(String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(TreeConstructor(s.nextLine()));
    }
}
