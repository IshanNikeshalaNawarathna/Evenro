package lk.evenro.even;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import lk.evenro.even.model.EventDetails;

public class DetailEventActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String locationName;

    private ImageButton call_button, message_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView icon = findViewById(R.id.user_email_detail);
        String email = user.getEmail();
        char firstCharUpper = Character.toUpperCase(email.charAt(0));
        icon.setText(String.valueOf(firstCharUpper));


        TextView event_title = findViewById(R.id.event_title);
        TextView event_price = findViewById(R.id.event_price);
        TextView event_location = findViewById(R.id.event_locations);
        TextView event_description = findViewById(R.id.event_description);
        TextView event_organizer_name = findViewById(R.id.organize_name);
        TextView event_date = findViewById(R.id.event_date);
        ImageView event_image = findViewById(R.id.event_detail_image);

        call_button = findViewById(R.id.call_button);
        message_button = findViewById(R.id.message_button);
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);

                        if (cursor.moveToNext()) {
                            String mobile = cursor.getString(3);
                            if (mobile != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                                        Uri uri = Uri.parse("smsto:" + mobile);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Please Inter your user Cradintal", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("TEST CODE GET THE ORGANIZER NAME", mobile);
                        }
                    }
                }).start();

            }
        });

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);

                        if (cursor.moveToNext()) {
                            String mobile = cursor.getString(3);
                            if (mobile != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        Uri uri = Uri.parse("tel:" + mobile);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Please Inter your user Cradintal", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("TEST CODE GET THE ORGANIZER NAME", mobile);
                        }
                    }
                }).start();

            }
        });

        EventDetails details = (EventDetails) getIntent().getSerializableExtra("event_details");

        String latlng = details.getEventLocations();
        Log.i("TEST CODES", latlng);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("locations").whereEqualTo("locationName", latlng)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            if (!documentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = documentSnapshots.get(0);
                                locationName = documentSnapshot.getString("locationLatlng");
                            }
                        }
                    }
                });

        if (details != null) {
            event_date.setText(details.getEventDate());
            event_title.setText(details.getEventName());
            event_description.setText(details.getEventDescriptions());
            event_location.setText(details.getEventLocations());
            event_price.setText(details.getPrices());
            event_organizer_name.setText(details.getOrganizerName());

            Glide.with(this)
                    .load(Uri.parse(details.getImageUri()))
                    .into(event_image);

            Log.i("EVENT CODE TESTS", event_organizer_name.getText().toString());

        }

        event_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapActivity.class);
                intent.putExtra("location", locationName);
                startActivity(intent);

            }
        });

        Button buy_ticket_button = findViewById(R.id.buy_ticket_button);
        buy_ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventCartActivity.class);
                intent.putExtra("cart_details", details);
                startActivity(intent);
            }
        });

    }


}