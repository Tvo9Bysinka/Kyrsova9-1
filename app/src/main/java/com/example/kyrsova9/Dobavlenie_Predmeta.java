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

public class Dobavlenie_Predmeta extends AppCompatActivity {
    ArrayList<Using_adater_dobavlenie> spisok;
    Adapter_dobavlenie adapter_dobavlenie;
    ArrayList<Using_adapter_dobavleni9_predmeta> spisok_predmeta;
    Adapter_dobavleni9_predmeta adapter_dobavleni9_predmeta;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okno_dobavleni9_fk_predmeta);
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

    // Начало.ФК
    /**  Тк как смысл один и тотже как и поля то просто скопировали метод поменяв поля таблицы*/
    public void OnClick_perehod_k_dobav_fk(View view) {
//Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Введите Вид занятия");
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
                values.put("vid_formi", et1.getText().toString());
                //Ну и откуда берём таблицу запихиваем в переменную
                long id_zapisi= db.insert("forma_kontrol9", null, values);
                //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                if(id_zapisi!=-1) Toast.makeText(getApplicationContext(),"Успешно добавлено", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Произошла ошибка", Toast.LENGTH_LONG).show();
                db.close();
                OnClick_Pokazat_Fk_Predmet_Refresh(1);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    // Конец.ФК

    /**Добавление Предмета*/
    public void OnClick_perehod_k_dobav_predmeta(View view){
        //Вызов/создание диалогового окна
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Введите название дисциплины");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_predmeta, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_predmeta_name);
        final Spinner sp1 = (Spinner) vv.findViewById(R.id.spinner_Fk);



        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query;
        //Флаг для того чтобы понятьт заходит ли в вайл ( т.е проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
        int flag_Fk=0;
//Для Должности
        //Делаю адаптер для заполнения, должности где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
        List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();
        //   Беру из бд выборку которая пришла (Select *)
        query=db.rawQuery("SELECT * FROM forma_kontrol9", null);
        while (query.moveToNext()) {
            //выбираем  столбцы  типо id  и название (нам нужно название должности)
            Integer id= query.getInt(0);
            String name= query.getString(1);
            fk_name.add(new Adapter_odnogo_dobavleni9(id,name));
            flag_Fk=1;
        }
        //После заполнения списка названий я передаю его и добавляю в спинер ( состоящий из одного элемента)
        Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Predmeta.this,fk_name);
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
        int finalFlag_Fk = flag_Fk;
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
                values.put("name_premeta", et1.getText().toString());

//Должно вернуть выделеный объект в спинере ( т.е id name)

                Adapter_odnogo_dobavleni9 aod;
                //Проверка пустые ли таблицы ФНГ, если пустые то не даст добавить студента)
                if (finalFlag_Fk ==1){
                    aod = (Adapter_odnogo_dobavleni9) sp1.getSelectedItem();
                    values.put("id_forma_kontrol9", aod.getId());
//Включаем добавление вторичных ключей
                    db.execSQL("PRAGMA foreign_keys=ON");
                    long id_zapisi = db.insert("predmet", null, values);
                    //Проверяем если таблица не пустая то добавилось и говорим это иначе не добавилось(вроде)
                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Работник успешно добавлен", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else  Toast.makeText(getApplicationContext(), "Занесите информацию в пустые поля", Toast.LENGTH_LONG).show();
                OnClick_Pokazat_Fk_Predmet_Refresh(4);
                db.close();
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        subjectDialog.show();
    }
    /**Конец Предмета*/


    //Редактирование
    public void Redactirovanie_Fk(Integer id){
        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);

        View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
        final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        String stroka;

        Cursor query;


        query=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {String.valueOf(id)});
        query.moveToFirst();
        stroka=query.getString(1);
        query.close();


        et1.setText(stroka);


        subjectDialog.setView(vv);
        subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            /** Внесение Изменения */
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();

                long id_zapisi ;
                if (id > 0) {
                    values.put("vid_formi", et1.getText().toString());
                    id_zapisi = db.update("forma_kontrol9", values, "id = ?", new String[]{String.valueOf(id)});

                    if (id_zapisi != -1)
                        Toast.makeText(getApplicationContext(), "Изменения внесены", Toast.LENGTH_LONG).show();
                    else Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                OnClick_Pokazat_Fk_Predmet_Refresh(1);
                db.close();
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
                                id_zapisi= db.delete("forma_kontrol9", "id = ?", new String[]{String.valueOf(id)});
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), "У этого вида занятия  есть Предмет", Toast.LENGTH_LONG).show();
                            }
                            if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                            else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();

                        }
                        else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
                        OnClick_Pokazat_Fk_Predmet_Refresh(1);
                        db.close();
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

    //Редактирование Предмета
    /** Легко запутаться оч много кода**/
    public void Redactirovanie_Prepmeta(Integer id){

        /**Вызывает окно для внесения изменения информации**/

        AlertDialog.Builder subjectDialog;
        subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
        subjectDialog.setTitle("Укажите новые данные");
        subjectDialog.setCancelable(false);
        View vv1 = (LinearLayout) getLayoutInflater().inflate(R.layout.vibor_izmeneni9_predmeta_fk, null);
        subjectDialog.setView(vv1);
        final Button b1 =(Button) vv1.findViewById(R.id.vibor_name_predmeta);
        final Button b2 =(Button) vv1.findViewById(R.id.vibor_fk);

/** Обновление для Предмета**/
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
                subjectDialog.setTitle("Укажите новые данные");
                subjectDialog.setCancelable(false);
                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.dobavlenie_odno_pole_edit, null);
                final EditText et1 = (EditText) vv.findViewById(R.id.ed_dobavlenie_odno_pole);


                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                String stroka;

                Cursor query;


                query=db.rawQuery("SELECT * FROM predmet WHERE id=?",new String[] {String.valueOf(id)});
                query.moveToFirst();
                stroka=query.getString(1);
                query.close();


                et1.setText(stroka);




                subjectDialog.setView(vv);
                subjectDialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                        ContentValues values = new ContentValues();

                        long id_zapisi ;
                        if (id > 0) {
                            db.execSQL("PRAGMA foreign_keys=ON");
                            values.put("name_premeta", et1.getText().toString());
                            id_zapisi = db.update("predmet", values, "id = ?", new String[]{String.valueOf(id)});
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

/** Обновление для ФК**/
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder subjectDialog;
                subjectDialog = new AlertDialog.Builder(Dobavlenie_Predmeta.this);
                subjectDialog.setTitle("Изменение");
                subjectDialog.setCancelable(false);

                View vv = (LinearLayout) getLayoutInflater().inflate(R.layout.izmenenie_odin_spiner, null);
                final Spinner sp1 = (Spinner) vv.findViewById(R.id.odin_spinner);


                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                Cursor query;

//Для ФК
                //Делаю адаптер для заполнения, факультета где я могу потом получать и id  и имя где потом записываться в выдвижной спинер
                List<Adapter_odnogo_dobavleni9> fk_name = new ArrayList<Adapter_odnogo_dobavleni9>();
                //   Беру из бд выборку которая пришла (Select *)
                query=db.rawQuery("SELECT * FROM forma_kontrol9", null);
                while (query.moveToNext()) {
                    //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                    Integer id= query.getInt(0);
                    String name= query.getString(1);
                    fk_name.add(new Adapter_odnogo_dobavleni9(id,name));
                }
                //После заполнения списка названий я передаю его
                Using_adapter_odnogo_dobavleni9 uaod = new Using_adapter_odnogo_dobavleni9(Dobavlenie_Predmeta.this,fk_name);
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
                //Конец.




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
                        values.put("id_forma_kontrol9", aod.getId());
//Включаем добавление вторичных ключей
                        db.execSQL("PRAGMA foreign_keys=ON");
                        long  id_zapisi = db.update("predmet", values, "id = ?", new String[]{String.valueOf(id)});
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

        /** Удаление Предмета**/
        subjectDialog.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long id_zapisi=0 ;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                if (id > 0) {
                    db.execSQL("PRAGMA foreign_keys=ON");
                    try{
                        id_zapisi=db.delete("predmet", "id = ?", new String[]{String.valueOf(id)});
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "У этого Предмета есть запись в журнале", Toast.LENGTH_LONG).show();
                    }
                    if(id_zapisi==1) Toast.makeText(getApplicationContext(),"Произошло удаление",Toast.LENGTH_LONG).show();
                    else  Toast.makeText(getApplicationContext(),"Ошибка удаления",Toast.LENGTH_LONG).show();
                }
                else { Toast.makeText(getApplicationContext(), "Некоректный id", Toast.LENGTH_LONG).show(); }
               OnClick_Pokazat_Fk_Predmet_Refresh(4);
                db.close();
            }
        })
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OnClick_Pokazat_Fk_Predmet_Refresh(4);
                    }
                });
        subjectDialog.show();


    }
    //Конец.Редактирование Предмета

    //Начало. Показываем список Должность Препода
    public void OnClick_Pokazat_Fk_Predmet_Refresh(Integer id_naj){
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_D;
        switch(id_naj){
            case 1:
                query=db.rawQuery("SELECT * FROM forma_kontrol9 ORDER BY vid_formi", null);
                position_fngs=1;
                break;
            case 4:
                query=db.rawQuery("SELECT * FROM predmet ORDER BY name_premeta", null);
                position_fngs=4;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + id_naj);
        }


        if(position_fngs!=4) {
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_fk_predmet);
            spisok = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id = query.getInt(0);
                String name = query.getString(1);
                spisok.add(new Using_adater_dobavlenie(id, name));

            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie_Predmeta.this, spisok);
            lv.setAdapter(adapter_dobavlenie);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Fk(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()));
                }
            });

        }

