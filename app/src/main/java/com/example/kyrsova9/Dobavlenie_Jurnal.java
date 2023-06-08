package com.example.kyrsova9;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class Dobavlenie_Jurnal extends AppCompatActivity {
    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;
    ArrayList<Using_adapter_dobavleni9_jurnala> spisok_jurnala  ;
    Adapter_dobavleni9_jurnala adapter_dobavleni9_jurnala;
    List<Integer> list_id_studentiv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okno_dobavleni9_jurnala);
        OnClick_Pokazat_jurnal();
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
 *
    /**Добавление Журнала*/
    public void OnClick_perehod_k_dobav_jurnala(View view){
        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Jurnal.this);
        subjectDialog.setTitle("Заполните поля журнала");
        subjectDialog.setCancelable(false);
/** Крч суть состоит в том что есть лаяут с спинерами где выборка по фНГ преподу и предмету
 * К преподу превязвыется и должность а к предмету и форма контроля
 * ФНГ определяет студентов т.е По заданным вторичным ключам я определяю студентов и добавляю всех студентов с этими вторичными кдючами
 * и сразу заношу что количество пропусков =0 */
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_jurnala, null);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.j_spinner_prepod);
        final Spinner sp2 = (Spinner) vv.findViewById(R.id.j_spinner_predmet);
        final Spinner sp3 = (Spinner) vv.findViewById(R.id.j_spinner_facultet);
        final Spinner sp4 = (Spinner) vv.findViewById(R.id.j_spinner_napravlenie);
        final Spinner sp5 = (Spinner) vv.findViewById(R.id.j_spinner_groupa);


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_D,query_Fk,query_F,query_N,query_G;
        //Флаг для того чтобы понятьт заходит ли в вайл ( т.е проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
        Boolean flag_Facultet=false,flag_Napravlenie=false,flag_Groupa=false,flag_Prepod=false,flag_Predmet=false;
        Integer flag_kolvo_studentov=0;


/** Форма Препода*/
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> prepod_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM rabotnik", null);
/** Захожу в тьаблицу работник если пустая то ставлю флаг =0 где в последствии говорю что занести препода, чтобы заполнить журнал
 * тцт цикл из 2 вайлов где я ещё привязвыю вторичный ключ должность тк как препод может быть и лектором например и практиком
 * ну и чтобы сразу показывалась должность для удобства*/
        while (query.moveToNext()) {
            query_D=db.rawQuery("SELECT * FROM doljnost", null);
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id,id_doljnosti;
            String prlast_name,prfirst_name,prmiddle_name,nazvanie_doljnosti=" ",name;
            id= query.getInt(0);
            prlast_name = query.getString(1);
            prfirst_name= query.getString(2);
            prmiddle_name= query.getString(3);
            id_doljnosti=query.getInt(4);
            while (query_D.moveToNext()){
                Integer id_doljnosti_while;
                String name_id_doljnosti;
                id_doljnosti_while=query_D.getInt(0);
                name_id_doljnosti=query_D.getString(1);
                if(id_doljnosti.intValue()==id_doljnosti_while.intValue())
                    nazvanie_doljnosti=name_id_doljnosti;
            }
            name=prlast_name+" "+ prfirst_name + " " + prmiddle_name +" " + nazvanie_doljnosti;
            prepod_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Prepod=true;
            query_D.close();
        }
        //После заполнения списка названий я передаю его
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,prepod_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Препода




/** Форма Предмета смысл такой же как и у пррепода*/
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> predmet_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM predmet", null);

        while (query.moveToNext()) {
            query_Fk=db.rawQuery("SELECT * FROM forma_kontrol9", null);
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id,id_predmeta;
            String name_predmeta,nazvanie_fk=" ",name;
            id= query.getInt(0);
            name_predmeta= query.getString(1);
            id_predmeta =query.getInt(2);
            while (query_Fk.moveToNext()){
                Integer id_fk_while;
                String name_id_fk_while;
                id_fk_while=query_Fk.getInt(0);
                name_id_fk_while=query_Fk.getString(1);
                if(id_fk_while==id_predmeta)
                    nazvanie_fk=name_id_fk_while;
            }

            name=name_predmeta+" "+nazvanie_fk;
            predmet_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Predmet=true;
            query_Fk.close();
        }
        //После заполнения списка названий я передаю его
       uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,predmet_name);
        sp2.setAdapter(uaod);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Предмета

