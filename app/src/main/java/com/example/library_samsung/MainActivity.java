package com.example.library_samsung;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageButton btnAdd;
    DbHelper  db= new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView=findViewById(R.id.listView);
        btnAdd=findViewById(R.id.btnAdd);


        fillData();
        ItemAdapter adapter= new ItemAdapter(this,
                db.getData());
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext()
                        ,AddActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =  new Intent(getApplicationContext(),
                        ContentActivity.class);
                Book book = (Book) parent.getAdapter().getItem(position);
                intent.putExtra("key", book);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ItemAdapter adapter= new ItemAdapter(this,
                db.getData());
        listView.setAdapter(adapter);

    }

    void fillData() {
        List<Book> books = new ArrayList<>(Arrays.asList(
                new Book("Унесенные ветром", "Маргарет Митчелл", "1936", "R.drawable.gone_with_the_wind", "Роман", "Эпопея о любви и выживании в период Гражданской войны в США."),
                new Book("1984", "Джордж Оруэлл", "1949", "R.drawable.nineteen_eighty_four", "Дистопия", "Роман о тоталитарном государстве, где осуществляется постоянный контроль за гражданами."),
                new Book("Преступление и наказание", "Фёдор Достоевский", "1866", "R.drawable.crime_and_punishment", "Роман", "Глубокое исследование морали и последствий преступления."),
                new Book("451 градус по Фаренгейту", "Рэй Брэдбери", "1953", "R.drawable.fahrenheit_451", "Дистопия", "Роман о будущем, где книги запрещены, а пожарные сжигают их."),
                new Book("Над пропастью во ржи", "Джереми Сэлинджер", "1951", "R.drawable.catcher_in_the_rye", "Классика", "История о подростковом бунте и поиске идентичности."),
                new Book("Великий Гэтсби", "Фрэнсис Скотт Фицджеральд", "1925", "R.drawable.the_great_gatsby", "Классика", "Роман о любви, богатстве и утраченной мечте в 1920-х годах."),
                new Book("Мастер и Маргарита", "Михаил Булгаков", "1967", "R.drawable.master_and_margarita", "Роман", "Сложная и многослойная история о любви, добре и зле.")
        ));

        // Получаем объект базы данных
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        // Очищаем таблицу
        sqLiteDatabase.execSQL("DELETE FROM books");


        // Вставляем новые данные
        for (Book book : books) {
            db.insertData(book);
        }

        books.forEach(book -> db.insertData(book)); // Предполагается, что у вас есть метод insertData для Book
    }


}