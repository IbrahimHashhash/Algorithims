package org.example.huffman;

public class ByteNode implements Comparable<ByteNode> {
    private Byte data;
    private int frequency;

    private ByteNode left, right;

    public ByteNode(Byte data, int frequency) {
        this(data, frequency, null, null);
    }

    public ByteNode(Byte data, int frequency, ByteNode left, ByteNode right) {
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public int getFrequency() {
        return frequency;
    }

    public ByteNode getLeft() {
        return left;
    }

    public ByteNode getRight() {
        return right;
    }

    public void setLeft(ByteNode left) {
        this.left = left;
    }

    public Byte getData() {
        return data;
    }

    public void setRight(ByteNode right) {
        this.right = right;
    }

    public void setData(Byte data) {
        this.data = data;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(ByteNode o) {
        int frequencyCompare = Integer.compare(this.frequency, o.frequency);
        if (frequencyCompare != 0) {
            return frequencyCompare;
        }
        // For nodes with equal frequency, compare their data (if not null)
        if (this.data != null && o.data != null) {
            return Byte.compare(this.data, o.data);
        }
        // Ensure null data (internal nodes) have consistent ordering
        return (this.data == null) ? 1 : -1;
    }

}
