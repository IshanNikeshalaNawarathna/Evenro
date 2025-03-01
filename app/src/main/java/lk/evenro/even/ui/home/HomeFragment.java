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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lk.evenro.even.R;
import lk.evenro.even.SearchActivity;
import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.databinding.FragmentHomeBinding;
import lk.evenro.even.model.EventDetails;

public class HomeFragment extends Fragment {
    RecyclerView categoryRecyclerView, musicRecyclerView;
    private FragmentHomeBinding binding;
    ArrayList<EventDetails> fullEventList;
    ArrayList<EventDetails> fullEventLists;
    EventDetails details;

    private List<EventDetails> eventDetail = new ArrayList<>();
    Map<String, Object> data;
    private FirebaseAuth mAuth;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        TextView user_email_home = getView().findViewById(R.id.user_email_home);

        TextView icon = getView().findViewById(R.id.icon_images_homes);
        String email = user.getEmail();
        char firstCharUpper = Character.toUpperCase(email.charAt(0));
        icon.setText(String.valueOf(firstCharUpper));
        user_email_home.setText(user.getEmail());


        categoryRecyclerView = getView().findViewById(R.id.event_category_recycler_view);
        fullEventList = new ArrayList<>();
        fullEventLists = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("event")
                .orderBy("event_date", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        eventDetail.clear();
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            Log.i("Event details", snapshot.getData().toString());
                            String eventID = snapshot.getId();
                            data = snapshot.getData();
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

                            details = new EventDetails(eventName, eventLocation, eventDescription, eventPrice, eventCategory, eventQty, eventDate, eventTime, eventOrganizerName, eventID, eventMobile, eventImage);
                            fullEventList.add(details);
                        }
                        updateRecyclerView(fullEventList);

                    } else {
                        Log.e("Popular Event load", "Error getting documents: ", task.getException());
                    }
                });


        TextView popluar_event_all_button = getView().findViewById(R.id.popular_event_view_all_button);
        popluar_event_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        ImageView search_button = getView().findViewById(R.id.search_image_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    private void updateRecyclerView(ArrayList<EventDetails> list) {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        categoryRecyclerView.setAdapter(new EventAdapter(list));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}