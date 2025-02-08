package lk.evenro.even;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import lk.evenro.even.model.SpinnerItem;

public class EventAddActivity extends AppCompatActivity {

    TextView category;
    String categ;
    String imageUrl;

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

