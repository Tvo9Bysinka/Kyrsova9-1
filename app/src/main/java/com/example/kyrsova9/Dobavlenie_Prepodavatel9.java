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

public class Dobavlenie_Prepodavatel9 extends AppCompatActivity {
    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;
    ArrayList<Using_adapter_dobavleni9_prepodavatel9> spisok_prepodavatel9;
    Adapter_dobavleni9_prepodavatel9 adapter_dobavleni9_prepodavatel9;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okno_dobavleni9_doljnosti_and_prepoda);
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

    // Начало.Добавление Должности
/**  Тк как смысл один и тотже как и поля то просто скопировали метод поменяв поля таблицы*/
    public void OnClick_perehod_k_dobav_doljnosti(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
        subjectDialog.setTitle("Введите название должности");
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
                values.put("name_doljnost", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi= db.insert("doljnost", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Должность успешно добавлена", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(1);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    // Конец.Добавление Должности

/**Добавление Препода*/
    public void OnClick_perehod_k_dobav_prepodavatel9(View view){
        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
        subjectDialog.setTitle("Введите ФИО работника");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_prepodavatel9, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_prepod_lastname);
        final EditText et2 = (EditText) vv.findViewById(R.id.ed_dobavlenie_prepod_name);
        final EditText et3 = (EditText) vv.findViewById(R.id.ed_dobavlenie_prepod_middlename);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_D);



        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        //Флаг для того чтобы понятьт заходит ли в вайл ( т.е проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
        int flag_D=0;
//Для Должности
        //Делаю адаптер для заполнения, должности где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> doljnost_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM doljnost", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название должности)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            doljnost_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_D=1;
        }
        //После заполнения списка названий я передаю его и добавляю в спинер ( состоящий из одного элемента)
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Prepodavatel9.this,doljnost_name);
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
        //Конец. Должности
        db.close();
        subjectDialog.setView(vv);
//переопределение флага чтобы зайти в диалоговое окно
        int finalFlag_D = flag_D;
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
                if (finalFlag_D ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                    values.put("id_doljnost", aod.getId());
//Включаем добавление вторичных ключей
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long id_zapisi = db.insert("rabotnik", null, values);
                    //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Работник успешно добавлен", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(4);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
/**Конец Препод*/
    //Редактирование
    public void Redactirovanie_Doljnosti(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String stroka;

        Cursor query;


                query=db.rawQuery("SELECT * FROM doljnost WHERE id=?",new String[] {String.valueOf(id)});
                query.moveToFirst();
                stroka=query.getString(1);
                query.close();


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
                            values.put("name_doljnost", et1.getText().toString());
                            id_zapisi = db.update("doljnost", values, "id = ?", new String[]{String.valueOf(id)});

                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(1);
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
                                    try{
                                        id_zapisi= db.delete("doljnost", "id = ?", new String[]{String.valueOf(id)});
                                    }catch (Exception e){
                                        Toast.makeText(getApplicationContext(), "У этой должности есть Работник", Toast.LENGTH_LONG).show();
                                    }
                            if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                            else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();

                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        db.close();
                        OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(1);
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

    //Редактирование Преподавателя
    /** Легко запутаться оч много кода**/
    public void Redactirovanie_Prepodavatel9(Integer id){

        /**Вызывает окно для внесения изменения информации**/

        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);
        View vv1 = (LinearLayout) getLayoutInflater().inflate(R.layout.vibor_izmeneni9_fio_prepod_doljnost, null);
        subjectDialog.setView(vv1);
        final Button b1 =(Button) vv1.findViewById(R.id.vibor_fio_prepoda);
        final Button b2 =(Button) vv1.findViewById(R.id.vibor_doljnosti);

/** Обновление для фио**/
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
                subjectDialog.setTitle("Укажите новые данные");
                subjectDialog.setCancelable(false);
                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_fio, null);
                final EditText et1 = (EditText) vv.findViewById(R.id.izmenenie_lastname);
                final EditText et2 = (EditText) vv.findViewById(R.id.izmenenie_firstname);
                final EditText et3 = (EditText) vv.findViewById(R.id.izmenenie_middlename);

                String stroka_firstname,stroka_lastname,stroka_middlename;

                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;


                query=db.rawQuery("SELECT * FROM rabotnik WHERE id=?",new String[] {String.valueOf(id)});
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
                            id_zapisi = db.update("rabotnik", values, "id = ?", new String[]{String.valueOf(id)});
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

/** Обновление для Должности**/
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Dobavlenie_Prepodavatel9.this);
                subjectDialog.setTitle("Изменение");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_odin_spiner, null);
                final Spinner sp1 = (Spinner) vv.findViewById(R.id.odin_spinner);


                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;

