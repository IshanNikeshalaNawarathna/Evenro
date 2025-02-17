package lk.evenro.even;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userName, userEmail, userMobile;
    String usersEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            usersEmail = user.getEmail();
            Log.i("TEST CODE", usersEmail);
        }

        Button saveButton = findViewById(R.id.save_profile_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String[] selectionArgs = {usersEmail};
                        Cursor cursor = userData.getReadableDatabase().query("user", null, null, selectionArgs, null, null, null);
                        if (cursor.moveToNext()) {
                            String email = cursor.getString(2);
                            String name = cursor.getString(1);
                            String mobile = cursor.getString(3);
                            Log.i("SIGN UP", email);
//                            userName.setText(name);
//                            userEmail.setText(email);
//                            userMobile.setText(mobile);
                        }

//                        else {
//                            String name = userName.getText().toString().trim();
//                            String email = userEmail.getText().toString().trim();
//                            String mobile = userMobile.getText().toString().trim();
//
//                            SQLiteDatabase database = userData.getWritableDatabase();
//                            ContentValues contentValues = new ContentValues();
//                            contentValues.put("name", name);
//                            contentValues.put("email", email);
//                            contentValues.put("mobile", mobile);
//
//                            long id = database.insert("user", null, contentValues);
//                            Log.i("SIGN UP", String.valueOf(id));
//                        }
                    }
                }).start();
            }
        });


    }
}

class UserDataBase extends SQLiteOpenHelper {

    public UserDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // CALL THE ON TIME
        db.execSQL("CREATE TABLE user (\n" +
                "    id        INTEGER PRIMARY KEY\n" +
                "                      NOT NULL,\n" +
                "    name      TEXT    NOT NULL,\n" +
                "    email     TEXT    NOT NULL,\n" +
                "    mobile    TEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // VERSION CHANGE IF THIS METHODE CALL

    }
}


