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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
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
                    Log.i("SIGN UP", "Enter you Email");
                } else if (!Validation.isEmailValid(email.getText().toString())) {
                    Log.i("SIGN UP", "Invalid you Email");
                } else if (password.getText().toString().isEmpty()) {
                    Log.i("SIGN UP", "Enter you password");
                } else if (!Validation.isPasswordValid(password.getText().toString())) {
                    Log.i("SIGN UP", "Invalid you password");
                } else if (password.getText().toString().matches(confrom_password.getText().toString())) {
                    Log.i("SIGN UP", "This not match");
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

//class UserDataBase extends SQLiteOpenHelper {
//
//    public UserDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE user (\n" +
//                "    id           INTEGER PRIMARY KEY\n" +
//                "                         NOT NULL,\n" +
//                "    name         TEXT    NOT NULL,\n" +
//                "    email        TEXT    NOT NULL,\n" +
//                "    mobile       TEXT,\n" +
//                "    password     TEXT    NOT NULL,\n" +
//                "    gender       TEXT,\n" +
//                "    verification TEXT\n" +
//                ");");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
