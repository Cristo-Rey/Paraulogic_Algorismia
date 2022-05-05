package com.example.paraulogic;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */

public class BSTMapping<K extends Comparable, V> {

    private K key;
    private V value;
    private Node left, right;

    public BSTMapping (){
        this.key = null;
        this.value = null;
        this.left = null;
        this.right = null;
    }

    public V put(K key, V value) {
        return (V) putRecursive(new Node(this.key, this.value), key, value);
    }

    private Node putRecursive(Node current, K key, V value) {
        if (current == null) {
            return new Node(key, value);
        }

        // Cas en el que és menor que el node arrel
        if (key.compareTo(current.key) < 0) {
            // El ficam a l'esquerra
            current.left = putRecursive(current.left, key, value);
        }
        // Cas en el que és major que el node arrel
        else if (key.compareTo(current.key) > 0) {
            // El ficam a la dreta
            current.right = putRecursive(current.right, key, value);
        } else {
            // Valor ja està dins l'arbre
            return current;
        }

        return current;
    }

    public V get(K key) {
        return (V)getRecursive(new Node(this.key, this.value), key);
    }

    private V getRecursive(Node current, K key){
        // Cas en el que ja hem trobat el que cercam
        if(current.key.equals(key)){
            return (V)current.value;
        }// Cas en el que el que cercam és menor que l'estat actual
        else if(current.key.compareTo(key)<0){
            return (V)getRecursive(current.left,key);
        }// Si no l'hem trobat i tampoc és menor, s'asumeix que és menor
        else{
            return (V)getRecursive(current.left,key);
        }

    }

    public V remove(K key) {
        return (V) removeRecursive(new Node(this.key, this.value), key).value;
    }

    private K minValue(Node root){
        //Inicialitzam minval
        K minval = (K) root.key;
        //find minval
        while (root.left != null)  {
            minval = (K) root.left.key;
            root = root.left;
        }
        return minval;
    }

    private Node removeRecursive(Node root, K key){
        //L'arbre es buit
        if (root == null)  return root;

        //Cercam per la part esquerra
        if (key.compareTo(root.key) < 0){
            root.left = removeRecursive(root.left, key);
        }
        //Cercam per la part dreta
        else if (key.compareTo(root.key) > 0){
            root.right = removeRecursive(root.right, key);
        }
        else{
            //El Node nomes te un fill
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            //El node te dos fills, per tant cercam el més petit
            root.key = minValue(root.right);

            root.right = removeRecursive(root.right, (K) root.key);
        }
        return root;
    }

    //Declaram la clase Node que servira per crear l'abre de cerca binari
    private class Node<K extends Comparable, V> {
        private V value;
        private K key;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        protected class Pair {
            private K key;
            private V value;
        }
    }
}
