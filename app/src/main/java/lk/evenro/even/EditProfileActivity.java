package lk.evenro.even;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import lk.evenro.even.model.UserDetails;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userName, userEmail, userMobile;
    String usersEmails, userIDs;
    private Uri imageUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userEmail = findViewById(R.id.user_email);
        userName = findViewById(R.id.user_name);
        userMobile = findViewById(R.id.user_mobile);
        Button saveButton = findViewById(R.id.save_profile_button);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView icon = findViewById(R.id.user_email_edit);
        String email = user.getEmail();
        char firstCharUpper = Character.toUpperCase(email.charAt(0));
        icon.setText(String.valueOf(firstCharUpper));
        if (user != null) {
            usersEmails = user.getEmail();
            userIDs = user.getUid();
            Log.i("TEST CODE", usersEmails);
            userEmail.setText(usersEmails);
            userEmail.setEnabled(false);
        }

        UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = userData.getReadableDatabase();

                String[] selectionArgs = {usersEmails};
                Cursor cursor = db.query("user", null, "email=?", selectionArgs, null, null, null);

                if (cursor.moveToNext()) {
                    String searchEmail = cursor.getString(2);
                    String searchName = cursor.getString(1);
                    String searchMobile = cursor.getString(3);
                    Toast.makeText(getApplicationContext(), searchName, Toast.LENGTH_SHORT).show();
                    userName.setText(searchName);
                    userMobile.setText(searchMobile);
                    userEmail.setText(searchEmail);

                    userName.setEnabled(false);
                    userMobile.setEnabled(false);
                    userEmail.setEnabled(false);

                } else {


                    if (userName.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Type your Full Name", Toast.LENGTH_SHORT).show();
                    } else if (userMobile.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Type your Mobile", Toast.LENGTH_SHORT).show();
                    } else {

                        UserSave();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SQLiteDatabase database = userData.getWritableDatabase();

                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", userName.getText().toString());
                                contentValues.put("email", userEmail.getText().toString());
                                contentValues.put("mobile", userEmail.getText().toString());

                                long id = database.insert("user", null, contentValues);
                                Toast.makeText(getApplicationContext(), "Save Credential", Toast.LENGTH_SHORT).show();

                                Log.i("SIGN UP", String.valueOf(id));
                                userName.setText("");
                                userMobile.setText("");
                                userEmail.setText("");

                            }
                        }).start();

                    }


                }

            }


        });

    }

    private void UserSave() {

        String uName = userName.getText().toString();
        String uEmail = userEmail.getText().toString();
        String uMobile = userMobile.getText().toString();
        String uID = userIDs;

        UserDetails details = new UserDetails(
                uName,
                uEmail,
                uMobile,
                uID
        );

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").add(details).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            public void onSuccess(DocumentReference documentReference) {
                Log.i("EVENT ADD", documentReference.getId());
                Log.i("EVENT ADD", "Success Add Event");
                Toast.makeText(getApplicationContext(), "Success Full Add Event", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("EVENT ADD", e.toString());
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
                "    id     INTEGER PRIMARY KEY\n" +
                "                   NOT NULL,\n" +
                "    name   TEXT,\n" +
                "    email  TEXT,\n" +
                "    mobile TEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // VERSION CHANGE IF THIS METHODE CALL

    }
}


