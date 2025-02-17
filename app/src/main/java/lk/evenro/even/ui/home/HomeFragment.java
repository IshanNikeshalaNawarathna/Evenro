package lk.evenro.even.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lk.evenro.even.R;
import lk.evenro.even.SearchActivity;
import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.databinding.FragmentHomeBinding;
import lk.evenro.even.model.EventDetails;

public class HomeFragment extends Fragment {
    RecyclerView categoryRecyclerView,musicRecyclerView;
    private FragmentHomeBinding binding;
    ArrayList<EventDetails> fullEventList;
    ArrayList<EventDetails> fullEventLists;
    EventDetails details;

    private List<EventDetails> eventDetail = new ArrayList<>();
    Map<String, Object> data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        categoryRecyclerView = view.findViewById(R.id.event_category_recycler_view);
        fullEventList = new ArrayList<>();
        fullEventLists = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();




        firebaseFirestore.collection("event")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        eventDetail.clear(); // Clear the list before adding new data
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            Log.i("Event details", snapshot.getData().toString());
                            loadEventDetailsObject(snapshot);
                        }
                        updateRecyclerView(fullEventList);

                    } else {
                        Log.e("Popular Event load", "Error getting documents: ", task.getException());
                    }
                });


        TextView popluar_event_all_button = view.findViewById(R.id.popular_event_view_all_button);
        popluar_event_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });



        ImageView search_button = view.findViewById(R.id.search_image_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
    private void updateRecyclerView(ArrayList<EventDetails> list) {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        categoryRecyclerView.setAdapter(new EventAdapter(list));
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}