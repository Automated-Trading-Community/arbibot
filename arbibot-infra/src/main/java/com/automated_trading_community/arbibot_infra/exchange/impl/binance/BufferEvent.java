package com.automated_trading_community.arbibot_infra.exchange.impl.binance;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic circular buffer implementation that supports a fixed size.
 * Once the buffer reaches its capacity, the oldest element is removed
 * and all elements are shifted.
 *
 * @param <T> the type of elements held in this buffer
 */
public class BufferEvent<T> {
    private final int capacity;
    private final T[] buffer;
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    /**
     * Constructs a CircularBuffer with the specified capacity.
     *
     * @param capacity the maximum number of elements the buffer can hold
     */
    @SuppressWarnings("unchecked")
    public BufferEvent(int capacity) {
        this.capacity = capacity;
        this.buffer = (T[]) new Object[capacity];
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index >= size)
     */

    /**
     * Adds an element to the buffer. If the buffer is full,
     * the oldest element is removed to make room for the new element.
     *
     * @param item the element to add
     */
    public void add(T item) {
        if (this.isFull()) {
            head = (head + 1) % capacity;
        } else {
            size++;
        }
        buffer[tail] = item;
        tail = (tail + 1) % capacity;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (index < 0 || index >= size)
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return buffer[(head + index) % capacity];
    }

    /**
     * Retrieves the last x elements of type I from the buffer.
     *
     * @param count the number of elements to retrieve
     * @param cls   the class of type I
     * @param <I>   a type that extends T
     * @return a list of the last x elements of type I from the buffer
     */
    public <I extends T> List<I> getLast(int count, Class<I> cls) {
        if (count >= this.capacity) {
            throw new IndexOutOfBoundsException("Count: " + count + ", Capacity: " + this.capacity);
        }
        List<I> result = new ArrayList<>();
        int elementsToRetrieve = Math.min(count, size);

        for (int i = 0; i < elementsToRetrieve; i++) {
            int index = (tail - 1 - i + capacity) % capacity;
            T element = buffer[index];
            if (cls.isInstance(element)) {
                result.add(cls.cast(element));
            }
        }
        return result;
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return the number of elements in the buffer
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the capacity of the buffer.
     *
     * @return the maximum number of elements the buffer can hold
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Checks if the buffer is empty.
     *
     * @return true if the buffer contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the buffer is full.
     *
     * @return true if the buffer is full, false otherwise
     */
    public boolean isFull() {
        return size == capacity;
    }
}