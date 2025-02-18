package lk.evenro.even;

import android.content.Intent;
import android.database.Cursor;
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
import com.google.api.LogDescriptor;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lk.evenro.even.model.UserDetails;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText email = findViewById(R.id.sign_in_email);
        EditText password = findViewById(R.id.sign_in_password);

        Button signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type your Email", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Type your Password", Toast.LENGTH_SHORT).show();
                } else {

                    String userEmail = email.getText().toString();
                    String userPassword = password.getText().toString();

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Log.i("Loging Success",user.getEmail());
                                        Toast.makeText(getApplicationContext(), "Success Login", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignInActivity.this,DashboradMain.class);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        Log.i("Loging Error",task.getException().getMessage());
                                        Toast.makeText(getApplicationContext(), "Success Login"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

        });

        TextView text = findViewById(R.id.sign_in_text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}