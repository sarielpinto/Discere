package com.jhpat.discere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity2 extends AppCompatActivity {
    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    JSONObject jsonObject;
    JSONObject jsonObjecte;
    String fecha;
    private String nombreE;
    private String fechaE;
    private String horaIE;
    private String horaFE;
    private String mandafecha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Aqui llamamo al metodo


        datosc("1");

        //


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(this, cal_month,HomeCollection.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4&&cal_month.get(GregorianCalendar.YEAR)==2017) {
                    cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(MainActivity2.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5&&cal_month.get(GregorianCalendar.YEAR)==2018) {
                    cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(MainActivity2.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                String fecham= selectedGridDate;
              //  ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, MainActivity2.this);
                //Toast.makeText(MainActivity2.this, "Me tocaste pervertido :'0 "+HwAdapter.day_string.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity2.this, dialogo.class);
                i.putExtra("Rnombre", nombreE);
                i.putExtra("Rfecha", fecham);
                i.putExtra("RhoraI", horaIE);
                i.putExtra("RhoraF", horaFE);

                obtenerFecha(fecham);
                startActivity(i);
            }

        });
    }
    public void obtenerFecha (String Correo)
    {
//Conexion
        AsyncHttpClient conexion = new AsyncHttpClient();
        final String url ="http://puntosingular.mx/cas/obtenerS.php"; //la url del web service
        final RequestParams requestParams =new RequestParams();
        requestParams.add("fecha",Correo); //envio el parametro

        conexion.post(url, requestParams, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                try {
                    jsonObjecte = new JSONObject(new String(responseBody));
                    //Apartir de aqui, les asigno a los editText el valor que obtengo del webservice

                    nombreE=jsonObjecte.getJSONArray("datos").getJSONObject(0).getString("name_fellow");
                    fechaE=jsonObjecte.getJSONArray("datos").getJSONObject(0).getString("date");
                    horaIE=jsonObjecte.getJSONArray("datos").getJSONObject(0).getString("start_time");
                    horaFE=jsonObjecte.getJSONArray("datos").getJSONObject(0).getString("end_time");




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }





    public void datosc (String Correo)
    {
        HomeCollection.date_collection_arr=new ArrayList<HomeCollection>();
        // HomeCollection.date_collection_arr.add( new HomeCollection("2019-07-08" ,"Diwali","Holiday","this is holiday"));


        AsyncHttpClient conexion = new AsyncHttpClient();
        final String url ="http://puntosingular.mx/cas/obtener_fecha_lessons.php"; //la url del web service obtener_fecha_lessons.ph
        final RequestParams requestParams =new RequestParams();
        requestParams.add("correo",Correo); //envio el parametro

        conexion.post(url, requestParams, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                try {
                    jsonObject = new JSONObject(new String(responseBody));
                    //Apartir de aqui, les asigno a los editText el valor que obtengo del webservice

                    //fechaFinal=jsonObject.getJSONArray("datos").getJSONObject(1).getString("end_date");
                    // sTipo=jsonObject.getJSONArray("datos").getJSONObject(0).getString("type");
                    int tamanio =jsonObject.getJSONArray("datos").length();
                    String fechaInicio[]=new String[tamanio];
                    String tipo[]=new String[tamanio];

                    for (int i=0; i<tamanio; i++)
                    {
                        fechaInicio[i]=jsonObject.getJSONArray("datos").getJSONObject(i).getString("start_date");
                        tipo[i]=jsonObject.getJSONArray("datos").getJSONObject(i).getString("type");

                        HomeCollection.date_collection_arr.add( new HomeCollection(fechaInicio[i] ,"Evento: "+tipo[i],"Holiday","this is holiday"));


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }//FIN
}