/**Для Препода поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
        else if(position_fngs==4){
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_fk_predmet);
            spisok_predmeta = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id,id_fk;
                String name_predmeta,naz_id_fk;
                id= query.getInt(0);
                name_predmeta= query.getString(1);
                id_fk= query.getInt(2);

                /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
                query_D=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {id_fk.toString()});
                query_D.moveToFirst();
                naz_id_fk=query_D.getString(1);
                spisok_predmeta.add(new Using_adapter_dobavleni9_predmeta(id,name_predmeta,
                        naz_id_fk,id_fk));
                /** Закрытие табл*/
                query_D.close();
            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_predmeta= new Adapter_dobavleni9_predmeta(Dobavlenie_Predmeta.this, spisok_predmeta);
            lv.setAdapter(adapter_dobavleni9_predmeta);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Prepmeta(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prlist_id)).getText().toString()));

                }
            });

        }



        query.close();
        db.close();
    }
    //Конец.Показания
    //Начало. Показываем список Должность Препода
    public void OnClick_Pokazat_Fk_Predmet(View view){
        int id_naj=view.getId();
        final int position_fngs;
        //подключаемся к базе
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //Беру из бд выборку которая пришла (Select *)
        Cursor query,query_D;
        switch(id_naj){
            case R.id.pokazat_fk:
                query=db.rawQuery("SELECT * FROM forma_kontrol9 ORDER BY vid_formi", null);
                position_fngs=1;
                break;
            case R.id.pokazat_predmet:
                query=db.rawQuery("SELECT * FROM predmet ORDER BY name_premeta", null);
                position_fngs=4;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + id_naj);
        }

        if(position_fngs!=4) {
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_fk_predmet);
            spisok = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id = query.getInt(0);
                String name = query.getString(1);
                spisok.add(new Using_adater_dobavlenie(id, name));

            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavlenie = new Adapter_dobavlenie(Dobavlenie_Predmeta.this, spisok);
            lv.setAdapter(adapter_dobavlenie);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Fk(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.list_id)).getText().toString()));
                }
            });

        }

