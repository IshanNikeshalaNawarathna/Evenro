package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.adapter.MyTicketAdapter;
import lk.evenro.even.model.EventDetails;

public class MyTicketActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<EventDetails> fullEventList;
    private ArrayList<EventDetails> eventList;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_ticket);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fullEventList = new ArrayList<>();
        eventList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();

            firebaseFirestore.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");

                                if (name != null) {
                                    Log.w("TEST CODE", "User Name: " + name);
                                    SearchEvent(name);
                                } else {
                                    Log.w("TEST CODE", "User Name is null");
                                }
                            }
                        } else {
                            Log.w("TEST CODE", "No such document");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Error", "Firestore Error: " + e.getMessage());
                    });
        }

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.my_ticket_recycler_view);

    }


    private void SearchEvent(String name) {

        Log.w("test name", name);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("event").whereEqualTo("organizer_name", name).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        fullEventList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                          EventDetails details =  allData(document);
                            fullEventList.add(details);
                        }
                        updateRecyclerView(fullEventList);
                    }
                });


    }
    private void updateRecyclerView(ArrayList<EventDetails> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MyTicketAdapter(list));
    }
    private EventDetails allData(DocumentSnapshot document) {
        String eventID = document.getId();
        Map<String, Object> data = document.getData();
        String eventName = (String) data.get("event_name");
        String eventDescription = (String) data.get("event_description");
        String eventDate = (String) data.get("event_date");
        String eventTime = (String) data.get("event_time");
        String eventPrice = (String) data.get("price");
        String eventCategory = (String) data.get("event_category");
        String eventOrganizerName = (String) data.get("organizer_name");
        String eventLocation = (String) data.get("event_location");
        String eventQty = (String) data.get("qty");
        String eventMobile = (String) data.get("mobile_number");
        String eventImage = (String) data.get("event_image");

        Log.i("EVENT CODE TEST", eventID);

        return new EventDetails(
                eventName, eventLocation, eventDescription, eventPrice, eventCategory, eventQty, eventDate, eventTime, eventOrganizerName, eventID, eventMobile, eventImage
        );

    }
}
