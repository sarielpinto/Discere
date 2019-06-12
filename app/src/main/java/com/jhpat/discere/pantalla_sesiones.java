package com.jhpat.discere;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class pantalla_sesiones extends AppCompatActivity {
    EditText año = null;
    Spinner mes = null;
    EditText dia = null;                    //
    CheckBox duracion = null;              // Declaracion de Recursos
    Spinner hora = null;                  //
    EditText minuto = null;              //
    ButtonBarLayout agregar = null;     //
    EditText titulo = null;            //
    EditText descripcion = null;      //
    EditText localizacion = null;    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_sesiones);

        año = (EditText) findViewById(R.id.etAño);                          //
        mes = (Spinner) findViewById(R.id.spMes);                          //
        dia = (EditText) findViewById(R.id.etDia);                        //
        duracion = (CheckBox) findViewById(R.id.chkDuracion);            // Instanciacion de recursos
        hora = (Spinner) findViewById(R.id.spHora);                     //
        minuto = (EditText) findViewById(R.id.etMinuto);               //
        titulo = (EditText) findViewById(R.id.etTitulo);              //
        descripcion = (EditText) findViewById(R.id.etDescripcion);   //
        localizacion = (EditText) findViewById(R.id.etLocation);    //


        ArrayAdapter<CharSequence> adapmes = ArrayAdapter.createFromResource(this, R.array.meses,android.R.layout.simple_spinner_item);
        mes.setAdapter(adapmes); //Llenar Spinner de mes

        ArrayAdapter<CharSequence> adaphora = ArrayAdapter.createFromResource(this, R.array.horas,android.R.layout.simple_spinner_item);
        hora.setAdapter(adaphora);//Llenar Spinner de horas

        //------------------------------------------------------------------------------------------------------------------------>

    }

    public void Agregar(View v) {


        Calendar cal = Calendar.getInstance();


        boolean val = false; //Controlador del ciclo while
        Intent intent = null;
        while (val == false) {

            try {
                cal.set(Calendar.YEAR, Integer.parseInt(año.getText().toString()));                 //
                cal.set(Calendar.MONTH, (Integer.parseInt(mes.getSelectedItem().toString()) - 1));   // Set de AÑO MES y Dia
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia.getText().toString()));       //


                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt((hora.getSelectedItem().toString())));// Set de HORA y MINUTO
                cal.set(Calendar.MINUTE, Integer.parseInt(minuto.getText().toString()));            //

                intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);

                intent.putExtra(CalendarContract.Events.ALL_DAY, duracion.isSelected());
                intent.putExtra(CalendarContract.Events.TITLE, titulo.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, localizacion.getText().toString());

                startActivity(intent);
                val = true;
            } catch (Exception e) {
                año.setText("");
                dia.setText("");
                Toast.makeText(getApplicationContext(), "Fecha Inválida", Toast.LENGTH_LONG).show();
            }
        }

    }

}
