package lk.evenro.even;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

import lk.evenro.even.model.PaymentDetails;
import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;

public class PaymentActivity extends AppCompatActivity {

    private String eventName;
    private int payment_ID;
    private int ticketPrice;
    private int qty;
    private String paymentDate;
    private String buyerEmail;
    private String buyerName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PaymentDetails details = (PaymentDetails) getIntent().getSerializableExtra("payment_details");

        if (details != null) {

            eventName = details.getEvent_name();
            payment_ID = details.getPayment_ID();
            ticketPrice = details.getTicket_price();
            qty = details.getQty();
            paymentDate = details.getPayment_date();
            buyerEmail = details.getBuyer_email();
            buyerName = details.getBuyer_name();

        }




    }


}