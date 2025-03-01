package lk.evenro.even;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lk.evenro.even.model.CustomAlert;
import lk.evenro.even.model.Location;

public class EventUpdateFragment extends BottomSheetDialogFragment {
    private Spinner location_spinner;
    private ArrayList<Location> locations = new ArrayList<>();
    private Location selectedLocation;
    private String loca, date, time;
    private Map<String, Object> data;
    private FirebaseFirestore firebaseFirestore;
    private String eventId;
    private TextView dateText, timeText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            eventId = getArguments().getString("eventId");
        }

        View view = inflater.inflate(R.layout.fragment_event_update, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dateText = getView().findViewById(R.id.edite_event_date);

        ImageView date_picker = getView().findViewById(R.id.pick_date_button);
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlert.showDatePickerDialog(requireContext(), new CustomAlert.DatePickerListener() {
                    @Override
                    public void onDateSelected(String selectedDate) {

                        dateText.setText(selectedDate);
                        date = selectedDate;
                        Log.d("new test code", date);
                    }
                });
            }
        });
        timeText = getView().findViewById(R.id.edite_event_time);

        ImageView time_picker_button = getView().findViewById(R.id.time_pick_button);
        time_picker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlert.showTimePickerDialog(requireContext(), new CustomAlert.TimePickerListener() {
                    @Override
                    public void onTimeSelected(String selectedTime) {
                        timeText.setText(selectedTime);
                        time = selectedTime;
                    }
                });
            }
        });

        location_spinner = getView().findViewById(R.id.location_spinner);
        locations.add(new Location("Select Location", "0"));
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("locations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    locations.clear();

                    for (DocumentSnapshot document : task.getResult()) {
                        loadLocations(document);
                    }

                }
            }
        });

        LocationAdapter locationAdapter = new LocationAdapter(getContext(), R.layout.custome_spinner_location_item, locations);
        location_spinner.setAdapter(locationAdapter);


        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = (Location) parent.getItemAtPosition(position);
                loca = selectedLocation.getName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button update_event_button = getView().findViewById(R.id.update_event_button);
        update_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDate(date, time, loca, eventId);
                dismiss();
            }
        });

    }

    private void loadLocations(DocumentSnapshot document) {
        String eventID = document.getId();
        data = document.getData();
        String locationName = (String) data.get("locationName");
        String locationLatlng = (String) data.get("locationLatlng");


        Log.i("EVENT CODE TEST", eventID);

        selectedLocation = new Location(locationName, locationLatlng);
        locations.add(selectedLocation);
    }

    private void UpdateDate(String date, String time, String loca, String eventId) {
        HashMap<String, Object> event_data = new HashMap<>();
        event_data.put("event_date", date);
        event_data.put("event_time", time);
        event_data.put("event_location", loca);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("event")
                .document(eventId)
                .update(event_data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("TEST CODE", "Success");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getContext(), EventUpdateFragment.class);
                                startActivity(intent);
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TEST CODE", "Error" + e.getMessage());
                    }
                });

        timeText.setText("");
        dateText.setText("");
        dateText.setText("");
        location_spinner.setSelection(0);
    }

}
