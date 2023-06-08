package com.example.kyrsova9;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Smotret_Jurnal extends AppCompatActivity {
    ArrayList<Using_adapter_dvapol9_dobavleni9> spisok;
   Adapter_dvapol9_dobavleni9 adapter_dobavlenie;
    ArrayList<Using_adapter_dobavleni9_jurnala> spisok_jurnala  ;
    Adapter_dobavleni9_jurnala adapter_dobavleni9_jurnala;
    List<Integer> list_id_f,list_id_n,list_id_g;
    List<String> list_string;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.okno_smotret_jurnal);
        final Spinner sp1 = (Spinner) findViewById(R.id.spinner_vibor_vsego);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_Prepod,query_Predmet,query_D,query_Fk,query_S,query_F,query_N,query_G,query_SJ,query_1,query_2,query_3;
        List<Adapter_tri_dobavleni9> jurnal_id_name = new ArrayList<Adapter_tri_dobavleni9>();
        String naz_id_prepoda_doljn=" ",naz_id_predmeta_fk=" ",naz_id_studenta=" ",naz_FNG=" ";

        query=db.rawQuery("SELECT distinct J.id_rabotnika,J.id_predmeta,S.id_facultet, S.id_napravlenie, S.id_groupa  " +
                "FROM jurnal J inner Join studenti S on S.id=J.id_studentov ORDER BY J.id_rabotnika,J.id_predmeta", null);
