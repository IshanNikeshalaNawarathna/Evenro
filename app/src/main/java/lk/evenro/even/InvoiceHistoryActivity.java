package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lk.evenro.even.model.PaymentEventDetails;

public class InvoiceHistoryActivity extends AppCompatActivity {

    private PaymentEventDetails paymentEventDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        paymentEventDetails = (PaymentEventDetails) getIntent().getSerializableExtra("payment_details");

        if (paymentEventDetails != null) {
            String eventName = paymentEventDetails.getEvent_name();
            String eventqty = paymentEventDetails.getQty();
            String buyerEmail = paymentEventDetails.getBuyer_email();
            String buyerName = paymentEventDetails.getBuyer_name();
            String paymentID = paymentEventDetails.getPayment_ID();
            String paymentDate = paymentEventDetails.getPayment_date();
            String paymentPrice = paymentEventDetails.getTicket_price();
            Log.i("TEST CODE", eventName);
            Log.i("TEST CODE", eventqty);
            Log.i("TEST CODE", buyerName);
            Log.i("TEST CODE", buyerEmail);
            Log.i("TEST CODE", paymentID);
            Log.i("TEST CODE", paymentDate);
            Log.i("TEST CODE", paymentPrice);
        } else {

            Log.i("TEST CODE", "NULL DATA");

        }


    }
}