package lk.evenro.even;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lk.evenro.even.model.UserDetails;

public class SignInActivity extends AppCompatActivity {

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
                    Log.i("Sign In", "Invalid Email");
                } else if (password.getText().toString().isEmpty()) {
                    Log.i("Sign In", "Invalid Password");
                } else {
                    UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);

                            while (cursor.moveToNext()) {
                                String resultEmail = cursor.getString(2);
                                String resultPassword = cursor.getString(4);
                                String resultName = cursor.getString(1);
                                Log.i("SIGN UP", resultEmail);

                                if (resultEmail.equals(email.getText().toString()) && resultPassword.equals(password.getText().toString())) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            email.setText("");
                                            password.setText("");
                                            Intent intent = new Intent(SignInActivity.this, DashboradMain.class);
                                            UserDetails user = new UserDetails(
                                                    resultEmail,
                                                    resultName
                                            );
                                            intent.putExtra("user_details",user);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    Log.i("SIGN UP", "Invalid user credential");
                                }

                            }
                            cursor.close();
                        }
                    }).start();
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