/** Вывожу списокпреподов с их должностью
 * Соединяю таблицы пжурнал и студенты по айди студентов
 * и запоминаю только поля айди раб предмета и ФНГ
 * и делаю чтобы не повторялось
 * журнал      студенты
 * 1 1 3      3 9 8 7
 * 1 1 4      4 9 8 7
 * 1 1 5      5 7 8 9
 *    и на выоход
 *    1 1 9 8 7
 *    1 1 7 8 9
 *    ищу название этих айди и заношу в адаптер всю конкатинацию строги ППФНГ и пять айди
 * */
        while (query.moveToNext()) {
            String fulname=" ";
            //Для Препода
            Integer id,id_prepoda_doljn,id_predmeta_fk,id_studenta,propyski;

            //Для Препода
            Integer id_doljnosti;
            String last_name_id_prepoda,first_name_id_prepoda,middle_name_id_prepoda;
            //Для должности
            String name_id_doljnosti;

            //Для Предмета
            Integer id_fk;
            String name_predmeta;
            //Для Формы контроля
            String name_id_fk;

            //Для  Студента
            Integer id_faculteta,id_napravleni9,id_groupi;
            String last_name_id_studenta,first_name_id_studenta,middle_name_id_studenta;
            //Для ФНГ
            String naz_id_facultet,naz_id_napravlenie,naz_id_groupa;
            Integer j_id_faculteta,j_id_napravleni9,j_id_groupi;



            id_prepoda_doljn= query.getInt(0);
            id_predmeta_fk= query.getInt(1);
            id_faculteta= query.getInt(2);
            id_napravleni9= query.getInt(3);
            id_groupi= query.getInt(4);





            query_F = db.rawQuery("SELECT * FROM facultet WHERE id=?", new String[]{id_faculteta.toString()});
            query_F.moveToFirst();
            naz_id_facultet = query_F.getString(1);

            query_N = db.rawQuery("SELECT * FROM napravlenie WHERE id=?", new String[]{id_napravleni9.toString()});
            query_N.moveToFirst();
            naz_id_napravlenie = query_N.getString(1);

            query_G = db.rawQuery("SELECT * FROM groupa WHERE id=?", new String[]{id_groupi.toString()});
            query_G.moveToFirst();
            naz_id_groupa = query_G.getString(1);

            naz_FNG = naz_id_facultet + " " + naz_id_napravlenie + "  " + naz_id_groupa + "гр.";


            query_Prepod = db.rawQuery("SELECT * FROM rabotnik WHERE id=?", new String[]{id_prepoda_doljn.toString()});
            query_Prepod.moveToFirst();
            last_name_id_prepoda = query_Prepod.getString(1);
            first_name_id_prepoda = query_Prepod.getString(2);
            middle_name_id_prepoda = query_Prepod.getString(3);
            id_doljnosti = query_Prepod.getInt(4);
            query_D = db.rawQuery("SELECT * FROM doljnost WHERE id=?", new String[]{id_doljnosti.toString()});
            query_D.moveToFirst();
            name_id_doljnosti = query_D.getString(1);

            naz_id_prepoda_doljn = last_name_id_prepoda + " " + first_name_id_prepoda.substring(0, 1)
                    + "." + middle_name_id_prepoda.substring(0, 1) + ".   " + name_id_doljnosti;


            query_Predmet = db.rawQuery("SELECT * FROM predmet WHERE id=?", new String[]{id_predmeta_fk.toString()});
            query_Predmet.moveToFirst();
            name_predmeta = query_Predmet.getString(1);
            id_fk = query_Predmet.getInt(2);
            query_Fk = db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?", new String[]{id_fk.toString()});
            query_Fk.moveToFirst();
            name_id_fk = query_Fk.getString(1);

            naz_id_predmeta_fk = name_predmeta + " " + name_id_fk;




            fulname = naz_id_prepoda_doljn + " " + naz_id_predmeta_fk + "  " + naz_FNG;
            jurnal_id_name.add(new Adapter_tri_dobavleni9(id_prepoda_doljn,id_predmeta_fk,id_faculteta,id_napravleni9,id_groupi,fulname));

            query_N.close();
            query_F.close();
            query_G.close();
            query_Predmet.close();
            query_Prepod.close();
            query_D.close();
            query_Fk.close();




        }
        //После заполнения списка названий я передаю его
        Using_adapter_tri_dobavleni9 uatd = new Using_adapter_tri_dobavleni9(Smotret_Jurnal.this,jurnal_id_name);
        sp1.setAdapter(uatd);


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                /** */
                AdapterView<?> parent_onItemSelected=parent;
                View view_onItemSelected=view;
                int pos_onItemSelected=pos;
                long id_onItemSelected=id;



                ListView lv = (ListView) findViewById(R.id.otobrajenie_proS_LV_jurnal);
                ArrayList<Using_adapter_dvapol9_dobavleni9> spisok;
                Adapter_dvapol9_dobavleni9 adapter_dobavlenie;



                spisok = new ArrayList<>();
                Cursor  query,query_S,query_J;
                List<Integer>  list_id_studentiv;
                list_id_studentiv= new ArrayList<Integer>();
                Adapter_tri_dobavleni9 atr;

                Integer Id_prepod,Id_premdet,Id_f,Id_n,Id_g;

                        atr = (Adapter_tri_dobavleni9) parent.getItemAtPosition(pos);
                         SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                         Adapter_odnogo_dobavleni9 aod,aodS,aodPD,aodPFk;

                atr = (Adapter_tri_dobavleni9) sp1.getSelectedItem();
                    Id_prepod=atr.getId(); Id_premdet=atr.getId_1();Id_f=atr.getId_2();Id_n=atr.getId_3();Id_g=atr.getId_4();

                /**Смоттря какой элемент в спинере выбрали то я передаю данные ФНГ
                 * И смотрю всех сдентов по этому ФНГ
                 * далее записываю в список все эти айди по этим ФНГ*/

                    query=db.rawQuery("SELECT * FROM studenti WHERE id_facultet=? and id_napravlenie=? and id_groupa=? "
                        ,new String[] {Id_f.toString(),Id_n.toString(),Id_g.toString()});
                while (query.moveToNext()) {
                    Integer id_st;
                    id_st=query.getInt(0);
                    list_id_studentiv.add(id_st);
                }

                /** Беру теже айди только работника и предмета и смотрю записи в журнале
                 * где потом я нахожу нужгный айди студентов сравниваю со всеми айди студентов по ФНГ
                 * и отсеиваю нужный мне студентов и беру их пропуски и показываю*/
                query=db.rawQuery("SELECT * FROM jurnal WHERE id_rabotnika=? and id_predmeta=?"
                        ,new String[] {Id_prepod.toString(),Id_premdet.toString()});