/** А тут уже другое смысл заключается что я передаю уже сущ ФНГ
 * если они пустые то просят заполнить поля
 * Потом я просто в спинер показываю сущ ФНГ
 * и выборкой вторичных ключей мы будем определять какие Студенты занесуться */

/**Для факультета */
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> facul_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM facultet", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            facul_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Facultet=true;
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,facul_name);
        sp3.setAdapter(uaod);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец. Факультета



/**Для Направления */
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> naprav_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM napravlenie", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            naprav_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Napravlenie=true;;
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,naprav_name);
        sp4.setAdapter(uaod);
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец.Направления


/**Для Группы */
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> group_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM groupa", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            group_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Groupa=true;;
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,group_name);
        sp5.setAdapter(uaod);
        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Группы


query.close();
db.close();
        subjectDialog.setView(vv);
        final Boolean[] flag_Student = {false};
        Boolean finalFlag_Predmet = flag_Predmet;
        Boolean finalFlag_Prepod = flag_Prepod;
        Boolean finalFlag_Facultet = flag_Facultet;
        Boolean finalFlag_Napravlenie = flag_Napravlenie;
        Boolean finalFlag_Groupa = flag_Groupa;

        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query,query_S,query_Predmet,query_Prepod,query_J,query_PPS;

                /**Есть ли студенты по этим ФНГ
                 * Тут уже по выбранным Фнг мы будем смотреть есть ли такие студенты по вторичным ключам, если таких нет то просто
                 * говорим что тааких нет ( пуста)
                 * Если такие есть то мы берём значения из того что выбрали в спинера ФНГ
                 * в вайле смотрю  если есть такие студенты то создаю лист куда и добавляю айди всех этих студентов */
                ContentValues valuesPPFNG = new ContentValues();
                Integer Id_f,Id_n,Id_g,kolvo_zapisi=0,m=0;
                Adapter_odnogo_dobavleni9 aod,aodS,aodPD,aodPFk;
                if (finalFlag_Facultet && finalFlag_Napravlenie && finalFlag_Groupa){
                    //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                    List<Adapter_odnogo_dobavleni9> student_id = new ArrayList<Adapter_odnogo_dobavleni9>();
                    //   Беру из бд выборку которая пришла (Select *)
                    query=db.rawQuery("SELECT * FROM studenti", null);
                    Adapter_odnogo_dobavleni9 aodF,aodN,aodG;
                    list_id_studentiv= new ArrayList<Integer>();
                    aodF = (Adapter_odnogo_dobavleni9) sp3.getSelectedItem();
                    aodN = (Adapter_odnogo_dobavleni9) sp4.getSelectedItem();
                    aodG = (Adapter_odnogo_dobavleni9) sp5.getSelectedItem();
                    Id_f=aodF.getId(); Id_n=aodN.getId();Id_g=aodG.getId();
                    /** Тут надо сделать если совпадает 3 айди то добалять этой id*/
                    while (query.moveToNext()) {
                        //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                        Integer id,id_facultet ,id_napravlenie ,id_groupa;
                        id= query.getInt(0);
                        id_facultet=query.getInt(4);
                        id_napravlenie=query.getInt(5);
                        id_groupa=query.getInt(6);


                        if(id_facultet.intValue()== Id_f.intValue() && id_napravlenie.intValue()==Id_n.intValue() && id_groupa.intValue()==Id_g.intValue()){
                            list_id_studentiv.add(id);
                            flag_Student[0] =true;;
                        }
                    }
                    query.close();
                }
                //Конец Cтудента
                //Значение куда сохраняем значение
                String pismo=" ";
                ContentValues values_values = new ContentValues();
                /**
                 * Ну и вот если мы дошли и есть преподы предмет и студенты по выбранным ФНГ
                 * то заношу в таблицу журнал столько раз сколько студентов ппо ввыбранным ФНГ
                 * НО сначала проверя пустой ли журнал если пустой то могу добавить любого стдуента и прч пареметры
                 * если есть записи то смотрю в журнале все записи по таким ключам если совпадают вторичные ключи то занесение одинаковой записи невозможно
                 * есл не совпадает то заношу
                 * */
                //Проверка пустые ли таблицы ФНГ, если пустые то не даст добавить Журнал)
                if (flag_Student[0] && finalFlag_Predmet && finalFlag_Prepod) {

               Boolean flg_while=true;
               Integer flg_zah;
                    long id_zapisi;
                    Integer spiner_id_prepoda,spiner_id_predmeta;
                    query_J=db.rawQuery("SELECT * FROM jurnal", null);
                    while(query_J.moveToNext())
                    {flg_while=false;}
                    query_J.close();
                  for (int k=0;k<list_id_studentiv.size();k++ ) {


                      aodPD = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                      spiner_id_prepoda=aodPD.getId();
                      aodPFk = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                      spiner_id_predmeta=aodPFk.getId();

                      if(flg_while){
                          kolvo_zapisi++;
                              aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                              values_values.put("id_rabotnika", aod.getId());
                              aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                              values_values.put("id_predmeta", aod.getId());
                              // values_values.put("id_studentov", list_id_studentiv.get(k));
                              values_values.put("id_studentov", list_id_studentiv.get(k));
                              values_values.put("kolvo_propyskov", 0);
//Включаем добавление вторичных ключей
                              db.execSQL("PRAGMA foreign_keys=ON");
                              id_zapisi = db.insert("jurnal", null, values_values);
                              if (id_zapisi != -1)
                                  Toast.makeText(getApplicationContext(), "В журнал добавлены записи", Toast.LENGTH_LONG).show();
                              else
                                  Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                      flg_while=false;}

                      else{
query_PPS=db.rawQuery("SELECT * FROM jurnal WHERE id_rabotnika =? and id_predmeta =? and id_studentov=?",
        new String[] {spiner_id_prepoda.toString(),spiner_id_predmeta.toString(),list_id_studentiv.get(k).toString()});
try {
    query_PPS.moveToFirst();
    flg_zah=query_PPS.getInt(0);
}
catch (Exception e){

                                  kolvo_zapisi++;
                                  aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                                  values_values.put("id_rabotnika", aod.getId());
                                  aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                                  values_values.put("id_predmeta", aod.getId());
                                  // values_values.put("id_studentov", list_id_studentiv.get(k));
                                  values_values.put("id_studentov", list_id_studentiv.get(k));
                                  values_values.put("kolvo_propyskov", 0);
//Включаем добавление вторичных ключей
                                  db.execSQL("PRAGMA foreign_keys=ON");
                                  id_zapisi = db.insert("jurnal", null, values_values);
                                  if (id_zapisi != -1)
                                      Toast.makeText(getApplicationContext(), "В журнал добавлены записи", Toast.LENGTH_LONG).show();
                                  else
                                      Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();

                              }
                              //////////////////////////////

                              //   query_Predmet.close(); query_Prepod.close(); query_S.close();

                          query_PPS.close();    }


                  }


                } else if (!finalFlag_Predmet )
                    Toast.makeText(getApplicationContext(), "Занесите информацию в Добавление предмета", Toast.LENGTH_LONG).show();
                else if(!finalFlag_Prepod )   Toast.makeText(getApplicationContext(), "Занесите информацию в Добавление Преподавателя", Toast.LENGTH_LONG).show();
                else if (!flag_Student[0] )
                    Toast.makeText(getApplicationContext(), "Нету Студентов по ФНГ", Toast.LENGTH_LONG).show();
          //  }


                if (kolvo_zapisi.intValue()!=0) pismo=" Добавлено: " + kolvo_zapisi.toString() + "  записи";
                else if(flag_Student[0]) pismo=" Такие записи в Журнале уже существуют";
            Toast.makeText(getApplicationContext(),pismo,Toast.LENGTH_SHORT).show();
                db.close();
                OnClick_Pokazat_jurnal();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    /** Конец.Добавления Журнала*/


    //Редактирование Журнала
    /** Легко запутаться оч много кода**/
    public void Redactirovanie_jurnala(Integer id){

        /**Вызывает окно для внесения изменения информации**/
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Jurnal.this);
        subjectDialog.setTitle("Изменение");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout)
                getLayoutInflater().inflate(R.layout.izmenenie_spiner, null);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.st_spiner_F);
        final Spinner sp2 = (Spinner) vv.findViewById(R.id.st_spiner_N);
        final Spinner sp3 = (Spinner) vv.findViewById(R.id.st_spiner_G);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query,query_D,query_Fk,query_F,query_N,query_G;
        //Флаг для того чтобы понятьт заходит ли в вайл ( т.е проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
        Boolean flag_Facultet=false,flag_Napravlenie=false,flag_Groupa=false,flag_Prepod=false,flag_Predmet=false;
        Integer flag_kolvo_studentov=0;

