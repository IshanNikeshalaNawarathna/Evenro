package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.model.EventDetails;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<EventDetails> fullEventList;
    private ArrayList<EventDetails> eventList;
    private EditText searchEditText;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.search_item_recycle_view);
        searchEditText = findViewById(R.id.cart_item_type_qty);

        fullEventList = new ArrayList<>();
        eventList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();

        loadAllEvents();

        ImageButton searchButton = findViewById(R.id.search_all_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim();
                Log.d("SearchDebug", "Searching for: " + searchText);
                performSearch(searchText);
            }
        });


    }

    private void loadAllEvents() {
        firestore.collection("event")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            fullEventList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                EventDetails event = loadEventDetailsObject(document);
                                fullEventList.add(event);
                            }
                            updateRecyclerView(fullEventList);
                            searchEditText.setText("");
                        } else {
                            Log.e("AllEventActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            updateRecyclerView(fullEventList);
        } else {
            searchEvents(searchText);
        }
    }

    private void searchEvents(String searchText) {
        String queryText = searchText.toLowerCase(Locale.getDefault());
        eventList.clear();
        for (EventDetails event : fullEventList) {
            if (event.getEventName().toLowerCase(Locale.getDefault()).contains(queryText) ||
                    event.getEventCategory().toLowerCase(Locale.getDefault()).contains(queryText) ||
                    event.getOrganizerName().toLowerCase(Locale.getDefault()).contains(queryText) ||
                    event.getEventLocations().toLowerCase(Locale.getDefault()).contains(queryText)) {
                eventList.add(event);
            }
        }
        updateRecyclerView(eventList);
    }

    private void updateRecyclerView(ArrayList<EventDetails> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EventAdapter(list));
    }


    private EventDetails loadEventDetailsObject(DocumentSnapshot document) {
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