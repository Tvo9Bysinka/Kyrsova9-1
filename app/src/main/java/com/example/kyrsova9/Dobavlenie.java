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
import java.util.Queue;

public class Dobavlenie extends AppCompatActivity {
    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;
    ArrayList<Using_adapter_dobavleni9_studenta> spisok_studenta;
    Adapter_dobavleni9_studenta adapter_dobavleni9_studenta;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dobavlenie);
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



// Начало.Добавление факультета
    public void OnClick_perehod_k_dobav_faculteta(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите название факультета");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);

        subjectDialog.setView(vv);
//Он клик на позитив батон
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                //Значение куда сохраняем значение
                ContentValues values = new ContentValues();
                //В значение сохраняем имя столбца с названием которые ввели в edit
                values.put("name_faculteta", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi= db.insert("facultet", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Факультет успешно добавлен", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_PokazatRefresh(1);
            }

        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    // Конец.Добавление факультета

    // Начало.Добавление Напарвления
    public void OnClick_perehod_k_dobav_napravleni9(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите название направления");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);

        subjectDialog.setView(vv);
//Он клик на позитив батон
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                //Значение куда сохраняем значение
                ContentValues values = new ContentValues();
                //В значение сохраняем имя столбца с названием которые ввели в edit
                values.put("name_napravlenie", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi  = db.insert("napravlenie", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Направление успешно добавлено", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_PokazatRefresh(2);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    // Конец.Добавление Направления

    // Начало.Добавление Группы
    public void OnClick_perehod_k_dobav_groupi(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите название группы");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);

        subjectDialog.setView(vv);
//Он клик на позитив батон
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                //Значение куда сохраняем значение
                ContentValues values = new ContentValues();
                //В значение сохраняем имя столбца с названием которые ввели в edit
                values.put("name_group", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi = db.insert("groupa", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi !=-1) Toast.makeText(getApplicationContext(),"Группа успешно добавлена", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_PokazatRefresh(3);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    // Конец.Добавление Группы

    /**Добавление Студента */
    public void OnClick_perehod_k_dobav_studentov(View view){
        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Введите ФИО студента");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_studenta, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_student_lastname);
        final EditText et2 = (EditText) vv.findViewById(R.id.ed_dobavlenie_student_name);
        final EditText et3 = (EditText) vv.findViewById(R.id.ed_dobavlenie_student_middlename);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_F);
        final Spinner sp2 = (Spinner) vv.findViewById(R.id.spinner_N);
        final Spinner sp3 = (Spinner) vv.findViewById(R.id.spinner_G);


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        //Флаг для того чтобы понятьт заходит ли в вайл ( т.е проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
int flag_F=0,flag_N=0,flag_G=0;


//Для факультета
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
         List<Adapter_odnogo_dobavleni9> facul_name = new ArrayList<Adapter_odnogo_dobavleni9>();
     //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM facultet", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            facul_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_F=1;
        }
        //После заполнения списка названий я передаю его
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,facul_name);
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
        //Конец. Факультета



//Для Направления
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> naprav_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM napravlenie", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            naprav_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_N=1;
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,naprav_name);
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
        //Конец.Направления


//Для Группы
        //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> group_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM groupa", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            group_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_G=1;
        }
        //После заполнения списка названий я передаю его
        uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,group_name);
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
        //Конец Группы


        db.close();
        subjectDialog.setView(vv);
//переопределение флага чтобы зайти в диалоговое окно
        int finalFlag_F = flag_F;
        int finalFlag_N = flag_N;
        int finalFlag_G = flag_G;
        //Он клик на позитив батон
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Грубо говоря открываем бд ( подключаемся)
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;
                //Значение куда сохраняем значение
                ContentValues values = new ContentValues();
                //В значение сохраняем имя столбца с названием которые ввели в edit
                values.put("last_name", et1.getText().toString());
                values.put("first_name", et2.getText().toString());
                values.put("middle_name", et3.getText().toString());

//Должно вернуть выделеный объект в спинере ( т.е id name)

                Adapter_odnogo_dobavleni9 aod;
                //Проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
                if (finalFlag_F ==1 && finalFlag_N ==1 && finalFlag_G ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                values.put("id_facultet", aod.getId());
                aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                values.put("id_napravlenie", aod.getId());
                aod = (Adapter_odnogo_dobavleni9) sp3.getSelectedItem();
                values.put("id_groupa", aod.getId());
//Включаем добавление вторичных ключей
                db.execSQL("PRAGMA foreign_keys=ON");
                long id_zapisi = db.insert("studenti", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if (id_zapisi != -1)
                    Toast.makeText(getApplicationContext(), "Студент успешно добавлен", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_PokazatRefresh(4);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
/** Конец. Доюъбавления Студента*/
    //Редактирование
    public void Redactirovanie(Integer id,Integer position_fngs){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String stroka;
        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);
        Cursor query;




        switch (position_fngs){
            case 1:
                query=db.rawQuery("SELECT * FROM facultet WHERE id=?",new String[] {String.valueOf(id)});
                query.moveToFirst();
                stroka=query.getString(1);
                query.close();
                break;
            case 2:
                query=db.rawQuery("SELECT * FROM napravlenie WHERE id=?",new String[] {String.valueOf(id)});
                query.moveToFirst();
                stroka=query.getString(1);
                query.close();
                break;
            case 3:
                query=db.rawQuery("SELECT * FROM groupa WHERE id=?",new String[] {String.valueOf(id)});
                query.moveToFirst();
                stroka=query.getString(1);
                query.close();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position_fngs);
        }
        et1.setText(stroka);
db.close();



        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            /** Внесение Изменения */
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();

                long id_zapisi ;
                if (id > 0) {
                    switch (position_fngs){
                        case 1:
                            values.put("name_faculteta", et1.getText().toString());
                            id_zapisi = db.update("facultet", values, "id = ?", new String[]{String.valueOf(id)});
                            break;
                        case 2:
                            values.put("name_napravlenie", et1.getText().toString());
                            id_zapisi = db.update("napravlenie", values, "id = ?", new String[]{String.valueOf(id)});
                            break;
                        case 3:
                            values.put("name_group", et1.getText().toString());
                            id_zapisi = db.update("groupa", values, "id = ?", new String[]{String.valueOf(id)});
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + position_fngs);
                    }
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                switch (position_fngs){
                case 1:
                OnClick_PokazatRefresh(1);
                    break;
                case 2:
                    OnClick_PokazatRefresh(2);
                    break;
                case 3:
                    OnClick_PokazatRefresh(3);
                    break;}

            }
        })
                /** Удаление*/
                .setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id_zapisi = 0;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                        switch (position_fngs){
                            case 1:
                                try{
                                    id_zapisi= db.delete("facultet", "id = ?", new String[]{String.valueOf(id)});
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(), "У этого факультета есть Студенты", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 2:
                                try{
                                id_zapisi= db.delete("napravlenie", "id = ?", new String[]{String.valueOf(id)});}
                                catch (Exception e){
                                    Toast.makeText(getApplicationContext(), "У этого направления есть Студенты", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 3:
                                try {
                                    id_zapisi = db.delete("groupa", "id = ?", new String[]{String.valueOf(id)});
                                }
                                catch (Exception e){Toast.makeText(getApplicationContext(), "У этой группы есть Студенты", Toast.LENGTH_LONG).show();
                                }
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + position_fngs);
                        }
                    if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                    else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();

                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                switch (position_fngs){
                    case 1:
                        OnClick_PokazatRefresh(1);
                        break;
                    case 2:
                        OnClick_PokazatRefresh(2);
                        break;
                    case 3:
                        OnClick_PokazatRefresh(3);
                        break;}
            }
        })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    //Конец.Редактирование

    //Редактирование Студента
    /** Легко запутаться оч много кода**/
    public void Redactirovanie_Studenta(Integer id){

        /**Вызывает окно для внесения изменения информации**/

        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);
        View vv1 = (LinearLayout) getLayoutInflater().inflate(R.layout.vibor_izmeneni9_fio_fng, null);
        subjectDialog.setView(vv1);
        final Button b1 =(Button) vv1.findViewById(R.id.vibor_fio);
        final Button b2 =(Button) vv1.findViewById(R.id.vibor_fng);

/** Обновление для фио**/
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
                subjectDialog.setTitle("Укажите новые данные");
                subjectDialog.setCancelable(false);
                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_fio, null);
                final EditText et1 = (EditText) vv.findViewById(R.id.izmenenie_lastname);
                final EditText et2 = (EditText) vv.findViewById(R.id.izmenenie_firstname);
                final EditText et3 = (EditText) vv.findViewById(R.id.izmenenie_middlename);
