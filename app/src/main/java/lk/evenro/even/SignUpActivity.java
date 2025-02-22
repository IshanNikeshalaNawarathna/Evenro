package lk.evenro.even;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lk.evenro.even.validation.Validation;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button sign_up_button = findViewById(R.id.sign_up_button);

        EditText email = findViewById(R.id.sign_up_email);
        EditText password = findViewById(R.id.sign_up_password);
        EditText confrom_password = findViewById(R.id.sign_up_confrom_password);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type your Email", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isEmailValid(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type your Password", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isPasswordValid(password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().matches(confrom_password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password is not match", Toast.LENGTH_SHORT).show();
                } else {

                    String userEmail = email.getText().toString();
                    String userPassword = password.getText().toString();


                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Log.i("Loging Success",user.getEmail());

                                        Intent intent = new Intent(SignUpActivity.this,DashboradMain.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        Toast.makeText(getApplicationContext(), "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.i("Loging Error",task.getException().getMessage());
                                    }
                                }
                            });

                }
            }
        });

        TextView sing_in_text = findViewById(R.id.sign_up_text);
        sing_in_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}

