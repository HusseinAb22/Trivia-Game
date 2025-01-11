package com.example.trivia_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.trivia_game.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        // Set up the Login button
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Fill out all the fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean checkEmail = dbHelper.checkEmail(email);
                if (!checkEmail) {
                    Toast.makeText(LoginActivity.this, "Invalid email, please sign up!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkEmailPass = dbHelper.checkEmailpassword(email, password);
                    if (!checkEmailPass) {
                        Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), StartGame.class);
                        startActivity(intent);
                    }
                }
            }
        });

        // Set up the Sign Up button
        binding.signup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });
    }
}