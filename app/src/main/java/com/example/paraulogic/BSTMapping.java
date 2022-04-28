package com.example.paraulogic;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */

public class BSTMapping<K, V> {

    //Declaram la clase Node que servira per crear l'abre de cerca binari
    private class Node<K, V> {
        private V value;
        private K key;
        private Node l,r;
    }

}
