package org.example.huffman;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HuffmanDecompression {
    public static void decompress(String src, String dst) {
        try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(src))) {
            // Deserialize the Huffman Tree
            String serializedTree = (String) inStream.readObject();

            // Read the compressed data (including the padding byte)
            byte[] compressedData = inStream.readAllBytes();

            // Deserialize the Huffman tree
            ByteNode root = Header.deserializeTree(serializedTree);

            // Decode the data
            byte[] originalData = decompressData(compressedData, root);

            // Write decompressed data to the destination file
            try (FileOutputStream outStream = new FileOutputStream(dst)) {
                outStream.write(originalData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] decompressData(byte[] compressedData, ByteNode root) {
        int padding = compressedData[compressedData.length - 1]; // Retrieve padding length
        byte[] actualData = Arrays.copyOf(compressedData, compressedData.length - 1); // Remove padding byte

        // Convert compressed bytes to a bit string
        StringBuilder bitString = new StringBuilder();
        for (byte b : actualData) {
            bitString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        bitString.setLength(bitString.length() - padding); // Remove padding bits

        // Decode the bit string using the Huffman Tree
        List<Byte> byteList = new LinkedList<>();
        ByteNode currentNode = root;
        for (char bit : bitString.toString().toCharArray()) {
            currentNode = (bit == '0') ? currentNode.getLeft() : currentNode.getRight();
            if (currentNode.getData() != null) { // Reached a leaf node
                byteList.add(currentNode.getData()); // Add data to LinkedList
                currentNode = root; // Restart at the root
            }
        }

        // Convert the list of bytes to a byte array
        byte[] originalData = new byte[byteList.size()];
        for (int i = 0; i < originalData.length; i++) {
            originalData[i] = byteList.get(i);
        }

        return originalData;
    }

}
