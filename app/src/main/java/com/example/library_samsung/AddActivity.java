package com.example.library_samsung;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {
    String selectedGenre;
    Spinner spGenre;
    EditText etAuthor, etYear, etTitle, etDescription;
    ImageView ivCover;
    Button btnInsert;
    Uri imagePath = null;
    DbHelper db;
    private final int GALLERY_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDecsription);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);

        ivCover = findViewById(R.id.ivCover);
        btnInsert = findViewById(R.id.btnInsert);
        spGenre = findViewById(R.id.spGenre);

        db = new DbHelper(this);

        // массив для Spinner
        String[] spinnerItems = {"жанр", "фэнтези", "роман", "фантастика"};

        // адаптер для Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(adapter);

        // слушатель для Spinner
        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenre = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQUEST_CODE);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String author = etAuthor.getText().toString();
                String year = etYear.getText().toString();
                String description = etDescription.getText().toString();

                if (title.isEmpty() || author.isEmpty() || year.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "заполните все поля!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Book book = new Book(title, author, year, imagePath.toString(), selectedGenre, description);
                boolean result = db.insertData(book);

                if (result) {
                    Toast.makeText(getApplicationContext(),
                            "вata inserted",
                            Toast.LENGTH_SHORT);
                    finish();
                }
                else Toast.makeText(getApplicationContext(),
                        "вata not inserted",
                        Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_REQUEST_CODE){
                imagePath = data.getData();
                ivCover.setImageURI(imagePath);
            }
        }
    }
}