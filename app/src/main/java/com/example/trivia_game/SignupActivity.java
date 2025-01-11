package com.example.trivia_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trivia_game.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dbHelper=new DatabaseHelper(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.email.getText().toString();
                String password=binding.password.getText().toString();
                String confirmpassword=binding.confirmpassword.getText().toString();
                if(email.equals("")||password.equals("")||confirmpassword.equals("")){
                    Toast.makeText(SignupActivity.this,"Fill out all the fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(password.equals(confirmpassword)){
                        Boolean checkemail=dbHelper.checkEmail(email);
                        if (checkemail==false){
                            Boolean insert=dbHelper.insertData(email,password);
                            if(insert==true){
                                Toast.makeText(SignupActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignupActivity.this, "Email is in use try another email", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(SignupActivity.this, "Make sure the passwords match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}