String stroka_firstname,stroka_lastname,stroka_middlename;

                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;


                        query=db.rawQuery("SELECT * FROM studenti WHERE id=?",new String[] {String.valueOf(id)});
                        query.moveToFirst();
                        stroka_lastname=query.getString(1);
                        stroka_firstname=query.getString(2);
                        stroka_middlename=query.getString(3);
                        query.close();
                et1.setText(stroka_lastname);
                et2.setText(stroka_firstname);
                et3.setText(stroka_middlename);

                db.close();

                subjectDialog.setView(vv);
                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        ContentValues values = new ContentValues();

                        long id_zapisi ;
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            values.put("last_name", et1.getText().toString());
                            values.put("first_name", et2.getText().toString());
                            values.put("middle_name", et3.getText().toString());
                            id_zapisi = db.update("studenti", values, "id = ?", new String[]{String.valueOf(id)});
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                            else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        db.close();
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectDialog.show();

            }
        });
        //Конец

/** Обновление для ФНГ**/
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Dobavlenie.this);
                subjectDialog.setTitle("Изменение");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_spiner, null);
                final Spinner sp1 = (Spinner) vv.findViewById(R.id.st_spiner_F);
                final Spinner sp2 = (Spinner) vv.findViewById(R.id.st_spiner_N);
                final Spinner sp3 = (Spinner) vv.findViewById(R.id.st_spiner_G);


                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;



