package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;

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

//        ArrayList<EventDetails> data = new ArrayList<>();
//        data.add(new EventDetails("Music", "ABC", "hello", "200", "hello", "10", "2025-02-01", "8.00am"));


        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("event").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    ArrayList<EventDetails> eventList = new ArrayList<>();

                    for (DocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = document.getData();
                        Log.i("TEST CODE", document.getData().toString());
                        String eventName = (String) data.get("event_name");
                        String eventDescription = (String) data.get("event_description");
                        String eventDate = (String) data.get("event_date");
                        String eventTime = (String) data.get("event_time");
                        String eventPrice = (String) data.get("price");
                        String eventCategory = (String) data.get("event_category");
                      //  String eventOrganizerName = (String) data.get("Organizer_name");
                        String eventLocation = (String) data.get("event_location");
                        String eventQty = (String) data.get("qty");

                        EventDetails details = new EventDetails(eventName,eventLocation,eventDescription,eventPrice,eventCategory,eventQty,eventDate,eventTime);
                        eventList.add(details);


                    }

                            RecyclerView recyclerView = findViewById(R.id.search_item_recycle_view);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(new EventAdapter(eventList));


                }

            }
        });

    }
}