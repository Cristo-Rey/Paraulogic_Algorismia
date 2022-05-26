package com.example.paraulogic;

import java.util.Iterator;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */
public class UnsortedArraySet<E> {

    // ATRIBUTS
    private E[] array;
    private int n;

    // Constructor que defineix el màxim del conjunt
    public UnsortedArraySet(int max) {
        this.array = (E[]) new Object[max];
        this.n = 0;
    }

    // Mètode contains
    public boolean contains(E elem) {
        for (int i = 0; i < n; i++) {
            if (((E) this.array[i]).equals(elem)) {
                // Si ja ho hem trobat retornam directament
                return true;
            }
        }
        return false;
    }

    // Per afegir al conjunt
    public boolean add(E elem) {
        // Recorrem només una vegade l'array
        for (int i = 0; i < n; i++) {
            if (((E) this.array[i]).equals(elem)) {
                // Si ja ho hem trobat retornam directament
                return false;
            }
        }
        // Si no l'hem trobat, l'afegim si hi cap
        if (n < this.array.length) {
            this.array[this.n++] = elem;
            return true;
        }
        // Si no hi cap return false
        return false;
    }

    // Per eliminar un element del conjunt
    public boolean remove(E elem) {
        for (int i = 0; i < n; i++) {
            // Miram mem si el trobam. Si el trobam l'eliminam i return true
            if (((E) this.array[i]).equals(elem)) {
                this.array[i] = this.array[--this.n];
                return true;
            }
        }
        // Si no hem eliminat res, return false
        return false;
    }

    // Per obtenir un iterador
    public Iterator iterator() {
        Iterator it = new IteratorUnsortedArraySet();
        return it;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    // Interfície Iterator per recórrer el conjunt
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

