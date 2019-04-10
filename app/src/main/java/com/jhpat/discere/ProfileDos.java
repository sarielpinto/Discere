package com.jhpat.discere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ProfileDos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dos);

        ImageView ImangenPerfil = (ImageView)findViewById(R.id.ImageProfile) ;
        ImangenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ProfileDos.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
