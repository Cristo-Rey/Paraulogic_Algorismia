package com.example.paraulogic;

public class BSTMapping<V, K> {
    private K key;
    private V value;
    private Node l, r;

    public V put(K key, V value) {
        return null;
    }

    private Node addRecursive(Node current, K key, V value) {
        if (current == null) {
            return new Node(key, value);
        }

        // Cas en el que és menor que el node arrel
        if (value.compareTo(current.value) < 0) {
            // El ficam a l'esquerra
            current.left = addRecursive(current.left, key, value);
        }
        // Cas en el que és major que el node arrel
        else if (value.compareTo(current.value) > 0) {
            // El ficam a la dreta
            current.right = addRecursive(current.right, key, value);
        } else {
            // Valor ja està dins l'arbre
            return current;
        }

        return current;
    }


    public V get(K key) {
        return null;
    }

    public V remove(K key) {
        return null;
    }


}