/** Форма Препода*/
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> prepod_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM rabotnik", null);
/** Вывожу списокпреподов с их должностью */
        while (query.moveToNext()) {
            //Для Препода
            Integer id_doljnosti,id_prepoda_doljn;
            String last_name_id_prepoda,first_name_id_prepoda,middle_name_id_prepoda, naz_id_prepoda_doljn;
            //Для должности
            String name_id_doljnosti;

            id_prepoda_doljn= query.getInt(0);
            last_name_id_prepoda= query.getString(1);
            first_name_id_prepoda= query.getString(2);
            middle_name_id_prepoda= query.getString(3);
            id_doljnosti=query.getInt(4);

            query_D=db.rawQuery("SELECT * FROM doljnost WHERE id=?",new String[] {id_doljnosti.toString()});
            query_D.moveToFirst();
            name_id_doljnosti=query_D.getString(1);
            naz_id_prepoda_doljn=last_name_id_prepoda + " " + first_name_id_prepoda
                    + " " + middle_name_id_prepoda + " " + name_id_doljnosti;
            prepod_name.add(new Adapter_odnogo_dobavleni9(id_prepoda_doljn,naz_id_prepoda_doljn));
            query_D.close();
        }
        //После заполнения списка названий я передаю его
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,prepod_name);
        sp1.setAdapter(uaod);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Препода




