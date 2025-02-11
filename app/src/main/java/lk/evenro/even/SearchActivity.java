package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadChipData();


        recyclerView = findViewById(R.id.search_item_recycle_view);
        search_text = findViewById(R.id.search_text);

        fullEventList = new ArrayList<>();
        eventList = new ArrayList<>();
        loadAllEvents();

        ImageButton search_button = findViewById(R.id.search_all_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_item_text = search_text.getText().toString().trim();
                Log.d("SearchDebug", "Searching for: " + search_item_text);

                if (search_item_text.isEmpty()) {
                    loadAllEvents();
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

    private void loadChipData(){
        
    }

    private void loadEventDetailsObject(DocumentSnapshot document) {
        data = document.getData();
        String eventName = (String) data.get("event_name");
        String eventDescription = (String) data.get("event_description");
        String eventDate = (String) data.get("event_date");
        String eventTime = (String) data.get("event_time");
        String eventPrice = (String) data.get("price");
        String eventCategory = (String) data.get("event_category");
        String eventOrganizerName = (String) data.get("Organizer_name");
        String eventLocation = (String) data.get("event_location");
        String eventQty = (String) data.get("qty");

        details = new EventDetails(eventName, eventLocation, eventDescription, eventPrice, eventCategory, eventQty, eventDate, eventTime, eventOrganizerName);
        fullEventList.add(details);
    }

}