//Для факультета
                //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                List<Adapter_odnogo_dobavleni9> facul_name = new ArrayList<Adapter_odnogo_dobavleni9>();
                //   Беру из бд выборку которая пришла (Select *)
                query=db.rawQuery("SELECT * FROM facultet", null);
                while (query.moveToNext()) {
                    //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                    Integer id= query.getInt(0);
                    String name= query.getString(1);
                    facul_name.add(new Adapter_odnogo_dobavleni9(id,name));
                }
                //После заполнения списка названий я передаю его
                Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,facul_name);
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
                //Конец. Факультета



                //Для Направления
                //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                List<Adapter_odnogo_dobavleni9> naprav_name = new ArrayList<Adapter_odnogo_dobavleni9>();
                //   Беру из бд выборку которая пришла (Select *)
                query=db.rawQuery("SELECT * FROM napravlenie", null);
                while (query.moveToNext()) {
                    //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                    Integer id= query.getInt(0);
                    String name= query.getString(1);
                    naprav_name.add(new Adapter_odnogo_dobavleni9(id,name));
                }
                //После заполнения списка названий я передаю его
                uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,naprav_name);
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
                //Конец.Направления


//Для Группы
                //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                List<Adapter_odnogo_dobavleni9> group_name = new ArrayList<Adapter_odnogo_dobavleni9>();
                //   Беру из бд выборку которая пришла (Select *)
                query=db.rawQuery("SELECT * FROM groupa", null);
                while (query.moveToNext()) {
                    //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                    Integer id= query.getInt(0);
                    String name= query.getString(1);
                    group_name.add(new Adapter_odnogo_dobavleni9(id,name));
                }
                //После заполнения списка названий я передаю его
                uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie.this,group_name);
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
                //Конец Группы


                db.close();
                subjectDialog.setView(vv);
//Он клик на позитив батон
                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Грубо говоря открываем бд ( подключаемся)
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        Cursor query;
                        //Значение куда сохраняем значение
                        ContentValues values = new ContentValues();
                        //В значение сохраняем имя столбца с названием которые ввели в edit