/**Для Препода поиск чтобы я искал поиск по ийди находил этой айди и отображалось не айди а название факультета**/
        else if(position_fngs==4){
            ListView lv = (ListView) findViewById(R.id.dobavlenie_LV_fk_predmet);
            spisok_predmeta = new ArrayList<>();
            //идём по записям в таблицах (типо идём по строкам таблицы)
            while (query.moveToNext()) {
                //выбираем  столбцы  типо id  и название (нам нужно название Факультета, направ, группы)
                Integer id,id_fk;
                String name_predmeta,naz_id_fk;
                id= query.getInt(0);
                name_predmeta= query.getString(1);
                id_fk= query.getInt(2);

                /**Нахожу строчку по выбранному айди( перевести в стринг надо) **/
                query_D=db.rawQuery("SELECT * FROM forma_kontrol9 WHERE id=?",new String[] {id_fk.toString()});
                query_D.moveToFirst();
                naz_id_fk=query_D.getString(1);
                spisok_predmeta.add(new Using_adapter_dobavleni9_predmeta(id,name_predmeta,
                        naz_id_fk,id_fk));
                /** Закрытие табл*/
                query_D.close();
            }
            //Ну и полученный список в адаптер чтобы отобразить
            adapter_dobavleni9_predmeta= new Adapter_dobavleni9_predmeta(Dobavlenie_Predmeta.this, spisok_predmeta);
            lv.setAdapter(adapter_dobavleni9_predmeta);
            //Чтобы для всех записей можно было выбрать редактирование
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    //вызываем метод где будем вносить изменение если нам понадобиться
                    Redactirovanie_Prepmeta(Integer.valueOf(((TextView) itemClicked.findViewById(R.id.prlist_id)).getText().toString()));

                }
            });

        }



        query.close();
        db.close();
    }
    //Конец.Показания

}

