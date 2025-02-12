package lk.evenro.even;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lk.evenro.even.validation.Validation;

public class SignUpActivity extends AppCompatActivity {

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
        EditText full_name = findViewById(R.id.sign_up_full_name);
        EditText email = findViewById(R.id.sign_up_email);
        EditText password = findViewById(R.id.sign_up_password);
        EditText confrom_password = findViewById(R.id.sign_up_confrom_password);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (full_name.getText().toString().isEmpty()) {
                    Log.i("SIGN UP", "Enter you Full Name");
                } else if (email.getText().toString().isEmpty()) {
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

                    UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            int code = (int) (Math.random() * 1000);

                            Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);
                            boolean emailExisis = false;
                            while (cursor.moveToNext()) {
                                String resultEmail = cursor.getString(2);
                                Log.i("SIGN UP", resultEmail);

                                if (resultEmail.equals(email.getText().toString())) {
                                    Log.i("SIGN UP", "Email already Use");
                                    emailExisis = true;
                                    break;
                                }
                            }
                            cursor.close();

                            if (!emailExisis) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", full_name.getText().toString());
                                contentValues.put("email", email.getText().toString());
                                contentValues.put("password", confrom_password.getText().toString());
                                contentValues.put("verification", code);

                                long id = userData.getWritableDatabase().insert("user", null, contentValues);
                                Log.i("SIGN UP", String.valueOf(id));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        full_name.setText("");
                                        email.setText("");
                                        password.setText("");
                                        confrom_password.setText("");

                                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }).start();
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

class UserDataBase extends SQLiteOpenHelper {

    public UserDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (\n" +
                "    id           INTEGER PRIMARY KEY\n" +
                "                         NOT NULL,\n" +
                "    name         TEXT    NOT NULL,\n" +
                "    email        TEXT    NOT NULL,\n" +
                "    mobile       TEXT,\n" +
                "    password     TEXT    NOT NULL,\n" +
                "    gender       TEXT,\n" +
                "    verification TEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