Integer zapis_jurnala;
while (query.moveToNext()){
    Integer id_stud,kolvo_propysk;
    String stlastname,stfirstname,stmiddlename,stname;
    zapis_jurnala=query.getInt(0);
    id_stud=query.getInt(3);
    kolvo_propysk=query.getInt(4);
    for(int k=0;k<list_id_studentiv.size();k++){
    if(list_id_studentiv.get(k).intValue()==id_stud.intValue()){
        query_S=db.rawQuery("SELECT * FROM studenti WHERE id=?",new String[] {id_stud.toString()});
        query_S.moveToFirst();
        stlastname=query_S.getString(1);
        stfirstname=query_S.getString(2);
        stmiddlename=query_S.getString(3);
      stname=stlastname + " " + stfirstname + " " + stmiddlename;
        spisok.add(new Using_adapter_dvapol9_dobavleni9(zapis_jurnala,stname,kolvo_propysk));
        query_S.close();
    }
    }

}

query.close();

                adapter_dobavlenie = new Adapter_dvapol9_dobavleni9(Smotret_Jurnal.this, spisok);
                lv.setAdapter(adapter_dobavlenie);
                //Чтобы для всех записей можно было выбрать редактирование
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                        //вызываем метод где будем вносить изменение если нам понадобиться
                      Redactirovanie(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.propysklist_id)).getText().toString()),parent_onItemSelected ,view_onItemSelected ,pos_onItemSelected ,id_onItemSelected);


                    }
                });
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });

        query.close();
        db.close();


    }

    public void onItemSelectedRefresh(AdapterView<?> parent, View view, int pos, long id) {
        /** */
        AdapterView<?> parent_onItemSelected=parent;
        View view_onItemSelected=view;
        int pos_onItemSelected=pos;
        long id_onItemSelected=id;
        final Spinner sp1 = (Spinner) findViewById(R.id.spinner_vibor_vsego);


        ListView lv = (ListView) findViewById(R.id.otobrajenie_proS_LV_jurnal);
        ArrayList<Using_adapter_dvapol9_dobavleni9> spisok;
        Adapter_dvapol9_dobavleni9 adapter_dobavlenie;



        spisok = new ArrayList<>();
        Cursor  query,query_S,query_J;
        List<Integer>  list_id_studentiv;
        list_id_studentiv= new ArrayList<Integer>();
        Adapter_tri_dobavleni9 atr;

        Integer Id_prepod,Id_premdet,Id_f,Id_n,Id_g;

        atr = (Adapter_tri_dobavleni9) parent.getItemAtPosition(pos);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Adapter_odnogo_dobavleni9 aod,aodS,aodPD,aodPFk;

        atr = (Adapter_tri_dobavleni9) sp1.getSelectedItem();
        Id_prepod=atr.getId(); Id_premdet=atr.getId_1();Id_f=atr.getId_2();Id_n=atr.getId_3();Id_g=atr.getId_4();

        /**Смоттря какой элемент в спинере выбрали то я передаю данные ФНГ
         * И смотрю всех сдентов по этому ФНГ
         * далее записываю в список все эти айди по этим ФНГ*/

        query=db.rawQuery("SELECT * FROM studenti WHERE id_facultet=? and id_napravlenie=? and id_groupa=?"
                ,new String[] {Id_f.toString(),Id_n.toString(),Id_g.toString()});
        while (query.moveToNext()) {
            Integer id_st;
            id_st=query.getInt(0);
            list_id_studentiv.add(id_st);
        }

        /** Беру теже айди только работника и предмета и смотрю записи в журнале
         * где потом я нахожу нужгный айди студентов сравниваю со всеми айди студентов по ФНГ
         * и отсеиваю нужный мне студентов и беру их пропуски и показываю*/
        query=db.rawQuery("SELECT * FROM jurnal WHERE id_rabotnika=? and id_predmeta=?"
                ,new String[] {Id_prepod.toString(),Id_premdet.toString()});
        Integer zapis_jurnala;
        while (query.moveToNext()){
            Integer id_stud,kolvo_propysk;
            String stlastname,stfirstname,stmiddlename,stname;
            zapis_jurnala=query.getInt(0);
            id_stud=query.getInt(3);
            kolvo_propysk=query.getInt(4);
            for(int k=0;k<list_id_studentiv.size();k++){
                if(list_id_studentiv.get(k).intValue()==id_stud.intValue()){
                    query_S=db.rawQuery("SELECT * FROM studenti WHERE id=?",new String[] {id_stud.toString()});
                    query_S.moveToFirst();
                    stlastname=query_S.getString(1);
                    stfirstname=query_S.getString(2);
                    stmiddlename=query_S.getString(3);
                    stname=stlastname + " " + stfirstname + " " + stmiddlename;
                    spisok.add(new Using_adapter_dvapol9_dobavleni9(zapis_jurnala,stname,kolvo_propysk));
                    query_S.close();
                }
            }
        }
        query.close();

        adapter_dobavlenie = new Adapter_dvapol9_dobavleni9(Smotret_Jurnal.this, spisok);
        lv.setAdapter(adapter_dobavlenie);
        //Чтобы для всех записей можно было выбрать редактирование
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                //вызываем метод где будем вносить изменение если нам понадобиться
                Redactirovanie(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.propysklist_id)).getText().toString()),parent_onItemSelected ,view_onItemSelected ,pos_onItemSelected ,id_onItemSelected);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dobav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.menu_otmechat:
                intent = new Intent(this,Smotret_Jurnal.class);
                finish();
                startActivity(intent);
                break;
            case R.id.menu_fngs:
                intent = new Intent(this,Dobavlenie.class);
                finish();
                startActivity(intent);
                break;
            case R.id.menu_prepod:
                intent = new Intent(this,Dobavlenie_Prepodavatel9.class);
                finish();
                startActivity(intent);
                break;
            case R.id.menu_predmet:
                intent = new Intent(this,Dobavlenie_Predmeta.class);
                finish();
                startActivity(intent);
                break;
            case R.id.menu_jurnal:
                intent = new Intent(this,Dobavlenie_Jurnal.class);
                finish();
                startActivity(intent);
                break;
            case R.id.menu_main:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /** сделать чтобы при добавлении студентов я смотрел если такие вторичные ключи совпадали то не записывает,
     * сделать удаление одной записи
     * множеств записей например препод впредмет и фнг
     * и чтобы все они ужалились
     */
    //Редактирование
    public void Redactirovanie(Integer id,
                               AdapterView<?> parent_onItemSelected, View view_onItemSelected , int pos_onItemSelected, long id_onItemSelected){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Smotret_Jurnal.this);
        subjectDialog.setTitle("Отметить");
        subjectDialog.setCancelable(false);
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.propyski_plus_minus, null);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        Cursor  query;
        String strpropyski;
        Integer kolvo_propyskov;
