package lk.evenro.even.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import lk.evenro.even.R;
import lk.evenro.even.SearchActivity;
import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.adapter.PopularAdapter;
import lk.evenro.even.databinding.FragmentHomeBinding;
import lk.evenro.even.model.EventDetails;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private List<EventDetails> eventDetails = new ArrayList<>();
    private PopularAdapter popularAdapter;
    private EventAdapter eventAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.popular_event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        popularAdapter = new PopularAdapter((ArrayList<EventDetails>) eventDetails);
        recyclerView.setAdapter(popularAdapter);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("event")
                .whereEqualTo("event_category","Music")
                .whereEqualTo("price", "1000")
                .whereEqualTo("price", "5000")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        eventDetails.clear(); // Clear the list before adding new data
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            EventDetails details = snapshot.toObject(EventDetails.class);
                            if (details != null) {
                                eventDetails.add(details);
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(() -> {
                            popularAdapter.notifyDataSetChanged();
                        });
                    } else {
                        Log.e("Popular Event load", "Error getting documents: ", task.getException());
                    }
                });


        firebaseFirestore.collection("event")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        eventDetails.clear(); // Clear the list before adding new data
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            EventDetails details = snapshot.toObject(EventDetails.class);
                            if (details != null) {
                                eventDetails.add(details);
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(() -> {
                            popularAdapter.notifyDataSetChanged();
                        });
                    } else {
                        Log.e("Popular Event load", "Error getting documents: ", task.getException());
                    }
                });

        recyclerView = view.findViewById(R.id.event_category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        eventAdapter = new EventAdapter((ArrayList<EventDetails>) eventDetails);
        recyclerView.setAdapter(eventAdapter);


        TextView popluar_event_all_button = view.findViewById(R.id.popular_event_view_all_button);
        popluar_event_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        TextView category_event_all_button = view.findViewById(R.id.category_view_all_button);
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}