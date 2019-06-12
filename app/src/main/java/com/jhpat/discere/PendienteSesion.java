package com.jhpat.discere;

import java.util.ArrayList;

class PendienteSesion{
    public String dateP="";
    public String nameP="";
    public String subjectP="";
    public String descriptionP="";


    public static ArrayList<PendienteSesion> date_collection_ar;
    public PendienteSesion(String date, String name, String subject, String description){

        this.dateP=date;
        this.nameP=name;
        this.subjectP=subject;
        this.descriptionP= description;

    }
}