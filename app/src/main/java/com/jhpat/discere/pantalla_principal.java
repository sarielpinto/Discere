package com.jhpat.discere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class pantalla_principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,profile_principal.OnFragmentInteractionListener,fragment_principal.OnFragmentInteractionListener, Fragment_skype.OnFragmentInteractionListener{
    private TextView N,C;
    String usuario;
    FloatingActionMenu actionMenu;
    com.github.clans.fab.FloatingActionButton ver,agendar;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        actionMenu=(FloatingActionMenu) findViewById(R.id.fab);
        actionMenu.setClosedOnTouchOutside(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hview=navigationView.getHeaderView(0);
        N=(TextView) hview.findViewById(R.id.Correo);
        C=(TextView)hview.findViewById(R.id.Nombre);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle datos = this.getIntent().getExtras();
         usuario=datos.getString("hola");


        N.setText(usuario);
        cargarP();

    }


            @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment miFragment=null;
        boolean fragmentSeleccionado=false;
        if (id == R.id.nav_home) {
            miFragment = new fragment_principal();
            fragmentSeleccionado = true;
        }else if (id == R.id.nav_camera) {
            miFragment = new profile_principal();
            actionMenu.setVisibility(View.GONE);
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_skype){
            miFragment = new Fragment_skype();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_out) {
            onOutSesion();
            return true;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        if(fragmentSeleccionado=true){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onOutSesion() {
        Login.cambiarEstado(pantalla_principal.this,false);
        Intent intent= new Intent(pantalla_principal.this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void ver(View view){
        Intent inten= new Intent(pantalla_principal.this,MainActivity2.class);
        startActivity(inten);
    }
    public void agendar(View view){
        Intent intent= new Intent(pantalla_principal.this,Activity_Agendar.class);
        startActivity(intent);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
    public void datosc (String Correo)
    {

        AsyncHttpClient conexion = new AsyncHttpClient();
        final String url ="http://puntosingular.mx/cas/usuario.php"; //la url del web service
        // final String urlimagen ="http://dominio.com/assets/img/perfil/"; //aqui se encuentran todas las imagenes de perfil. solo especifico la ruta por que el nombre de las imagenes se encuentra almacenado en la bd.
        final RequestParams requestParams =new RequestParams();
        requestParams.add("correo",Correo); //envio el parametro
        conexion.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {

                if (statusCode==200) // Lo mismo que con LOGIN
                {


                    try {
                        jsonObject = new JSONObject(new String(responseBody));
                        //Apartir de aqui, les asigno a los editText el valor que obtengo del webservice


                        C.setText(jsonObject.getJSONArray("datos").getJSONObject(0).getString("name"));




                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(pantalla_principal.this, ""+e, Toast.LENGTH_SHORT).show();
                    }

                }

                else
                {


                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {

                Toast.makeText(pantalla_principal.this, "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show();

            }




        });




    }//FIN DATOSSC
    private  void cargarP()
    {
        SharedPreferences preferencia =getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        usuario= preferencia.getString("ID2", "NO EXISTE");

        datosc(usuario);
    }//Fin cargar preferencias

}