/** подставляю количество пропусков в готовое */
        query=db.rawQuery("SELECT * FROM jurnal WHERE id=?",new String[] {String.valueOf(id)});
        query.moveToFirst();
        kolvo_propyskov=query.getInt(4);
        strpropyski=kolvo_propyskov.toString();
        final EditText et1 = (EditText) vv.findViewById(R.id.kolvo_propyskov);
        final Button b1=(Button) vv.findViewById(R.id.b_minus);
        final Button b2=(Button) vv.findViewById(R.id.b_otsytsvyet);
        et1.setText(strpropyski);

        subjectDialog.setView(vv);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer kolvo_propyskov_minus =Integer.valueOf(et1.getText().toString());
                kolvo_propyskov_minus=kolvo_propyskov_minus-1;
                String strpropyski_minus=kolvo_propyskov_minus.toString();
                et1.setText(strpropyski_minus);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer kolvo_propyskov_plus =Integer.valueOf(et1.getText().toString());
                kolvo_propyskov_plus=kolvo_propyskov_plus+1;
                String strpropyski_plus=kolvo_propyskov_plus.toString();
                et1.setText(strpropyski_plus);
            }
        });

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            /** Внесение Изменения */
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();

                long id_zapisi ;
                if (id > 0) {
                            values.put("kolvo_propyskov", et1.getText().toString());
                            id_zapisi = db.update("jurnal", values, "id = ?", new String[]{String.valueOf(id)});
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Отметили", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();

            onItemSelectedRefresh(parent_onItemSelected,view_onItemSelected, pos_onItemSelected, id_onItemSelected);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        subjectDialog.show();
        db.close();
    }
    //Конец.Редактирование

}


