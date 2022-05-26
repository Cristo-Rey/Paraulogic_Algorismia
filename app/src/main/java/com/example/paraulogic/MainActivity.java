package com.example.paraulogic;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.paraulogic.MESSAGE";
    // Conjunt que representa les lletres amb les que es pot jugar
    UnsortedArraySet<Character> lletres;
    // El botó central ocupa la darrera posició
    int[] idButton = new int[7];
    // Mapping de les paraules que hem introduit
    BSTMapping<String, Integer> paraules;
    TreeSet<String> diccionari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cream arbre on guarderem les paraules escrites
        paraules = new BSTMapping<>();

        // Carregam el diccionari
        llegirDiccionari();

        // Cercam un conjunt de 7 lletres fins que es pugui fer un tuti
        while (!comprovarConjunt());
    }

    //Gestor de events botons principals (botons de lletres)
    public void setLletra(View view) {
        //Cream els objectes del botó pitjat i de la casella d'escriure
        Button btn = findViewById(view.getId());
        TextView text = findViewById(R.id.escritura);

        //Cream un nou string amb el contingut actual i el contingut del ja escrit
        String s = new StringBuilder().append(text.getText()).append(btn.getText()).toString();
        text.setText(s);
    }

    public void introdueixParaula(View view) {
        // Agafam el botó central
        Button btn = findViewById(idButton[idButton.length - 1]);
        // Agafam la paraula escrita
        TextView paraula = findViewById(R.id.escritura);

        // Comprovam si compté la lletra del botó central
        // Cas en el que sí te la lletra central
        if (paraula.getText().toString().length() >= 3) {
            if (paraula.getText().toString().contains(btn.getText().toString())) {
                // Comprovam si la lletra és del diccionari
                // Cas en el que és al diccionari
                if (diccionari.contains(paraula.getText().toString().toLowerCase())) {
                    Integer valor = paraules.get(paraula.getText().toString());
                    if (valor == null) {
                        paraules.put(paraula.getText().toString(), 1);
                    } else {
                        // augmentam en 1 la quantitat de vegades que l'hem introduit
                        valor = valor + 1;
                        paraules.put(paraula.getText().toString(), valor);
                    }

                    // Actialitzam la llista de paraules escrites
                    Iterator it = paraules.iterator();
                    String str = "";
                    BSTMapping.Pair p;
                    int contador = 0;
                    TextView respostes = findViewById(R.id.respostes);

                    while (it.hasNext()) {
                        contador++;
                        p = (BSTMapping.Pair) it.next();
                        str += p.key.toString() + "(" + p.value + "), ";
                    }
                    System.out.println(str);
                    respostes.setText("Has trobat " + contador + " paraules: " + str);
                }
                // Cas en el que la paraula no és del diccionari
                else {
                    System.out.println("[introdueixParaula() -> No és una paraula vàlida]");

                    // Ho mostram per la GUI
                    Context context = getApplicationContext();
                    CharSequence text = "No és una paraula vàlida";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
            // Cas en el que no compté la lletra central
            else {
                System.out.println("[introdueixParaula() -> No compté la lletra central]");

                // Ho mostram per la GUI
                Context context = getApplicationContext();
                CharSequence text = "No compté la lletra central";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        // Cas en el que la paraula no té 3 o més lletres
        else {
            System.out.println("[introdueixParaula() -> No compté 3 o més lletres]");

            // Ho mostram per la GUI
            Context context = getApplicationContext();
            CharSequence text = "No compté 3 o més lletres";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        //Borram el que s'havia introduit
        paraula.setText("");
    }

    // Botó suprimir (suprimeix una lletra)
    public void suprimeix(View view) {
        TextView text = findViewById(R.id.escritura);

        //Cream un nou string amb el contingut actual i el contingut del ja escrit
        String s = text.getText().toString();
        if (s.length() > 0) text.setText(s.substring(0, s.length() - 1));
    }

    // Botó shuffle
    public void shuffle(View view) {
        Character[] auxlletres = new Character[7];
        Iterator it = lletres.iterator();
        Random r = new Random();
        int m = 0;

        // Obtenim totes les lletres del nostre conjunt
        while (it.hasNext()) {
            auxlletres[m] = (Character) it.next();
            m++;
        }

        // Les remesclam totes excepte la central
        for (int i = 7 - 2; i > 0; i--) {
            int j = r.nextInt(i + 1);
            Character temp = auxlletres[i];
            auxlletres[i] = auxlletres[j];
            auxlletres[j] = temp;
        }

        for (int i = 0; i < idButton.length; i++) {
            Button btn = findViewById(idButton[i]);
            btn.setText(auxlletres[i].toString());
        }
    }

    //Generam l'array de lletres
    public void generarArray() {
        Random ran = new Random();
        char lletra;
        for (int i = 0; i < 7; ) {
            lletra = (char) (ran.nextInt(26) + 'A');
            if (lletres.add(lletra)) i++;
        }
    }

    //Metode que asigna les lletres als botons
    public void assignarLletres() {
        Button btn;
        Iterator iterador = lletres.iterator();
        Character lletra;

        idButton[0] = R.id.button1;
        idButton[1] = R.id.button2;
        idButton[2] = R.id.button3;
        idButton[3] = R.id.button4;
        idButton[4] = R.id.button5;
        idButton[5] = R.id.button6;
        idButton[6] = R.id.button0;

        for (int i = 0; i < 7; i++) {
            btn = findViewById(idButton[i]);
            lletra = (Character) iterador.next();
            btn.setText(lletra.toString());
        }
    }

    //Funció que llegueix el arxiu catala_filtrat i fica el seu contingut a un Arbre Vermell i Negre.
    public void llegirDiccionari() {
        //Declaram els objectes necessaris per llegir del arxiu
        InputStream is = getResources().openRawResource(R.raw.catala_filtrat);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        String liniaActual;

        //Cream l'arbre vermell i negre.
        diccionari = new TreeSet<>();

        try {
            //Bucle que llegueix tot l'arxiu fins al final
            liniaActual = r.readLine();
            while (liniaActual != null) {
                //Afegim la paraula llegida a l'arbre
                diccionari.add(liniaActual);
                //System.out.println(liniaActual);
                liniaActual = r.readLine();
            }
            //Control d'errors
        } catch (IOException e) {
            System.out.println("ERROR (llegirDiccionari): " + e);
        }
    }

    // Per el botó de solucions
    public void showActivity(View view) {
        Intent intent = new Intent(this, solucions.class);
        intent.putExtra(EXTRA_MESSAGE, generarSolucions());
        startActivity(intent);

    }

    // Generar un conjunt de lletres
    public void generarConjuntLletres() {
        lletres = new UnsortedArraySet<>(7);
        generarArray();
        assignarLletres();
    }

    // Comprovam que el conjunt de lletres pugui formar un tuti
    private boolean comprovarConjunt() {
        //Generam un conjunt de lletres
        generarConjuntLletres();

        //VARIABLES
        String paraula;
        boolean trobat = true;
        boolean vocal;
        Iterator it = lletres.iterator();
        Iterator<String> iterador = diccionari.iterator();
        Character[] auxlletres = new Character[7];

        //Guardam el conjunt generat a un array per facilitar el tractament
        for (int m = 0; it.hasNext(); m++) {
            auxlletres[m] = (Character) it.next();
        }

        //Revisam que hi hagui almenys una vocal
        vocal = comprovarVocal(auxlletres);

        //DEBUG: Veure el conjunt de lletres
        for (int i = 0; i < auxlletres.length; i++) {
            System.out.print(auxlletres[i] + " ");
        }
        System.out.println();

        //Bucle que recorr totes les paraules
        while (iterador.hasNext() && vocal) {
            paraula = iterador.next();

            //Verificam que totes les lletres pertanyen al conjunt de lletres
            if (paraula.length() >= 7) {
                trobat = paraulaPertanyConjunt(paraula, auxlletres);
            }

            //Verificam que la paraula sigui un TUTI
            if (trobat) {
                if (paraulaTUTI(paraula, auxlletres)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Per mostrar les solucions
    private String generarSolucions() {
        String solucions = "";

        //VARIABLES
        String paraula;
        boolean trobat = false;
        Iterator it = lletres.iterator();
        Iterator<String> iterador = diccionari.iterator();
        Character[] auxlletres = new Character[7];

        //Guardam el conjunt generat a un array per facilitar el tractament
        for (int m = 0; it.hasNext(); m++) {
            auxlletres[m] = (Character) it.next();
        }

        //Bucle que recorr totes les paraules
        while (iterador.hasNext()) {
            paraula = iterador.next();

            //Verificam que totes les lletres pertanyen al conjunt de lletres
            if (paraula.length() >= 3) {
                trobat = paraulaPertanyConjunt(paraula, auxlletres);
            }

            // Si és un tuti el posam en vermell
            if (trobat) {
                if (paraulaTUTI(paraula, auxlletres)) {
                    solucions += "<font color = 'red'>" + paraula.toUpperCase() + "</font>, ";
                } else {
                    solucions += paraula.toUpperCase() + ", ";
                }
            }
        }
        return solucions;
    }

    // Verificam que una paraula es pugui formar amb i només amb les lletres del nostre conjunt
    private boolean paraulaPertanyConjunt(String paraula, Character[] auxlletres) {
        boolean resultat = true;
        for (int i = 0; i < paraula.length() && resultat; i++) {
            for (int j = 0; j < auxlletres.length; j++) {
                if (paraula.toUpperCase().charAt(i) == auxlletres[j]) {
                    resultat = true;
                    break;
                } else {
                    resultat = false;
                }
            }
        }
        return resultat;
    }

    //Verificam que en el conjunt existeixi una vocal
    private boolean comprovarVocal(Character[] conjuntLletres) {
        boolean resultat = false;
        Character[] vocals = {'A', 'E', 'I', 'O', 'U'};
        for (int i = 0; i < conjuntLletres.length && !resultat; i++) {
            for (int j = 0; j < vocals.length; j++) {
                if (conjuntLletres[i] == vocals[j]) {
                    resultat = true;
                    break;
                }
            }
        }
        return resultat;
    }

    // Comprovam que la paraula sigui o no sigui un tuti
    private boolean paraulaTUTI(String paraula, Character[] auxlletres) {
        int contador = 0;

        for (int i = 0; i < auxlletres.length; i++) {
            if (paraula.toUpperCase().contains(new StringBuilder().append(auxlletres[i]))) {
                contador++;
                if (contador == 7) {
                    return true;
                }
            }
        }
        return false;
    }
}

