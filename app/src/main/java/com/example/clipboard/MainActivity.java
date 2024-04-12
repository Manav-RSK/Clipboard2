package com.example.clipboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;

    Button Upload , Fetch ;
    TextView code ;
    EditText EdtRead , EdtWrite ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Upload = findViewById(R.id.btnupload);
        Fetch = findViewById(R.id.btnfetch);
        code = findViewById(R.id.code);
        EdtRead = findViewById(R.id.edtRead);
        EdtWrite = findViewById(R.id.edtWrite);

        String ToRead = " ";

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ToWrite = EdtWrite.getText().toString() ;
                String val = random_val();
                code.setText(val);
                myRef = database.getReference(val);
                myRef.setValue(ToWrite);
            }
        });
        Fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ToWrite = EdtRead.getText().toString() ;
                String val = ToWrite;
                myRef = database.getReference(val);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            String data = dataSnapshot.getValue().toString() ;
                            EdtRead.setText(data);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "No value found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors if any
                    }
                });
            }
        });

    }

    private String random_val() {
        Random random = new Random();
        return Integer.toString(random.nextInt(9000) + 1000);
    }
}