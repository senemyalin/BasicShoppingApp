package com.example.basicshoppingapp.Class;

import android.provider.ContactsContract;

import org.sql2o.Sql2o;

public class DatabaseStuff {
  public  static Sql2o sql2o=new Sql2o("jdbc:mysql://10.0.2.2:3306/basicshoppingapp","test","test");
}
