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

public class Result extends AppCompatActivity {
    private TextView resultView, text1;
    private Button reset;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        resultView = findViewById(R.id.ResultView);
        text1 = findViewById(R.id.textView2);
        reset=findViewById(R.id.restart);
        dbHelper = new DatabaseHelper(this);
        // Retrieve score and wrongAnswers from the Intent
        int score = getIntent().getIntExtra("score", 0);  // Default to 0 if no score is passed
        List<String> wrongAnswers = getIntent().getStringArrayListExtra("wrongAnswers");  // Retrieve the list of wrong answers
        int questionscnt=getIntent().getIntExtra("questions-num",0);
        // Display results
        if (wrongAnswers != null && !wrongAnswers.isEmpty()) {
            results(score, wrongAnswers,questionscnt);
}
        else{
            results(score, wrongAnswers,questionscnt);
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this,MainActivity.class );
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void results(int cnt, List<String> wrong,int q_size) {
        String str;


        if(cnt==q_size){
            text1.setText("Gongrats you Won!");
            dbHelper.insertResults(cnt+"/"+q_size,wrong.toArray(new String[0]));

            str ="Perfect Score!!!\n Your Score: "+cnt+"/"+q_size+"\n\n";
        }
        else {
            text1.setText("You Lose!!! :(");
            dbHelper.insertResults(cnt+"/"+q_size,wrong.toArray(new String[0]));
            str = "Your Score: " + cnt +"/"+q_size+ "\n\n";
            for (int i = 0; i < wrong.size(); i++) {

                str += wrong.get(i) + "\n\n";
            }
        }
        resultView.setText(str);  // Set the result text
    }
}