/** Форма Предмета смысл такой же как и у пррепода*/
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> predmet_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM predmet", null);

        while (query.moveToNext()) {

            Integer id_fk,id_predmeta_fk;
            String name_predmeta,name_id_fk,naz_id_predmeta_fk;

            id_predmeta_fk= query.getInt(0);
            name_predmeta= query.getString(1);
            id_fk=query.getInt(2);

            query_Fk=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {id_fk.toString()});
            query_Fk.moveToFirst();
            name_id_fk=query_Fk.getString(1);
            /** Предмет */
            naz_id_predmeta_fk=name_predmeta + " " + name_id_fk;

            predmet_name.add(new Adapter_odnogo_dobavleni9(id_predmeta_fk,naz_id_predmeta_fk));
            query_Fk.close();
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,predmet_name);
        sp2.setAdapter(uaod);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Предмета


//Для Группы
        /** Отображение Студента для изменение журнала */
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> fng_student_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM studenti", null);

        while (query.moveToNext()) {

            Integer id_fgn_studenta,id_faculteta,id_napravleni9,id_groupi;
            String name_id_studenta,naz_id_fgn_studenta,last_name_id_studenta,first_name_id_studenta,middle_name_id_studenta;
            String  naz_FNG,naz_id_facultet ,naz_id_napravlenie ,naz_id_groupa;

            id_fgn_studenta= query.getInt(0);
            last_name_id_studenta=query.getString(1);
            first_name_id_studenta=query.getString(2);
            middle_name_id_studenta=query.getString(3);
            id_faculteta=query.getInt(4);
            id_napravleni9=query.getInt(5);
            id_groupi=query.getInt(6);

            name_id_studenta=last_name_id_studenta + " " + first_name_id_studenta
                    + " " + middle_name_id_studenta + " " ;

            query_F=db.rawQuery("SELECT * FROM facultet WHERE id=?",new String[] {id_faculteta.toString()});
            query_F.moveToFirst();
            naz_id_facultet=query_F.getString(1);

            query_N=db.rawQuery("SELECT * FROM napravlenie WHERE id=?",new String[] {id_napravleni9.toString()});
            query_N.moveToFirst();
            naz_id_napravlenie=query_N.getString(1);

            query_G=db.rawQuery("SELECT * FROM groupa WHERE id=?",new String[] {id_groupi.toString()});
            query_G.moveToFirst();
            naz_id_groupa=query_G.getString(1);

            naz_FNG=naz_id_facultet + " " + naz_id_napravlenie + "  " + naz_id_groupa + "гр.";

            naz_id_fgn_studenta=name_id_studenta + "  " + naz_FNG;



            fng_student_name.add(new Adapter_odnogo_dobavleni9(id_fgn_studenta,naz_id_fgn_studenta));
            query_F.close();
            query_G.close();
            query_N.close();
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Jurnal.this,fng_student_name);
        sp3.setAdapter(uaod);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Вызывается, когда был выбран новый элемент (в Spinner)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) parent.getItemAtPosition(pos);

                // Toast.makeText(getApplicationContext(), aod.getName()+" is "+aod.getId(), Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView parent) {
                // Ничего не делать.
            }
        });
        //Конец Студента ФНГ
