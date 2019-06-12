package com.jhpat.discere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import cz.msebera.android.httpclient.Header;

public class Profile_Password extends AppCompatActivity {
    EditText et_password, et_cpassword, et_cpassword_2;
    Button btn_actualizarCon;

    public static String NAME1, LAST_NAME1, GENDER1, ID1, EMAIL1, TEL1, PASSWORD1;//CLASE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_password);

        et_password = (EditText) findViewById(R.id.enter_Password);
        et_cpassword = (EditText)findViewById(R.id.et_cpass);
        et_cpassword_2=(EditText)findViewById(R.id.et_cpass_2);
        btn_actualizarCon= (Button) findViewById(R.id.btn_updateCon);



        btn_actualizarCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargarP();

            }
        });
    }

    public void editarContraseña(String id_usu)
    {
        AsyncHttpClient conexion = new AsyncHttpClient();
        final String url ="http://puntosingular.mx/cas/consulta_password.php"; //la url del web service
        final RequestParams requestParams =new RequestParams();

        //ENVIO LOS PARAMETROS
        requestParams.add("id_", ID1);
        requestParams.add("password",et_cpassword_2.getText().toString());

        conexion.post(url, requestParams, new AsyncHttpResponseHandler() {



            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                if (statusCode==200) // Lo mismo que con LOGIN
                {
                    Toast.makeText(Profile_Password.this, "Change saved", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Profile_Password.this, profile_principal.class);
                    startActivity(i);
                    finish();
                }
                else
                {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Profile_Password.this, "ERROR", Toast.LENGTH_SHORT).show();
            }




        });

    }//FIN EDITAR CONTRASEÑA


    private  void cargarP()
    {
        SharedPreferences preferencia =getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        ID1 = preferencia.getString("ID2", "NO EXISTE");
        String c = preferencia.getString("user", "NO EXISTE");
        String contra = preferencia.getString("password", "NO EXISTE");

        String pas1, pas2, con;
        pas1 = et_cpassword.getText().toString();
        pas2=et_cpassword_2.getText().toString();
        con = et_password.getText().toString();//UNO


        if (con.equals(contra))
        {
            if(pas1.equals(pas2))
            {
                editarContraseña(ID1);

            }else
            {
                Toast.makeText(Profile_Password.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(Profile_Password.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }



    }//Fin cargar preferencias
}
