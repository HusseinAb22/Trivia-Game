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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Question_Answer> questions;
    private int ind = 0, count = 0;
    private List<String> wrongAnswers = new ArrayList<>();
    private TextView questionTextView;
    private Button buttonA, buttonB, buttonC, buttonD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.question);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        String[] options1 = {"Seoul", "Beijing", "Tokyo", "Bangkok"};
        Question_Answer q1 = new Question_Answer(options1, "Tokyo", "What is the capital of Japan?");
        String[] options2 = {"6", "8", "10", "12"};
        Question_Answer q2 = new Question_Answer(options2, "8", "What is the square root of 64?");
        String[] options3 = {"CO2", "O2", "H2O", "O3"};
        Question_Answer q3 = new Question_Answer(options3, "H2O", "What is the chemical symbol for water?");
        String[] options4 = {"Tiger", "Elephant", "Lion", "Bear"};
        Question_Answer q4 = new Question_Answer(options4, "Lion", "Which animal is known as the 'King of the Jungle'?");
        String[] options5 = {"South Korea", "Japan", "China", "India"};
        Question_Answer q5 = new Question_Answer(options5, "Japan", "Which country is known as the 'Land of the Rising Sun'?");

        questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);

        information(0);

        // Button A click listener
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions.get(ind).getCorrect_answer().equals(questions.get(ind).getAnswers()[0])) {
                    count++;
                } else {
                    String st = "Question: " + questions.get(ind).getQuestion() + "\nCorrect answer: " + questions.get(ind).getCorrect_answer() + "\nYour Answer: " + questions.get(ind).getAnswers()[0];
                    wrongAnswers.add(st);
                }
                nextQuestion();
            }
        });

        // Button B click listener
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions.get(ind).getCorrect_answer().equals(questions.get(ind).getAnswers()[1])) {
                    count++;
                } else {
                    String st = "Question: " + questions.get(ind).getQuestion() + "\nCorrect answer: " + questions.get(ind).getCorrect_answer() + "\nYour Answer: " + questions.get(ind).getAnswers()[1];
                    wrongAnswers.add(st);
                }
                nextQuestion();
            }
        });

        // Button C click listener
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions.get(ind).getCorrect_answer().equals(questions.get(ind).getAnswers()[2])) {
                    count++;
                } else {
                    String st = "Question: " + questions.get(ind).getQuestion() + "\nCorrect answer: " + questions.get(ind).getCorrect_answer() + "\nYour Answer: " + questions.get(ind).getAnswers()[2];
                    wrongAnswers.add(st);
                }
                nextQuestion();
            }
        });

        // Button D click listener
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions.get(ind).getCorrect_answer().equals(questions.get(ind).getAnswers()[3])) {
                    count++;
                } else {
                    String st = "Question: " + questions.get(ind).getQuestion() + "\nCorrect answer: " + questions.get(ind).getCorrect_answer() + "\nYour Answer: " + questions.get(ind).getAnswers()[3];
                    wrongAnswers.add(st);
                }
                nextQuestion();
            }
        });

        // Apply window insets for layout adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void nextQuestion() {
        if (ind < questions.size() - 1) {
            ind++;
            information(ind);
        } else {
            Intent intent = new Intent(MainActivity.this, Result.class);
            intent.putExtra("score", count);  // Pass the score (count)
            int q_size=questions.size();
            intent.putExtra("questions-num",q_size);
            intent.putStringArrayListExtra("wrongAnswers", new ArrayList<>(wrongAnswers));  // Pass the wrong answers list
            startActivity(intent);
        }
    }

    public void information(int ind) {
        questionTextView.setText("Question " + (ind + 1) + ": " + questions.get(ind).getQuestion());
        buttonA.setText("A: " + questions.get(ind).getAnswers()[0]);
        buttonB.setText("B: " + questions.get(ind).getAnswers()[1]);
        buttonC.setText("C: " + questions.get(ind).getAnswers()[2]);
        buttonD.setText("D: " + questions.get(ind).getAnswers()[3]);
    }
}