query.close();
                db.close();
                subjectDialog.setView(vv);
//Он клик на позитив батон
                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Грубо говоря открываем бд ( подключаемся)
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Cursor query_PPS;
                        long id_zapisi;
                        Integer spiner_id_prepoda,spiner_id_predmeta,spiner_id_studenta,flg_zah;
                        //Значение куда сохраняем значение
                        ContentValues values_values = new ContentValues();
                        if(id>0) {

                            Adapter_odnogo_dobavleni9 aodPD,aodPFk,aodS,aod;
                            aodPD = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                            spiner_id_prepoda=aodPD.getId();
                            aodPFk = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                            spiner_id_predmeta=aodPFk.getId();
                            aodS = (Adapter_odnogo_dobavleni9) sp3.getSelectedItem();
                            spiner_id_studenta=aodS.getId();

                            query_PPS=db.rawQuery("SELECT * FROM jurnal WHERE id_rabotnika =? and id_predmeta =? and id_studentov=?",
                                    new String[] {spiner_id_prepoda.toString(),spiner_id_predmeta.toString(),spiner_id_studenta.toString()});
                            try {
                                query_PPS.moveToFirst();
                                flg_zah=query_PPS.getInt(0);
                                Toast.makeText(getApplicationContext(), "Такая запись уже существует", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e){
                                aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                                values_values.put("id_rabotnika", aod.getId());
                                aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                                values_values.put("id_predmeta", aod.getId());
                                aod=(Adapter_odnogo_dobavleni9) sp3.getSelectedItem();
                                values_values.put("id_studentov",aod.getId());
                                values_values.put("kolvo_propyskov", 0);
//Включаем добавление вторичных ключей
                                db.execSQL("PRAGMA foreign_keys=ON");
                                id_zapisi = db.update("jurnal", values_values, "id = ?", new String[]{String.valueOf(id)});
                                if (id_zapisi != -1)
                                    Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Произошла оиииииииииииииибка", Toast.LENGTH_LONG).show();

                            }

                            query_PPS.close();

                        }
                        else Toast.makeText(getApplicationContext(),"Некоретный id", Toast.LENGTH_LONG).show();

                        db.close();
                        OnClick_Pokazat_jurnal();

                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            /** Удаление одной записи Журнала **/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id_zapisi=0 ;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try{
                        id_zapisi=db.delete("jurnal", "id = ?", new String[]{String.valueOf(id)});
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    }
                    //Если вернулось 1 значит удалили
                    if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                    else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                OnClick_Pokazat_jurnal();

            }
        });
        subjectDialog.show();
    }
    //Конец.Редактирование Журнала


    /** Показать журнал */
    public void OnClick_Pokazat_jurnal(){

        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_Prepod,query_Predmet,query_D,query_Fk,query_S,query_F,query_N,query_G;

                query=db.rawQuery("SELECT * FROM jurnal ORDER BY id_rabotnika ,id_predmeta", null);


            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_jurnal);
            spisok_jurnala = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {

               //Получаем id Журнала
               Integer id,id_prepoda_doljn,id_predmeta_fk,id_studenta,propyski;
               String naz_id_prepoda_doljn,naz_id_predmeta_fk,naz_id_studenta,naz_FNG;

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


                id= query.getInt(0);
                id_prepoda_doljn= query.getInt(1);
                id_predmeta_fk= query.getInt(2);
                id_studenta= query.getInt(3);
                propyski= query.getInt(4);
//
                /** буду по айди из журнала искать препода с должностью Прпеподаватель */

                    query_Prepod=db.rawQuery("SELECT * FROM rabotnik WHERE id=?",new String[] {id_prepoda_doljn.toString()});
                     query_Prepod.moveToFirst();
                     last_name_id_prepoda=query_Prepod.getString(1);
                     first_name_id_prepoda=query_Prepod.getString(2);
                     middle_name_id_prepoda=query_Prepod.getString(3);
                    id_doljnosti=query_Prepod.getInt(4);
                    query_D=db.rawQuery("SELECT * FROM doljnost WHERE id=?",new String[] {id_doljnosti.toString()});
                    query_D.moveToFirst();
                    name_id_doljnosti=query_D.getString(1);
                /** Препод Фамилия и иницалы беру + Должность */
                try {


                    naz_id_prepoda_doljn=last_name_id_prepoda + " " + first_name_id_prepoda.substring(0,1)
                            + "." + middle_name_id_prepoda.substring(0,1) + ".   " + name_id_doljnosti;}
                catch(Exception e ){
                    naz_id_prepoda_doljn=last_name_id_prepoda + " " + first_name_id_prepoda
                            + " " + middle_name_id_prepoda+ " " + name_id_doljnosti;
                }



                /** Предмет */
                query_Predmet=db.rawQuery("SELECT * FROM predmet WHERE id=?",new String[] {id_predmeta_fk.toString()});
                query_Predmet.moveToFirst();
                name_predmeta=query_Predmet.getString(1);
                id_fk=query_Predmet.getInt(2);
                query_Fk=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {id_fk.toString()});
                query_Fk.moveToFirst();
                name_id_fk=query_Fk.getString(1);
                /** Предмет */
                naz_id_predmeta_fk=name_predmeta + " " + name_id_fk;



                /** Студент и ФНГ*/
                query_S=db.rawQuery("SELECT * FROM studenti WHERE id=?",new String[] {id_studenta.toString()});
                query_S.moveToFirst();
                last_name_id_studenta=query_S.getString(1);
                first_name_id_studenta=query_S.getString(2);
                middle_name_id_studenta=query_S.getString(3);
                id_faculteta=query_S.getInt(4);
                id_napravleni9=query_S.getInt(5);
                id_groupi=query_S.getInt(6);
