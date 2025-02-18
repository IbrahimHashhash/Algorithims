package org.example.huffman;
import java.io.*;

public class HuffmanCompression {
    int[] frequency;
    private static final String[] huffCodes = new String[256]; // array to store huffman codes for each byte
    private String serializeTree;
    private ByteNode root;

    public HuffmanCompression(File file) {
        frequency = HandleFile.getFrequency(file); // get frequency of each byte from the file
    }

    public void compress(String src, String dst) throws IOException {
        // build the huffman tree and check if it is valid
        root = HuffmanTree.buildTree(frequency);
        if (root == null) {
            throw new IllegalStateException("huffman tree could not be built. check frequency array.");
        }

        // serialize the tree for use during decompression
        serializeTree = Header.serializeTree(root);

        // generate huffman codes for the tree
        generateHuffmanCodes(root, "");

        // read all bytes from the input file
        byte[] inputBytes = HandleFile.readAllBytes(src);

        // compress the input data
        byte[] compressedData = compressData(inputBytes);

        // write the serialized tree and compressed data to the output file
        writeToFile(dst, serializeTree, compressedData);
    }

    private void generateHuffmanCodes(ByteNode node, String code) {
        if (node != null) {
            if (node.getData() != null) { // if it's a leaf node
                huffCodes[node.getData() & 0xFF] = code; // store the code for the byte
            } else { // if it's an internal node
                generateHuffmanCodes(node.getLeft(), code + "0"); // go left and add "0" to the code
                generateHuffmanCodes(node.getRight(), code + "1"); // go right and add "1" to the code
            }
        }
    }

    private byte[] compressData(byte[] bytes) {
        StringBuilder encodedString = new StringBuilder();

        // convert each byte into its corresponding huffman code
        for (byte b : bytes) {
            int index = b & 0xFF; // make sure the index is unsigned
            String code = huffCodes[index];
            if (code == null) {
                throw new IllegalArgumentException("no huffman code for byte: " + index);
            }
            encodedString.append(code);
        }

        // add padding to make the bit length a multiple of 8
        int padding = (8 - (encodedString.length() % 8)) % 8;
        for (int i = 0; i < padding; i++) {
            encodedString.append("0");
        }

        // convert the bit string into a byte array
        int byteLength = encodedString.length() / 8;
        byte[] compressedData = new byte[byteLength + 1];
        for (int i = 0; i < encodedString.length(); i += 8) {
            String byteString = encodedString.substring(i, i + 8);
            compressedData[i / 8] = (byte) Integer.parseInt(byteString, 2);
        }

        // store the padding length in the last byte
        compressedData[compressedData.length - 1] = (byte) padding;

        return compressedData;
    }

    public void writeToFile(String dst, String serializeTree, byte[] compressedData) throws IOException {
        try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(dst))) {
            outStream.writeObject(serializeTree); // write the serialized huffman tree
            outStream.write(compressedData); // write the compressed data
            outStream.flush(); // ensure all data is written
        }
    }

    public String generateAndReturnCodeTable(ByteNode root) {
        StringBuilder stringBuilder = new StringBuilder();
        generateAndPrintCodeTableHelper(root, "", stringBuilder);
        return stringBuilder.toString(); // return the code table as a string
    }

    private void generateAndPrintCodeTableHelper(ByteNode node, String code, StringBuilder stringBuilder) {
        if (node == null) {
            return;
        }

        // if it's a leaf node, add the character and its code to the string builder
        if (node.isLeaf()) {
            stringBuilder.append((char) (node.getData() & 0xFF))
                    .append(" : ")
                    .append(code)
                    .append("\n");
        }

        // process the left and right child nodes recursively
        generateAndPrintCodeTableHelper(node.getLeft(), code + "0", stringBuilder);
        generateAndPrintCodeTableHelper(node.getRight(), code + "1", stringBuilder);
    }

    public String getSerializeTree() {
        return serializeTree; // return the serialized tree
    }

    public long getOriginalFileSize(String src) {
        return new File(src).length(); // get the original file size in bytes
    }

    public long getCompressedFileLength(String dest) {
        return new File(dest).length(); // get the compressed file size in bytes
    }

    public double calculateCompressionRate(long originalSize, long compressedSize) {
        return (double) (originalSize / compressedSize); // calculate the compression rate as a percentage
    }

    public ByteNode getRoot() {
        return root; // return the root of the huffman tree
    }
}
