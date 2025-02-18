package org.example.huffman;

public class CustomPriorityQueue {
    private ByteNode[] heap; // Array to store heap elements
    private int size; // Current number of elements in the heap

    public CustomPriorityQueue() {
        this.heap = new ByteNode[256];
        this.size = 0;
    }

    public void add(ByteNode node) {
        // Add the node at the end of the heap
        heap[size] = node;
        size++;

        // Heapify-up to maintain the min-heap property
        heapifyUp(size - 1);
    }

    public ByteNode dequeue() {
        if (isEmpty()) {
            return null;
        }

        // The root of the heap is the smallest element
        ByteNode min = heap[0];

        // Replace the root with the last element in the heap
        heap[0] = heap[size - 1];
        size--;

        // Heapify-down to maintain the min-heap property
        heapifyDown(0);

        return min;
    }

    public int size() {
        return size; // Return the number of elements in the heap
    }

    public boolean isEmpty() {
        return size == 0; // Return true if the heap is empty
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;

        // While the current node is smaller than its parent, swap them
        while (index > 0 && compare(heap[index], heap[parentIndex]) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int smallest = index;

        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        // Check if the left child is smaller than the current smallest
        if (leftChildIndex < size && compare(heap[leftChildIndex], heap[smallest]) < 0) {
            smallest = leftChildIndex;
        }

        // Check if the right child is smaller than the current smallest
        if (rightChildIndex < size && compare(heap[rightChildIndex], heap[smallest]) < 0) {
            smallest = rightChildIndex;
        }

        // If the smallest is not the current node, swap and continue heapifying down
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private int compare(ByteNode a, ByteNode b) {
        int frequencyCompare = Integer.compare(a.getFrequency(), b.getFrequency());
        if (frequencyCompare != 0) {
            return frequencyCompare;
        }
        // For nodes with equal frequency, compare their data (if not null)
        if (a.getData() != null && b.getData() != null) {
            return Byte.compare(a.getData(), b.getData());
        }
        // Ensure null data (internal nodes) have consistent ordering
        return (a.getData() == null) ? 1 : -1;
    }

    private void swap(int i, int j) {
        ByteNode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
