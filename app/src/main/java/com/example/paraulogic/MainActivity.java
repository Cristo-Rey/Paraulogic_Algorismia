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

        paraules = new BSTMapping<>();
        llegirDiccionari();

        while (!comprovarConjunt()) ;
    }

    //Gestor de events botons principals
    public void setLletra(View view) {
        //Cream els objectes del botó pitjat i de la casella d'escriure
        Button btn = findViewById(view.getId());
        TextView text = findViewById(R.id.escritura);

        //Cream un nou string amb el contingut actual i el contingut del ja escrit
        String s = new StringBuilder().append(text.getText()).append(btn.getText()).toString();
        text.setText(s);
    }

    @SuppressLint("SetTextI18n")
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


    public void suprimeix(View view) {
        TextView text = findViewById(R.id.escritura);

        //Cream un nou string amb el contingut actual i el contingut del ja escrit
        String s = text.getText().toString();
        if (s.length() > 0) text.setText(s.substring(0, s.length() - 1));
    }

    public void shuffle(View view) {
        Character[] auxlletres = new Character[7];
        Iterator it = lletres.iterator();
        Random r = new Random();
        int m = 0;

        while (it.hasNext()) {
            auxlletres[m] = (Character) it.next();
            m++;
        }

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

    public void showActivity(View view) {
        Intent intent = new Intent(this, solucions.class);
        intent.putExtra(EXTRA_MESSAGE, generarSolucions());
        startActivity(intent);

    }

    public void generarConjuntLletres() {
        lletres = new UnsortedArraySet<>(7);
        generarArray();
        assignarLletres();
    }

    private boolean comprovarConjunt() {
        //Generam un conjunt de lletres
        generarConjuntLletres();

        //VARIABLES
        String paraula;
        int contador = 0;
        boolean trobat = true;
        Iterator it = lletres.iterator();
        Iterator<String> iterador = diccionari.iterator();
        Character[] auxlletres = new Character[7];

        //Guardam el conjunt generat a un array per facilitar el tractament
        for (int m = 0; it.hasNext(); m++) {
            auxlletres[m] = (Character) it.next();
        }

        //DEBUG: Veure el conjunt de lletres
        for (int i = 0; i < auxlletres.length; i++) {
            System.out.print(auxlletres[i] + " ");
        }
        System.out.println();

        //Bucle que recorr totes les paraules
        while (iterador.hasNext()) {
            paraula = iterador.next();
            trobat = paraula.length() >= 7; //Filtrat per millorar rendiment

            contador = 0;

            //Verificam que totes les lletres pertanyen al conjunt de lletres
            for (int i = 0; i < paraula.length() && trobat; i++) {
                for (int j = 0; j < auxlletres.length; j++) {
                    if (paraula.toUpperCase().charAt(i) == auxlletres[j]) {
                        trobat = true;
                        break;
                    } else {
                        trobat = false;
                    }
                }
            }

            //Verificam que la paraula tengui totes les lletres
            for (int i = 0; i < auxlletres.length && trobat; i++) {
                if (paraula.toUpperCase().contains(new StringBuilder().append(auxlletres[i]))) {
                    contador++;
                    if (contador == 7) {
                        System.out.println("TUTIFRUTI: " + paraula.toUpperCase()); //DEBUG
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String generarSolucions() {
        String solucions = "";

        //VARIABLES
        String paraula;
        int contador = 0;
        boolean trobat = true, tuti = false;
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
            trobat = paraula.length() >= 3; //Filtrat per millorar rendiment
            tuti = false;
            contador = 0;

            //Verificam que totes les lletres pertanyen al conjunt de lletres
            for (int i = 0; i < paraula.length() && trobat; i++) {
                for (int j = 0; j < auxlletres.length; j++) {
                    if (paraula.toUpperCase().charAt(i) == auxlletres[j]) {
                        trobat = true;
                        break;
                    } else {
                        trobat = false;
                    }
                }
            }

            //Verificam que la paraula tengui totes les lletres
            for (int i = 0; i < auxlletres.length && trobat; i++) {
                if (paraula.toUpperCase().contains(new StringBuilder().append(auxlletres[i]))) {
                    contador++;
                    if (contador == 7) {
                        tuti = true;
                    }

                }else if (i == auxlletres.length - 1) trobat =  false;
            }
            if (trobat) {
                if (tuti) {
                    solucions += "<font color = 'red'>" + paraula.toUpperCase() + "</font>, ";
                } else {
                    solucions += paraula.toUpperCase() + ", ";
                }
            }
        }
        return solucions;
    }
}