//Должно вернуть выделеный объект в спинере ( т.е id name)
                        if(id>0) {
                            Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                            values.put("id_facultet", aod.getId());
                            aod = (Adapter_odnogo_dobavleni9) sp2.getSelectedItem();
                            values.put("id_napravlenie", aod.getId());
                            aod = (Adapter_odnogo_dobavleni9) sp3.getSelectedItem();
                            values.put("id_groupa", aod.getId());
//Включаем добавление вторичных ключей
                            db.execSQL("PRAGMA foreign_keys=ON");
                            long id_zapisi = db.update("studenti", values, "id = ?", new String[]{String.valueOf(id)});
                            //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                            if (id_zapisi != -1)
                                Toast.makeText(getApplicationContext(), "Внесены изменения", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        db.close();
                        OnClick_PokazatRefresh(4);
                    }
                }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                subjectDialog.show();

            }
        });
//Конец

        /** Удаление Студента **/
        subjectDialog.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id_zapisi=0 ;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try{
                        id_zapisi=db.delete("studenti", "id = ?", new String[]{String.valueOf(id)});
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "У этого Студента есть запись в журнале", Toast.LENGTH_LONG).show();
                    }
                    //Если вернулось 1 значит удалили
                   if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                   else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                OnClick_PokazatRefresh(4);
            }

        })
        .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnClick_PokazatRefresh(4);

            }
        });
        subjectDialog.show();


    }
    //Конец.Редактирование Студента
    public void OnClick_PokazatRefresh(Integer id_naj ){

        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_F,query_N,query_G;
        switch(id_naj){
            case 1:
                query=db.rawQuery("SELECT * FROM facultet ORDER BY name_faculteta", null);
                position_fngs=1;
                break;
            case 2:
                query=db.rawQuery("SELECT * FROM napravlenie ORDER BY name_napravlenie", null);
                position_fngs=2;
                break;
            case 3:
                query=db.rawQuery("SELECT * FROM groupa ORDER BY name_group", null);
                position_fngs=3;
                break;
            case 4:
                query=db.rawQuery("SELECT * FROM studenti  ORDER BY last_name", null);
                position_fngs=4;
                break;
            /**Надо сделать новый адаптер и использованиее адаптера
             Во первых чтобы можно было 6 штучек 3 это ФИОН чтобы редактировать
             остальные 3 это видвижной спинер, как было с оценками чтобы я мог выбирать из существующих
             Затем грубо говоря выбирал по именам а добавлял по id

             2) Чтобы когда только выбрал все айтнемы загоралась кнопка добавить
             3) Просто отдельное меню где просто показывают все Факультеты группы ученики и прч и возможностью редактирования
             **/

            default:
                throw new IllegalStateException("Unexpected value: " + id_naj);
        }

        if(position_fngs!=4) {
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
            spisok = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id = query.getInt(0);
                String name = query.getString(1);
                spisok.add(new Using_adater_dobavlenie(id, name));

            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie.this, spisok);
            lv.setAdapter(adapter_dobavlenie);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()), position_fngs);
                }
            });

        }

