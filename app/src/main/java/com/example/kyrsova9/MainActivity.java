package com.example.kyrsova9;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Копочка для выдвижения меню слева
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//Подключение к БД

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Создание таблиц БД
        db.execSQL("CREATE TABLE IF NOT EXISTS facultet " +
                "(id integer primary key autoincrement, name_faculteta TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS napravlenie " +
                "(id integer primary key autoincrement, name_napravlenie TEXT)");
        //"(id integer primary key autoincrement, name_napravlenie TEXT, id_faculteta integer,foreign key (id_faculteta) references facultet (id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS groupa " +
                "(id integer primary key autoincrement, name_group TEXT)");
        //"(id integer primary key autoincrement, name_group TEXT,id_napravlenie integer, foreign key (id_napravlenie) references napravlenie (id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS studenti " +
                "(id integer primary key autoincrement,  last_name TEXT,first_name TEXT, middle_name TEXT, " +
                "id_facultet integer,id_napravlenie integer,id_groupa integer," +
                " foreign key (id_facultet) references facultet (id),foreign key (id_napravlenie) references napravlenie (id),foreign key (id_groupa) references groupa (id))");


        db.execSQL("CREATE TABLE IF NOT EXISTS forma_kontrol9 " +
                "(id integer primary key autoincrement, vid_formi TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS predmet " +
                "(id integer primary key autoincrement, name_premeta TEXT," +
                " id_forma_kontrol9 integer, foreign key (id_forma_kontrol9) references forma_kontrol9 (id))");


        db.execSQL("CREATE TABLE IF NOT EXISTS doljnost " +
                "(id integer primary key autoincrement, name_doljnost TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS rabotnik " +
                "(id integer primary key autoincrement,last_name TEXT, first_name TEXT, middle_name TEXT, " +
                "id_doljnost integer, foreign key (id_doljnost) references doljnost (id))");


        db.execSQL("CREATE TABLE IF NOT EXISTS jurnal " +
                "(id integer primary key autoincrement, id_rabotnika integer,id_predmeta integer, id_studentov integer,kolvo_propyskov integer," +
                " foreign key (id_rabotnika) references rabotnik (id),foreign key (id_predmeta) references predmet (id),foreign key (id_studentov) references studenti (id))");
 /*
String im9="Вася",fam="Васильев",ot="Васькович";
        ContentValues values = new ContentValues();
      for(Integer i=0;i<50;i++) {
          im9="Вася";fam="Васильев";ot="Васькович";
          values = new ContentValues();
          im9=im9+i.toString();
          fam=fam+i.toString();
          ot=ot+i.toString();
          values.put("last_name", fam);
          values.put("first_name", im9);
          values.put("middle_name", ot);
          values.put("id_facultet", 1);
          values.put("id_napravlenie", 1);
          values.put("id_groupa", 3);
          db.execSQL("PRAGMA foreign_keys=ON"); //при каждой сесии надо включать
          long newRowId = db.insert("studenti", null, values);

      }

  */
      //select

        /*
        Cursor query = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", "logbook"});
        TextView headerView = (TextView) findViewById(R.id.selectedMenuItem);
        if (!query.moveToFirst())
        {
            //headerView.setText("false");
        }
        else{
            int count = query.getInt(0);
            //headerView.setText(String.valueOf(count));
        }
        */
        //insert client+id пример добавления поля без внешнего ключа
        /*
        ContentValues values = new ContentValues();
        values.put("first_name", "Vasy");
        values.put("last_name", "Pupkin");
        values.put("middle_name", "Pupkevich");
        values.put("telephone", "+6(863)27-45");
        long newRowId = db.insert("client", null, values);
        headerView.setText(String.valueOf(newRowId));
        */
        //insert logbook+id+FK пример добавления с внешним ключом
        /*
        ContentValues values = new ContentValues();
        values.put("log", "Vasy");
        values.put("id_client", 1);
        values.put("id_book", 2);
        values.put("id_librarian", 3);
        //db.execSQL("PRAGMA foreign_keys=ON"); //при каждой сесии надо включать
        long newRowId = db.insert("logbook", null, values);
        headerView.setText(String.valueOf(newRowId));
        */
        //context delete db удвление бд
        /*
        Context context =  this;
        context.deleteDatabase("app.db");
        */
        //query.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //При нажатии выбираем меню слева и взависимости что выбрали выполняется
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id){

            case R.id.nav_spisok_jurnal:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Smotret_Jurnal.class);
                startActivity(intent);
                Toast.makeText(this, "Отмечать посещаемость", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dobavlenie:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie.class);
                startActivity(intent);
                    Toast.makeText(this, "Добавление", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dobavleniePrepodavatel9:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie_Prepodavatel9.class);
                startActivity(intent);
                Toast.makeText(this, "Работник", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dobavlenieDisciplina:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie_Predmeta.class);
                startActivity(intent);
                Toast.makeText(this, "Дисциплина", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_jurnal:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this,Dobavlenie_Jurnal.class);
                startActivity(intent);
                Toast.makeText(this, "Журнал", Toast.LENGTH_SHORT).show();
                break;
        }
        //После выбора элемента меню закрывает меню
//drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}