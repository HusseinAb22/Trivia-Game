package com.example.trivia_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class StartGame extends AppCompatActivity {
    Button start;
    TextView lastResult;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_game);

        dbHelper = new DatabaseHelper(this);


        start = findViewById(R.id.startgame);
        lastResult = findViewById(R.id.LastResult);

        String Date_Time=dbHelper.getLastResultDateTime();
        String str;
        List<String[]> allResults = dbHelper.getAllResults();

        str=Date_Time+"\n\n";

        if (!allResults.isEmpty()) {
            str += allResults.get(allResults.size() - 1)[0] + "\n\n\n";
            lastResult.setText(str);
        } else {
            lastResult.setText("No previous results found.");
        }


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGame.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}