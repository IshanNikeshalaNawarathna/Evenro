package lk.evenro.even;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lk.evenro.even.model.EventDetails;

public class DetailEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView event_title = findViewById(R.id.event_title);
        TextView event_price = findViewById(R.id.event_price);
        TextView event_location = findViewById(R.id.event_locations);
        TextView event_description = findViewById(R.id.event_description);
        TextView event_organizer_name = findViewById(R.id.organize_name);
        TextView event_date = findViewById(R.id.event_date);

        EventDetails details = (EventDetails) getIntent().getSerializableExtra("event_details");

        if (details != null) {
            event_date.setText(details.getEventDate());
            event_title.setText(details.getEventName());
            event_description.setText(details.getEventDescriptions());
            event_location.setText(details.getEventLocations());
            event_price.setText(details.getPrices());
            event_organizer_name.setText(details.getOrganizerName());
        }

        Button buy_ticket_button = findViewById(R.id.buy_ticket_button);
        buy_ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EventCartActivity.class);
                intent.putExtra("cart_details",details);
                startActivity(intent);
            }
        });

    }
}