try {


                naz_id_studenta=last_name_id_studenta + " " + first_name_id_studenta.substring(0,1)
                        + "." + middle_name_id_studenta.substring(0,1) + ". " ;}
catch(Exception e ){
    naz_id_studenta=last_name_id_studenta + " " + first_name_id_studenta
            + " " + middle_name_id_studenta + " " ;
}

                /** Студент */

                query_F=db.rawQuery("SELECT * FROM facultet WHERE id=?",new String[] {id_faculteta.toString()});
                query_F.moveToFirst();
                naz_id_facultet=query_F.getString(1);

                query_N=db.rawQuery("SELECT * FROM napravlenie WHERE id=?",new String[] {id_napravleni9.toString()});
                query_N.moveToFirst();
                naz_id_napravlenie=query_N.getString(1);

                query_G=db.rawQuery("SELECT * FROM groupa WHERE id=?",new String[] {id_groupi.toString()});
                query_G.moveToFirst();
                naz_id_groupa=query_G.getString(1);

                naz_FNG=naz_id_facultet + " " + naz_id_napravlenie + "  " + naz_id_groupa + "гр.";

                /** ФНГ*/

                spisok_jurnala.add(new Using_adapter_dobavleni9_jurnala(id,naz_id_prepoda_doljn,naz_id_predmeta_fk,naz_FNG,naz_id_studenta));

                query_F.close();
                query_N.close();
                query_G.close();
                query_D.close();
                query_Fk.close();
                query_Predmet.close();
                query_Prepod.close();
                query_S.close();
            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_jurnala= new Adapter_dobavleni9_jurnala(Dobavlenie_Jurnal.this, spisok_jurnala);
            lv.setAdapter(adapter_dobavleni9_jurnala);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_jurnala(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.jlist_id)).getText().toString()));

                }
            });



        query.close();
        db.close();
    }
    //Конец.Показания

}



