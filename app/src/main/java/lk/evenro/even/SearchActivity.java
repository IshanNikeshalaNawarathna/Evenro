package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.model.EventDetails;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<EventDetails> eventList;
    ArrayList<EventDetails> fullEventList;
    Map<String, Object> data;
    EventDetails details;
    EditText search_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.search_item_recycle_view);
        search_text = findViewById(R.id.cart_item_type_qty);

        fullEventList = new ArrayList<>();
        eventList = new ArrayList<>();
        loadAllEvents();
        FilterChip();


        ImageButton search_button = findViewById(R.id.search_all_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_item_text = search_text.getText().toString().trim();
                Log.d("SearchDebug", "Searching for: " + search_item_text);

                if (search_item_text.isEmpty()) {
                    updateRecyclerView(fullEventList);
                } else {

                    FirebaseFirestore searchFirestore = FirebaseFirestore.getInstance();
                    Query query = searchFirestore.collection("event")
                            .whereEqualTo("event_name", search_item_text);
                    Log.d("SearchDebug", "Query: " + query.toString());

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    Log.d("SearchDebug", "Empty result");
                                }

                                eventList.clear();
                                for (DocumentSnapshot document : task.getResult()) {
                                    loadEventDetailsObject(document);
                                }

                                updateRecyclerView(eventList);
                                search_text.setText("");
                            }
                        }
                    });
                }
            }
        });


    }

    private void loadAllEvents() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("event").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    fullEventList.clear();
                    for (DocumentSnapshot document : task.getResult()) {

                        loadEventDetailsObject(document);
                    }
                    updateRecyclerView(fullEventList);
                    search_text.setText("");
                }
            }
        });
    }

    private void updateRecyclerView(ArrayList<EventDetails> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new EventAdapter(list));
    }


    private void FilterChip() {
        Chip music_chip = findViewById(R.id.chip_music);
        Chip art_chip = findViewById(R.id.chip_art);
        Chip sport_chip = findViewById(R.id.chip_sport);

        CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    String category = "";
                    int chipId = buttonView.getId();
                    if (chipId == R.id.chip_music) {
                        category = "Music";
                        Log.i("TEST CODE", category);
                    } else if (chipId == R.id.chip_art) {
                        category = "Art";
                        Log.i("TEST CODE", category);
                    } else if (chipId == R.id.chip_sport) {
                        category = "Sport";
                        Log.i("TEST CODE", category);
                    }

                    if (category.isEmpty()) {
                        loadAllEvents();
                    } else {
                        LoadingChipCategory(category);
                    }
                } else {
                    updateRecyclerView(fullEventList);
                }
            }
        };

        music_chip.setOnCheckedChangeListener(changeListener);
        art_chip.setOnCheckedChangeListener(changeListener);
        sport_chip.setOnCheckedChangeListener(changeListener);

    }

    private void LoadingChipCategory(String category) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("event")
                .whereEqualTo("event_category", category);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    eventList.clear();
                    for (DocumentSnapshot document : task.getResult()) {
                        loadEventDetailsObject(document);
                    }
                    eventList.clear();
                    updateRecyclerView(fullEventList);
                    search_text.setText("");
                }
            }
        });

    }


    private void loadEventDetailsObject(DocumentSnapshot document) {
        String eventID = document.getId();
        data = document.getData();
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

        details = new EventDetails(eventName, eventLocation, eventDescription, eventPrice, eventCategory, eventQty, eventDate, eventTime, eventOrganizerName, eventID,eventMobile,eventImage);
        fullEventList.add(details);
    }

}