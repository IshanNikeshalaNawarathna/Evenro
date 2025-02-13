package lk.evenro.even;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lk.evenro.even.model.PaymentEventDetails;

public class TicketViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView event_name = findViewById(R.id.ticket_event_name);
        TextView event_date = findViewById(R.id.start_event_date);
        TextView event_time = findViewById(R.id.event_start_time);
        TextView event_value_date = findViewById(R.id.value_date);
        TextView event_value_qty = findViewById(R.id.ticket_qtys);

        PaymentEventDetails paymentEventDetails = (PaymentEventDetails) getIntent().getSerializableExtra("ticket_details");
        if (paymentEventDetails != null) {
            event_name.setText(paymentEventDetails.getEvent_name());
            event_date.setText(paymentEventDetails.getEvent_date());
            event_time.setText(paymentEventDetails.getEvent_time());
            event_value_date.setText(paymentEventDetails.getEvent_date());
            event_value_qty.setText(paymentEventDetails.getQty());
        }

    }
}