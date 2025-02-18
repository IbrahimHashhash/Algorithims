package org.example.huffman;

import java.io.*;

public class HandleFile {
    public static int[] getFrequency(File file) {
        int[] frequency = new int[256]; // Initialize frequency for all bytes
        byte[] buffer = new byte[8192 * 2]; // Use a buffer for faster reading (8 KB is a common size)

        try (InputStream in = new FileInputStream(file)) {
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    frequency[buffer[i] & 0xFF]++; // Convert byte to unsigned value
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frequency;
    }    public static byte[] readAllBytes(String filePath) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024]; // Buffer for reading chunks of data
            byte[] result = new byte[0]; // Array to hold the accumulated data
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                // Create a new array to hold the accumulated data
                byte[] newResult = new byte[result.length + bytesRead];
                // Copy the existing data to the new array
                System.arraycopy(result, 0, newResult, 0, result.length);
                // Copy the new data from the buffer to the new array
                System.arraycopy(buffer, 0, newResult, result.length, bytesRead);
                result = newResult; // Update the result with the new array
            }
            return result;
        }
    }
}
