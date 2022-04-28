package com.example.paraulogic;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */

public class BSTMapping<K extends Comparable, V> {

    //Declaram la clase Node que servira per crear l'abre de cerca binari
    private class Node<K extends Comparable, V> {
        private V value;
        private K key;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private K key;
    private V value;
    private Node left, right;

    public V put(K key, V value) {
        return (V) addRecursive(new Node(this.key, this.value), key, value);
    }

    private Node addRecursive(Node current, K key, V value) {
        if (current == null) {
            return new Node(key, value);
        }

        // Cas en el que és menor que el node arrel
        if (key.compareTo(current.key) < 0) {
            // El ficam a l'esquerra
            current.left = addRecursive(current.left, key, value);
        }
        // Cas en el que és major que el node arrel
        else if (key.compareTo(current.key) > 0) {
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
