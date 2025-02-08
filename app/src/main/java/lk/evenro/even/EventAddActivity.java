package lk.evenro.even;

import android.content.Context;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.evenro.even.model.SpinnerItem;

public class EventAddActivity extends AppCompatActivity {

    TextView category;
    String categ;
    String imageUrl;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton date_image_button = findViewById(R.id.date_add_bottom_sheet_button);
        date_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment  bottomSheetFragment = new BottomSheetFragment();
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
        ImageView imageView = findViewById(R.id.event_img_view);
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                imageView.setImageURI(uri);
                imageUrl = uri.toString();
                Log.i("ABC", imageUrl);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });
        ImageButton selectImge = findViewById(R.id.image_select_button);
        selectImge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });


        Spinner spinner = findViewById(R.id.event_category_item);

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
                category = view.findViewById(R.id.spinner_item_text);
                categ = (String) category.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button add_event_button = findViewById(R.id.event_add_button);
        EditText event_name = findViewById(R.id.add_event_name);
        EditText event_time = findViewById(R.id.add_event_time);
        EditText event_date = findViewById(R.id.add_event_date);
        EditText event_price = findViewById(R.id.add_event_price);
        EditText event_qty = findViewById(R.id.add_event_qty);
        EditText event_location = findViewById(R.id.add_event_location);
        EditText event_description = findViewById(R.id.add_event_description);


        add_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUrl == null) {
                    Log.i("EVENT ADD", "Select an Image");
                } else if (event_name.getText().toString().trim().isEmpty()) {
                    Log.i("EVENT ADD", "Type an Event name");
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
                } else if (event_location.getText().toString().trim().isEmpty()) {
                    Log.i("EVENT ADD", "Type a Location");
                } else if (event_description.getText().toString().trim().isEmpty()) {
                    Log.i("EVENT ADD", "Type an Event Description");
                } else {

                    String eventName = event_name.getText().toString().trim();
                    String eventTime = event_time.getText().toString().trim();
                    String eventDate = event_date.getText().toString().trim();
                    String eventPrice = event_price.getText().toString().trim();
                    String eventQty = event_qty.getText().toString().trim();
                    String eventCategory = String.valueOf(categ);
                    String eventLocation = event_location.getText().toString().trim();
                    String eventDescription = event_description.getText().toString().trim();

                    // FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                    HashMap<String, Object> event_data = new HashMap<>();
                    event_data.put("event_name", eventName);
                    event_data.put("organizer_name", "Ishan Nikeshala Nawarathna");
                    event_data.put("qty", eventQty);
                    event_data.put("event_date", eventDate);
                    event_data.put("event_time", eventTime);
                    event_data.put("price", "Rs." + eventPrice + ".00");
                    event_data.put("event_category", eventCategory);
                    event_data.put("event_location", eventLocation);
                    event_data.put("event_description", eventDescription);
                    event_data.put("event_image", imageUrl);

//                    firebaseFirestore.collection("event").add(event_data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.i("EVENT ADD", documentReference.getId());
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.i("EVENT ADD", e.toString());
//                        }
//                    });

                    event_name.setText("");
                    event_date.setText("");
                    event_time.setText("");
                    event_qty.setText("");
                    event_price.setText("");
                    event_location.setText("");
                    event_description.setText("");
                    spinner.setSelection(0);
                }
            }
        });

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
        TextView textView = view.findViewById(R.id.spinner_item_text);

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

