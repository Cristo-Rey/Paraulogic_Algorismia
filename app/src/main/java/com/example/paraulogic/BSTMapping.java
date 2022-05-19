package com.example.paraulogic;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */

import java.util.Iterator;
import java.util.Stack;

public class BSTMapping<K extends Comparable, V> {

    private Node root;

    public BSTMapping() {
        // Inicialitzam arrel
        this.root = null;
    }


    public V put(K key, V value) {
        Cerca c = new Cerca(null);
        this.root = putRecursive(key, value, root, c);
        return c.value;
    }

    private Node putRecursive(K key, V value, Node current, Cerca c) {
        // Cas en el que no hi era i ja hem trobat un lloc per situar-lo
        if (current == null) {
            return new Node(key, value);
        } else {
            // Cas en el que ja hi era, per tant, actualitzam el seu valor
            if (current.key.equals(key)) {
                c.value = (V) current.value;
                current.value = value;
                return current;
            }

            // Casos en el que encara no l'hem trobat però seguim cercant
            if (key.compareTo(current.key) < 0) {
                current.left = putRecursive(key, value, current.left, c);
            } else {
                current.right = putRecursive(key, value, current.right, c);
            }
            return current;
        }
    }

    public V get(K key) {
        Node n = getRecursive(key, root);
        if (n == null) {
            return null;
        } else {
            return n.value;
        }
    }

    private Node getRecursive(K key, Node current) {
        // Cas en el que no existeix aquest element
        if (current == null) {
            return null;
        } else {
            // Cas en el que trobam l'element
            if (current.key.equals(key)) {
                return current;
            }
            // Cas en el que no el trobam però seguim cercant
            if (key.compareTo(current.key) < 0) {
                return getRecursive(key, current.left);
            } else {
                return getRecursive(key, current.right);
            }
        }
    }

    public V remove(K key) {
        Cerca cerca = new Cerca(null);
        this.root = removeRecursive(key, root, cerca);
        return root.value;
    }


    // Mètode recursiu per a l'eliminació d'un node
    private Node removeRecursive(K key, Node current, Cerca cerca) {
        // Cas en el que no l'hem trobat
        if (current == null) {
            return null;
        }
        // Cas en el que el trobma
        if (current.key.equals(key)) {
            // Eliminam el node
            cerca.value = (V) current.value;
            // Comprovam els fills del Node i els reordenam
            if (current.left != null && current.right != null) {
                Node plowest = current.right;
                Node parent = current;
                while (plowest.left != null) {
                    parent = plowest;
                    plowest = plowest.left;
                }
                plowest.left = current.left;
                if (plowest != current.right) {
                    parent.left = plowest.right;
                    plowest.right = current.right;
                }
                return plowest;
            } else if (current.left == null && current.right != null) {
                return current.right;
            } else if (current.left != null && current.right == null) {
                return current.left;
            }
        }
        // Casos en el que no el trobam
        if (key.compareTo(current.key) < 0) {
            current.left = removeRecursive(key, current.left, cerca);
        } else {
            current.right = removeRecursive(key, current.right, cerca);
        }
        return current;
    }

    public Iterator iterator() {
        Iterator it = new IteratorBSTMapping();
        return it;
    }


    public boolean isEmpty() {
        return root == null;
    }


    /*
     *
     *   DECLARACIÓ DE CLASSES INTERNES AUXILIARS
     *
     * */

    // CLASSE NODE QUE SERVEIX PER FORMAR L'ARBRE
    private class Node {
        private V value;
        private K key;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    // CLASSE CERCA PER PODER LOCALITZAR ELS NODES QUAN FEIM UN get(), put() O remove()
    private class Cerca {
        V value;

        public Cerca(V v) {
            value = v;
        }
    }

    // CLASSE PAIR PER PODER RETORNAR PARELLES CLAU VALOR
    protected class Pair {
        V value;
        K key;

        public Pair(K k, V v) {
            value = v;
            key = k;
        }

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }
    }


    // ITERADOR PER PODER RECOLLIR ELS NODES DE L'ARBRE
    private class IteratorBSTMapping implements Iterator {

        // Usarem una pila per poder retornar els nodes de menor a major
        private Stack<Node> pila;

        // Empilam nodes
        public IteratorBSTMapping() {
            Node p;
            pila = new Stack();
            if (root != null) {
                p = root;
                while (p.left != null) {
                    pila.push(p);
                    p = p.left;
                }
                pila.push(p);
            }
        }

        public boolean hasNext() {
            return !pila.isEmpty();
        }

        // Extreim els nodes de manera que poguem teure'ls en un inordre
        public Object next() {
            Node p = pila.pop();
            Pair pair = new Pair(p.key, p.value);
            if (p.right != null) {
                p = p.right;
                while (p.left != null) {
                    pila.push(p);
                    p = p.left;
                }
                pila.push(p);
            }
            return pair;
        }
    }

}
