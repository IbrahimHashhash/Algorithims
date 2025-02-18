package org.example.huffman;

public class HuffmanTree {

    private  ByteNode root; // Root of the Huffman Tree
    private static final StringBuilder table = new StringBuilder();

    public HuffmanTree(ByteNode root) {
        this.root = root;
    }

    public static ByteNode buildTree(int[] frequencies) {
        CustomPriorityQueue queue = new CustomPriorityQueue();

        // Add all nodes with non-zero frequencies to the priority queue
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                queue.add(new ByteNode((byte) i, frequencies[i]));
            }
        }

        // Build the Huffman tree by merging nodes
        while (queue.size() > 1) {
            ByteNode left = queue.dequeue(); // Node with the smallest frequency
            ByteNode right = queue.dequeue(); // Node with the second smallest frequency

            // Create a parent node with combined frequency
            ByteNode parent = new ByteNode(null, left.getFrequency() + right.getFrequency());
            parent.setLeft(left);
            parent.setRight(right);

            queue.add(parent);
        }

        // The final node in the queue is the root of the Huffman Tree
        return queue.dequeue();
    }

}
