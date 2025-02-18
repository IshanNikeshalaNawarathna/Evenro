package lk.evenro.even;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.evenro.even.model.CloudinaryHelper;
import lk.evenro.even.model.EventDetails;
import lk.evenro.even.model.Location;
import lk.evenro.even.model.SpinnerItem;

public class EventAddActivity extends AppCompatActivity {

    TextView category, location;
    private FirebaseFirestore firebaseFirestore;

    private EditText event_add_mobile_number, event_name, event_time, event_date, event_price, event_qty, event_description;
    private Spinner spinner, location_spinner;
    String categ;
    String loca;
    private ImageView imageView;
    private Uri imageUri;
    private ArrayList<Location> locations = new ArrayList<>();
    Map<String, Object> data;

    private Location selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_add);
        Map config = new HashMap();
        config.put("cloud_name", "dzqpctth7");

        try {
            MediaManager.get();
        } catch (IllegalStateException e) {
            MediaManager.init(getApplicationContext(), config);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageButton date_image_button = findViewById(R.id.date_add_bottom_sheet_button);
        date_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        ImageButton time_image_button = findViewById(R.id.time_bottom_sheet_button);
        time_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBottomSheetFragment timeBottomSheetFragment = new TimeBottomSheetFragment();
                timeBottomSheetFragment.show(getSupportFragmentManager(), timeBottomSheetFragment.getTag());
            }
        });

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),
                        new ActivityResultCallback<Uri>() {
                            @Override
                            public void onActivityResult(Uri uri) {
                                if (uri != null) {
                                    imageUri = uri;
                                    imageView.setImageURI(uri);
                                }
                            }
                        });

        imageView = findViewById(R.id.event_img_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());


            }
        });
        location_spinner = findViewById(R.id.location_spinner);
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

        LocationAdapter locationAdapter = new LocationAdapter(getApplicationContext(), R.layout.custome_spinner_location_item, locations);
        location_spinner.setAdapter(locationAdapter);



        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation =(Location) parent.getItemAtPosition(position);

                loca = selectedLocation.getName();
                Log.i("TEST CODE", loca);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner = findViewById(R.id.event_category_item);

        ArrayList<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(R.drawable.arrow_drop_down, "Select"));
        spinnerItems.add(new SpinnerItem(R.drawable.music, "Music"));
        spinnerItems.add(new SpinnerItem(R.drawable.painting, "Art"));
        spinnerItems.add(new SpinnerItem(R.drawable.tennis_ball, "Sport"));

        SpinnerAdapter arrayAdapter = new SpinnerAdapter(getApplicationContext(), R.layout.custome_spinner_item, spinnerItems);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = view.findViewById(R.id.spinner_items_text);
                categ = (String) category.getText();
                Log.i("TEST CODE", categ);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button add_event_button = findViewById(R.id.event_add_button);
        event_name = findViewById(R.id.add_event_name);
        event_time = findViewById(R.id.add_event_time);
        event_date = findViewById(R.id.add_event_date);
        event_price = findViewById(R.id.add_event_price);
        event_qty = findViewById(R.id.add_event_qty);
        event_description = findViewById(R.id.add_event_description);
        event_add_mobile_number = findViewById(R.id.add_event_mobile_number);


        add_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);

                        if (cursor.moveToNext()) {
                            String name = cursor.getString(1);
                            Log.i("TEST CODE GET THE ORGANIZER NAME", name);
                            addEvent(name);
                        }
                    }
                }).start();


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

    private void addEvent(String name) {
        if (event_name.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Type an Event name");
        } else if (event_add_mobile_number.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Type a Mobile Number");
        } else if (event_time.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Choose your Time");
        } else if (event_date.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Choose your Date");
        } else if (event_price.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Type a Ticket Price");
        } else if (event_qty.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Type a Ticket Quantity");
        } else if (spinner.getSelectedItem() == null) {
            Log.i("EVENT ADD", "Select an Event Category");
        } else if (event_description.getText().toString().trim().isEmpty()) {
            Log.i("EVENT ADD", "Type an Event Description");
        } else {

            String eventName = event_name.getText().toString().trim();
            String eventTime = event_time.getText().toString().trim();
            String eventDate = event_date.getText().toString().trim();
            String eventPrice = event_price.getText().toString().trim();
            String eventQty = event_qty.getText().toString().trim();
            String eventCategory = String.valueOf(categ);
            String eventLocation = loca;
            String eventDescription = event_description.getText().toString().trim();
            String eventmobileNumber = event_add_mobile_number.getText().toString().trim();


            CloudinaryHelper.uploadImage(imageUri, null, new CloudinaryHelper.OnUploadCompleteListener() {
                @Override
                public void onUploadComplete(String url) {

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    HashMap<String, Object> event_data = new HashMap<>();
                    event_data.put("event_name", eventName);
                    event_data.put("organizer_name", name);
                    event_data.put("qty", eventQty);
                    event_data.put("event_date", eventDate);
                    event_data.put("event_time", eventTime);
                    event_data.put("price", eventPrice + ".00");
                    event_data.put("event_category", eventCategory);
                    event_data.put("event_location", eventLocation);
                    event_data.put("event_description", eventDescription);
                    event_data.put("mobile_number", eventmobileNumber);
                    event_data.put("event_image", url);

                    firebaseFirestore.collection("event").add(event_data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i("EVENT ADD", documentReference.getId());
                            Log.i("EVENT ADD", "Success Add Event");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("EVENT ADD", e.toString());
                        }
                    });

                }
            });


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    event_name.setText("");
                    event_date.setText("");
                    event_time.setText("");
                    event_qty.setText("");
                    event_price.setText("");
//                    event_location.setText("");
                    event_description.setText("");
                    spinner.setSelection(0);
                    event_add_mobile_number.setText("");
                    imageView.setImageResource(R.drawable.add_photo_alternate);
                }
            });
        }
    }


}

class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    List<SpinnerItem> spinnerItems;
    int layout;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<SpinnerItem> objects) {
        super(context, resource, objects);
        spinnerItems = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layout, parent, false);
        ImageView imageView = view.findViewById(R.id.spinner_image);
        TextView textView = view.findViewById(R.id.spinner_items_text);

        SpinnerItem spinnerItem = spinnerItems.get(position);
        imageView.setImageResource(spinnerItem.getItemResourceId());
        textView.setText(spinnerItem.getName());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }
}

class LocationAdapter extends ArrayAdapter<Location> {

    List<Location> spinnerItems;
    int layout;

    public LocationAdapter(@NonNull Context context, int resource, @NonNull List<Location> objects) {
        super(context, resource, objects);
        spinnerItems = objects;
        layout = resource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layout, parent, false);
        TextView textView = view.findViewById(R.id.spinner_items_text);
        Location spinnerItem = spinnerItems.get(position);
        textView.setText(spinnerItem.getName());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

}


