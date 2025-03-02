package lk.evenro.even;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;

import lk.evenro.even.model.UserDetails;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userName, userEmail, userMobile;
    String usersEmails, userIDs;
    private Uri imageUri;
    private ImageView imageView;

    private  SharedPreferences sharedPreferences;


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

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getSharedPreferences("lk.evenro.even.data", Context.MODE_PRIVATE);
                String uData = sharedPreferences.getString("userData", null);

                Gson gson = new Gson();
                UserDetails userDetails = gson.fromJson(uData, UserDetails.class);

                if(userDetails !=null){
                    userName.setText(userDetails.getName());
                    userMobile.setText(userDetails.getMobile());
                    userEmail.setText(userDetails.getEmail());
                }else{

                    if(userName.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();
                    }else if(userMobile.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                    }else{
                        UserDetails  user = new UserDetails(
                                userName.getText().toString(),
                                userMobile.getText().toString(),
                                userEmail.getText().toString(),
                                userIDs
                        );
                        Gson userGson = new Gson();
                        String userDate = userGson.toJson(user);

                        sharedPreferences= getSharedPreferences("lk.evenro.even.data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userData", userDate);
                        editor.apply();

                        UserSave();

                    }

                }

            }


        });

    }

    private void UserSave() {

        UserDetails details = new UserDetails(
                userName.getText().toString(),
                userMobile.getText().toString(),
                userEmail.getText().toString(),
                userIDs
        );

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").add(details).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            public void onSuccess(DocumentReference documentReference) {
                Log.i("EVENT ADD", documentReference.getId());
                Log.i("EVENT ADD", "Success Add Event");
                Toast.makeText(getApplicationContext(), "Success Full Add to Credential", Toast.LENGTH_SHORT).show();
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