/**Для студентов поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
        else if(position_fngs==4){
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
            spisok_studenta = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id,id_facultet,id_napravlenie,id_groupa;
                String last_name ,first_name , middle_name,naz_id_facultet,naz_id_napravlenie,naz_id_groupa ;

                id= query.getInt(0);
                last_name= query.getString(1); first_name= query.getString(2);middle_name= query.getString(3);
                id_facultet= query.getInt(4);id_napravlenie= query.getInt(5);id_groupa= query.getInt(6);

                /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
                query_F=db.rawQuery("SELECT * FROM facultet WHERE id=?",new String[] {id_facultet.toString()});
                query_F.moveToFirst();
                naz_id_facultet=query_F.getString(1);
                query_N=db.rawQuery("SELECT * FROM napravlenie WHERE id=?",new String[] {id_napravlenie.toString()});
                query_N.moveToFirst();
                naz_id_napravlenie=query_N.getString(1);
                query_G=db.rawQuery("SELECT * FROM groupa WHERE id=?",new String[] {id_groupa.toString()});
                query_G.moveToFirst();
                naz_id_groupa=query_G.getString(1);
                spisok_studenta.add(new Using_adapter_dobavleni9_studenta(id,last_name,first_name,middle_name,
                        naz_id_facultet,id_facultet,
                        naz_id_napravlenie,id_napravlenie,
                        naz_id_groupa,id_groupa));
                /** Закрытие табл*/
                query_F.close();
                query_N.close();
                query_G.close(); }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_studenta= new Adapter_dobavleni9_studenta(Dobavlenie.this, spisok_studenta);
            lv.setAdapter(adapter_dobavleni9_studenta);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Studenta(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.stlist_id)).getText().toString()));

                }
            });

        }



        query.close();
        db.close();
    }
    //Начало. Показываем список факультета,Направления,группы
    public void OnClick_Pokazat(View view){
        int id_naj=view.getId();
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_F,query_N,query_G;
        switch(id_naj){
            case R.id.pokazat_facultet:
                query=db.rawQuery("SELECT * FROM facultet ORDER BY name_faculteta", null);
                position_fngs=1;
                break;
            case R.id.pokazat_napravleni9:
                query=db.rawQuery("SELECT * FROM napravlenie ORDER BY name_napravlenie", null);
                position_fngs=2;
                break;
            case R.id.pokazat_groupi:
                query=db.rawQuery("SELECT * FROM groupa ORDER BY name_group", null);
                position_fngs=3;
                break;
            case R.id.pokazat_studenta:
                query=db.rawQuery("SELECT * FROM studenti  ORDER BY last_name", null);
                position_fngs=4;
                break;
                /**Надо сделать новый адаптер и использованиее адаптера
                Во первых чтобы можно было 6 штучек 3 это ФИОН чтобы редактировать
                 остальные 3 это видвижной спинер, как было с оценками чтобы я мог выбирать из существующих
                 Затем грубо говоря выбирал по именам а добавлял по id

                 2) Чтобы когда только выбрал все айтнемы загоралась кнопка добавить
                 3) Просто отдельное меню где просто показывают все Факультеты группы ученики и прч и возможностью редактирования
                 **/

            default:
                throw new IllegalStateException("Unexpected value: " + id_naj);
        }

        if(position_fngs!=4) {
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
            spisok = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id = query.getInt(0);
                String name = query.getString(1);
                spisok.add(new Using_adater_dobavlenie(id, name));

            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie.this, spisok);
            lv.setAdapter(adapter_dobavlenie);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()), position_fngs);
                }
            });

        }

/**Для студентов поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
else if(position_fngs==4){
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV);
            spisok_studenta = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id,id_facultet,id_napravlenie,id_groupa;
                String last_name ,first_name , middle_name,naz_id_facultet,naz_id_napravlenie,naz_id_groupa ;

                id= query.getInt(0);
              last_name= query.getString(1); first_name= query.getString(2);middle_name= query.getString(3);
              id_facultet= query.getInt(4);id_napravlenie= query.getInt(5);id_groupa= query.getInt(6);

              /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
                query_F=db.rawQuery("SELECT * FROM facultet WHERE id=?",new String[] {id_facultet.toString()});
                query_F.moveToFirst();
                naz_id_facultet=query_F.getString(1);
                query_N=db.rawQuery("SELECT * FROM napravlenie WHERE id=?",new String[] {id_napravlenie.toString()});
                query_N.moveToFirst();
                naz_id_napravlenie=query_N.getString(1);
                query_G=db.rawQuery("SELECT * FROM groupa WHERE id=?",new String[] {id_groupa.toString()});
                query_G.moveToFirst();
                naz_id_groupa=query_G.getString(1);
             spisok_studenta.add(new Using_adapter_dobavleni9_studenta(id,last_name,first_name,middle_name,
                     naz_id_facultet,id_facultet,
                     naz_id_napravlenie,id_napravlenie,
                     naz_id_groupa,id_groupa));
             /** Закрытие табл*/
                query_F.close();
                query_N.close();
                query_G.close(); }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_studenta= new Adapter_dobavleni9_studenta(Dobavlenie.this, spisok_studenta);
            lv.setAdapter(adapter_dobavleni9_studenta);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
 Redactirovanie_Studenta(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.stlist_id)).getText().toString()));

                }
            });

        }



        query.close();
        db.close();
    }
    //Конец.Показания

}


