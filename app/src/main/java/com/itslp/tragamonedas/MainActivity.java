package com.itslp.tragamonedas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int dificultad = 100;
    int columna;
    boolean[] continuar = {false, false, false};
    TextView tv, textDificultad;
    int[] fotoId =
            {R.drawable.tm1,
                    R.drawable.tm2,
                    R.drawable.tm3,
                    R.drawable.tm4,
                    R.drawable.tm5,
                    R.drawable.tm6,
                    R.drawable.tm7,
                    R.drawable.tm8,
                    R.drawable.tm9
            };
    // Secuencia de imagenes en cada columnna.
// Ojo: aqui las filas estan intercambiadas
// por las columnas
    int[][] secuencia = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {8, 7, 6, 5, 4, 3, 2, 1, 0},
            {4, 5, 3, 2, 6, 7, 1, 0, 8}};

    ImageView[][] imagev = new ImageView[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.texto);
        textDificultad = (TextView) findViewById(R.id.dificultad);

        imagev[0][0] = (ImageView) findViewById(R.id.imageView11);
        imagev[1][0] = (ImageView) findViewById(R.id.imageView21);
        imagev[2][0] = (ImageView) findViewById(R.id.imageView31);
        imagev[0][1] = (ImageView) findViewById(R.id.imageView12);
        imagev[1][1] = (ImageView) findViewById(R.id.imageView22);
        imagev[2][1] = (ImageView) findViewById(R.id.imageView32);
        imagev[0][2] = (ImageView) findViewById(R.id.imageView13);
        imagev[1][2] = (ImageView) findViewById(R.id.imageView23);
        imagev[2][2] = (ImageView) findViewById(R.id.imageView33);

        Button boton1 = (Button) findViewById(R.id.button1);
        boton1.setOnClickListener(this);
        View boton2 = findViewById(R.id.button2);
        boton2.setOnClickListener(this);
        View boton3 = findViewById(R.id.button3);
        boton3.setOnClickListener(this);

        View boton4 = findViewById(R.id.button4);
        boton4.setOnClickListener(this);
        View boton5 = findViewById(R.id.button5);
        boton5.setOnClickListener(this);
        View boton6 = findViewById(R.id.button6);
        boton6.setOnClickListener(this);


    }//onCreate

    class MiAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... parameter) {
            int columna = parameter[0];

            while (continuar[columna]) {

                int elemento1 = secuencia[columna][0];
                for (int i = 0; i < 8; i++) {
                    secuencia[columna][i] = secuencia[columna][i + 1];
                }
                secuencia[columna][8] = elemento1;

                try {
                    Thread.sleep(Math.abs(dificultad));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(columna);
            }
            return "Stop columna " + (columna + 1);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            int columna = progress[0];
            for (int i = 0; i < 3; i++) {
                imagev[i][columna].setImageResource(fotoId[secuencia[columna][i]]);
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if (continuar[0] == false & continuar[1] == false
                    & continuar[2] == false) {

                if (secuencia[0][1] == secuencia[1][1]
                        & secuencia[0][1] == secuencia[2][1]) {
                    tv.setText("PREMIO!!!");
                } else {
                    tv.setText("Suerte la proxima vez");
                }

            } else
                tv.setText("" + result);
        }//onPostExecute

    }//MiAsyncTask

    @Override
    public void onClick(View boton) {
        // TODO Auto-generated method stub
        if (boton.getId() == R.id.button4 |
                boton.getId() == R.id.button5 |
                boton.getId() == R.id.button6) {

            if (boton.getId() == R.id.button4) dificultad = dificultad + 10;
            if (boton.getId() == R.id.button5) dificultad = 200;
            if (boton.getId() == R.id.button6) dificultad = dificultad - 10;
            textDificultad.setText("Dificultad " + dificultad);
        } else {

            if (boton.getId() == R.id.button1) columna = 0;
            if (boton.getId() == R.id.button2) columna = 1;
            if (boton.getId() == R.id.button3) columna = 2;

            continuar[columna] = !continuar[columna];
            if (continuar[columna]) {
                new MiAsyncTask().execute(columna);
                ((TextView) boton).setText("Parar");
            } else {
                ((TextView) boton).setText("Continue");
            }

        }//else
    }//onClick


}//Clase