//Для Должности
                //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                List<Adapter_odnogo_dobavleni9> doljnost_name = new ArrayList<Adapter_odnogo_dobavleni9>();
                //   Беру из бд выборку которая пришла (Select *)
                query=db.rawQuery("SELECT * FROM doljnost", null);
                while (query.moveToNext()) {
                    //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                    Integer id= query.getInt(0);
                    String name= query.getString(1);
                    doljnost_name.add(new Adapter_odnogo_dobavleni9(id,name));
                }
                //После заполнения списка названий я передаю его
                Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Prepodavatel9.this,doljnost_name);
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
                //Конец. Должности




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
                        if(id>0){
                        Adapter_odnogo_dobavleni9 aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                        values.put("id_doljnost", aod.getId());
//Включаем добавление вторичных ключей
                        db.execSQL("PRAGMA foreign_keys=ON");
                        long  id_zapisi = db.update("rabotnik", values, "id = ?", new String[]{String.valueOf(id)});
                        //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                        if(id_zapisi !=-1) Toast.makeText(getApplicationContext(),"Внесены изменения", Toast.LENGTH_LONG).show();
                        else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
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

        /** Удаление Работника **/
        subjectDialog.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id_zapisi=0 ;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try{
                        id_zapisi=db.delete("rabotnik", "id = ?", new String[]{String.valueOf(id)});
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "У этого Работника есть запись в журнале", Toast.LENGTH_LONG).show();
                    }
                    if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                    else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                db.close();
                OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(4);
            }
        })
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(4);

                    }
                });
        subjectDialog.show();


    }
    //Конец.Редактирование Преподавателя

    //Начало. Показываем список Должность Препода
    public void OnClick_Pokazat_Doljnosti_Prepodavatel9(View view){
        int id_naj=view.getId();
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_D;
        switch(id_naj){
            case R.id.pokazat_doljnosti:
                query=db.rawQuery("SELECT * FROM doljnost ORDER BY name_doljnost", null);
                position_fngs=1;
                break;
            case R.id.pokazat_prepodavatel9:
                query=db.rawQuery("SELECT * FROM rabotnik ORDER BY last_name", null);
                position_fngs=4;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + id_naj);
        }

        if(position_fngs!=4) {
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_doljnost_prepodavatel);
            spisok = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id = query.getInt(0);
                String name = query.getString(1);
                spisok.add(new Using_adater_dobavlenie(id, name));

            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie_Prepodavatel9.this, spisok);
            lv.setAdapter(adapter_dobavlenie);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Doljnosti(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()));
                }
            });

        }

/**Для Препода поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
        else if(position_fngs==4){
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_doljnost_prepodavatel);
            spisok_prepodavatel9 = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id,id_doljnost;
                String last_name ,first_name , middle_name,naz_id_doljnost;
                id= query.getInt(0);
                last_name= query.getString(1); first_name= query.getString(2);middle_name= query.getString(3);
                id_doljnost= query.getInt(4);

                /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
                query_D=db.rawQuery("SELECT * FROM doljnost WHERE id=?",new String[] {id_doljnost.toString()});
                query_D.moveToFirst();
                naz_id_doljnost=query_D.getString(1);
                spisok_prepodavatel9.add(new Using_adapter_dobavleni9_prepodavatel9(id,last_name,first_name,middle_name,
                        naz_id_doljnost,id_doljnost));
                /** Закрытие табл*/
                query_D.close();
                 }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_prepodavatel9= new Adapter_dobavleni9_prepodavatel9(Dobavlenie_Prepodavatel9.this, spisok_prepodavatel9);
            lv.setAdapter(adapter_dobavleni9_prepodavatel9);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Prepodavatel9(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prepodavatel9list_id)).getText().toString()));

                }
            });

        }



        query.close();
        db.close();
    }
    //Конец.Показания
    public void OnClick_Pokazat_Doljnosti_Prepodavatel9_Refresh(Integer id_naj){
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_D;
        switch(id_naj){
            case 1:
                query=db.rawQuery("SELECT * FROM doljnost ORDER BY name_doljnost", null);
                position_fngs=1;
                break;
            case 4:
                query=db.rawQuery("SELECT * FROM rabotnik ORDER BY last_name", null);
                position_fngs=4;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + id_naj);
        }

        if(position_fngs!=4) {
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_doljnost_prepodavatel);
            spisok = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id = query.getInt(0);
                String name = query.getString(1);
                spisok.add(new Using_adater_dobavlenie(id, name));

            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie_Prepodavatel9.this, spisok);
            lv.setAdapter(adapter_dobavlenie);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Doljnosti(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()));
                }
            });

        }

/**Для Препода поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
        else if(position_fngs==4){
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_doljnost_prepodavatel);
            spisok_prepodavatel9 = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id,id_doljnost;
                String last_name ,first_name , middle_name,naz_id_doljnost;
                id= query.getInt(0);
                last_name= query.getString(1); first_name= query.getString(2);middle_name= query.getString(3);
                id_doljnost= query.getInt(4);

                /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
                query_D=db.rawQuery("SELECT * FROM doljnost WHERE id=?",new String[] {id_doljnost.toString()});
                query_D.moveToFirst();
                naz_id_doljnost=query_D.getString(1);
                spisok_prepodavatel9.add(new Using_adapter_dobavleni9_prepodavatel9(id,last_name,first_name,middle_name,
                        naz_id_doljnost,id_doljnost));
                /** Закрытие табл*/
                query_D.close();
            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_prepodavatel9= new Adapter_dobavleni9_prepodavatel9(Dobavlenie_Prepodavatel9.this, spisok_prepodavatel9);
            lv.setAdapter(adapter_dobavleni9_prepodavatel9);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Prepodavatel9(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prepodavatel9list_id)).getText().toString()));

                }
            });

        }



        query.close();
        db.close();
    }
}


