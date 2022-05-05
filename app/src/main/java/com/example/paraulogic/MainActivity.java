package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Random;

/*
 * @author Joan López Ferrer & Xavier Vives Marcus
 */

public class MainActivity extends AppCompatActivity {
    // Conjunt que representa les lletres amb les que es pot jugar
    UnsortedArraySet lletres;
    // El botó central ocupa la darrera posició
    int[] idButton = new int[7];
    // Mapping de les paraules que hem introduit
    BSTMapping paraules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lletres = new UnsortedArraySet(7);
        generarArray();
        assignarLletres();
        paraules = new BSTMapping();

    }

    //Gestor de events botons principals
    public void setLletra(View view) {
        //Cream els objectes del botó pitjat i de la casella d'escriure
        Button btn = (Button) findViewById(view.getId());
        TextView text = (TextView) findViewById(R.id.escritura);

        //Cream un nou string amb el contingut actual i el contingut del ja escrit
        String s = new StringBuilder().append(text.getText()).append(btn.getText()).toString();
        text.setText(s);
    }

    public void introdueixParaula(View view) {
        // Agafam el botó central
        Button btn = (Button) findViewById(idButton[idButton.length - 1]);
        // Agafam la paraula escrita
        TextView paraula = (TextView) findViewById(R.id.escritura);

        // Comprovam si compté la lletra del botó central
        // Cas en el que sí que la té
        if (paraula.getText().toString().contains(btn.getText().toString())) {
            Integer valor = (int) paraules.get(paraula.getText().toString());
            if (valor == null) {
                paraules.put(paraula.getText().toString(), 1);
            }else{
                // Eliminam la paraula
                paraules.remove(paraula.getText().toString());
                // La tornam a ficar amb el valor sumat a 1 per indicar que l'hem posat més d'una vegada
                paraules.put(paraula.getText().toString(),(Integer)(valor+1));
            }
        }
        // Cas en el que no la té
        else {
            System.out.println("[introdueixParaule() -> No compté la lletra central]");
        }
    }


    public void suprimeix(View view) {
        TextView text = (TextView) findViewById(R.id.escritura);

        //Cream un nou string amb el contingut actual i el contingut del ja escrit
        String s = text.getText().toString();
        if (s.length() > 0) text.setText(s.substring(0, s.length() - 1));
    }

    public void shuffle(View view) {
        Character auxlletres[] = new Character[7];
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
            Button btn = (Button) findViewById(idButton[i]);
            btn.setText(auxlletres[i].toString());
        }
    }

    //Generam l'array de lletres
    public void generarArray() {

        Random ran = new Random();
        char lletra;
        for (int i = 0; i < 7; ) {
            lletra = (char) (ran.nextInt(26) + 'A');
            if (lletres.add((Character) lletra)) i++;
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
            btn = (Button) findViewById(idButton[i]);
            lletra = (Character) iterador.next();
            btn.setText(lletra.toString());
        }
    }
}

