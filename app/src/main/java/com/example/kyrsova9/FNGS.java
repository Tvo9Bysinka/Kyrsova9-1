package com.example.kyrsova9;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FNGS extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facul_naprav_group_stud);

        String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner_facultet);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


   String[] countries1 = { "0.2 03 ", "0,2 05 ", "Колу", "Чили", "Уругвай"};
                Spinner spinner = (Spinner) findViewById(R.id.spinner_napravlenie);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(parent.getContext(),android.R.layout.simple_spinner_item, countries1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter1);
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(itemSelectedListener);
        spinner=(Spinner) findViewById(R.id.spinner_napravlenie);
        spinner.setAdapter(adapter);
       spinner= (Spinner) findViewById(R.id.spinner_group);
        spinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("Выход");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case 0:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
