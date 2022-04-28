package com.example.paraulogic;

import java.util.Iterator;

/*
 * @author Joan López Ferrer
 */
public class UnsortedArraySet<E> {

    private E[] array;
    private int n;

    public UnsortedArraySet(int max) {
        this.array = (E[]) new Object[max];
        this.n = 0;
    }

    public boolean contains(E elem) {
        for (int i = 0; i < n; i++) {
            if (((E) this.array[i]).equals(elem)) {
                return true;
            }
        }
        return false;
    }

    public boolean add(E elem) {
        if (contains(elem)) {
            return false;
        } else {
            this.array[this.n++] = elem;
            return true;
        }
    }

    public boolean remove(E elem) {
        for (int i = 0; i < n; i++) {
            if (((E) this.array[i]).equals(elem)) {
                this.array[i] = this.array[--this.n];
                return true;
            }
        }
        return false;
    }

    public Iterator iterator() {
        Iterator it = new IteratorUnsortedArraySet();
        return it;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    private class IteratorUnsortedArraySet implements Iterator {
        private int idxIterator;

        private IteratorUnsortedArraySet() {
            idxIterator = 0;
        }

        @Override
        public boolean hasNext() {
            return idxIterator < n;
        }

        @Override
        public Object next() {
            idxIterator++;
            return array[idxIterator - 1];
        }
    }
}

