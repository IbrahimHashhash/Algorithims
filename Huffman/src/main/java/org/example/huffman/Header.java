package org.example.huffman;

import java.util.LinkedList;

public class Header {
    public static String serializeTree(ByteNode root) {
        StringBuilder sb = new StringBuilder();
        serializeTreeHelper(root, sb);
        System.out.println(sb.toString());
        return sb.toString();
    }

    private static void serializeTreeHelper(ByteNode node, StringBuilder sb) {
        if (node == null) return;

        if (node.getData() == null) { // Internal node
            sb.append("0");
        } else { // Leaf node
            sb.append("1").append((char) node.getData().byteValue());
        }

        serializeTreeHelper(node.getLeft(), sb); // Serialize left subtree
        serializeTreeHelper(node.getRight(), sb); // Serialize right subtree
    }

    public static ByteNode deserializeTree(String serializedTree) {
        LinkedList<Character> linkedList = new LinkedList<>();
        for (char c : serializedTree.toCharArray()) {
            linkedList.add(c);
        }
        return deserializeTreeHelper(linkedList);
    }

    // Helper function for deserializing the tree recursively
    private static ByteNode deserializeTreeHelper(LinkedList<Character> queue) {
        if (queue.isEmpty()) return null;

        char c = queue.remove(); // Dequeue
        if (c == '1') { // Leaf node
            return new ByteNode((byte) queue.remove().charValue(), 0); // Dequeue next character
        }

        // Internal node
        ByteNode node = new ByteNode(null, 0);
        node.setLeft(deserializeTreeHelper(queue));  // Deserialize left subtree
        node.setRight(deserializeTreeHelper(queue));  // Deserialize right subtree
        return node;
    }
}
