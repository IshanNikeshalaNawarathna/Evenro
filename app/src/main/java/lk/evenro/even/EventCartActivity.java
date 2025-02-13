package lk.evenro.even;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lk.evenro.even.model.EventDetails;
import lk.evenro.even.model.PaymentEventDetails;
import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;


public class EventCartActivity extends AppCompatActivity {
    TextView cart_item_total;
    private int totalPrice;
    private int count = 0;
    private String event_name;
    private int code;

    private String typeQty;

    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_location), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView cart_item_title = findViewById(R.id.cart_item_title);
        TextView cart_item_category = findViewById(R.id.cart_itme_category);
        TextView cart_item_price = findViewById(R.id.cart_item_price);
        TextView cart_item_new_qty = findViewById(R.id.cart_item_new_qty);
        TextView cart_item_type_qty = findViewById(R.id.cart_item_type_qty);
        cart_item_total = findViewById(R.id.cart_item_total_price);


        ImageButton increment_button = findViewById(R.id.cart_item_increment_button);
        ImageButton decrement_button = findViewById(R.id.cart_item_decrement_button);
        Button checkout_button = findViewById(R.id.cart_checkout_button);

        EventDetails details = (EventDetails) getIntent().getSerializableExtra("cart_details");


        if (details != null) {
            try {
                event_name = details.getEventName();
                String event_category = details.getEventCategory();
                int event_price = (int) Double.parseDouble(details.getPrices());
                int event_qty = Integer.valueOf(details.getQty());

                cart_item_title.setText(event_name);
                cart_item_category.setText(event_category);
                cart_item_price.setText(String.valueOf(event_price));


                increment_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count < event_qty) count++;
                        Log.i("TEST CODE", String.valueOf(Integer.valueOf(count)));
                        cart_item_type_qty.setText(String.valueOf(count));
                        typeQty = cart_item_type_qty.getText().toString();
                        cart_item_new_qty.setText(String.valueOf(typeQty).toString());
                        TicketPriceCale(typeQty, event_price);
                    }
                });

                decrement_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count <= 0) count = 0;
                        else count--;
                        Log.i("TEST CODE", String.valueOf(Integer.valueOf(count)));
                        cart_item_type_qty.setText(String.valueOf(count));
                        typeQty = cart_item_type_qty.getText().toString();
                        cart_item_new_qty.setText(String.valueOf(typeQty).toString());
                        TicketPriceCale(typeQty, event_price);
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = (int) (Math.random() * 1000);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = simpleDateFormat.format(new Date());

                UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);

                        if (cursor.moveToNext()) {
                            String name = cursor.getString(1);
                            String email = cursor.getString(2);
                            paymentMethod(name, email);
                            InvoicePayment(code, event_name, name, email, totalPrice, date, typeQty);
                        }

                    }
                }).start();

            }
        });
    }

    private final ActivityResultLauncher<Intent> paymentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    if (data.hasExtra(PHConstants.INTENT_EXTRA_DATA)) {
                        Serializable serializable = data.getSerializableExtra(PHConstants.INTENT_EXTRA_DATA);
                        if (serializable instanceof PHResponse) {
                            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) serializable;
                            if (response.isSuccess()) {
                                Log.i("Payment Message", "Payment Success");

                            } else {
                                Log.i("Payment Message", "Payment Failed" + response);
                            }
                        }

                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.i("Payment Message", "Payment Cancelled");
                }
            }
    );

    private void TicketPriceCale(String qty, int event_price) {

        String newQty = qty;
        int newQtyInt = Integer.valueOf(newQty);
        totalPrice = newQtyInt * event_price;

        cart_item_total.setText(String.valueOf(totalPrice));
    }

    private void paymentMethod(String name, String email) {
        InitRequest req = new InitRequest();
        req.setMerchantId("1222107");       // Merchant ID
        req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
        req.setAmount(totalPrice);             // Final Amount to be charged
        req.setOrderId(String.valueOf(code));        // Unique Reference ID
        req.setItemsDescription(event_name);  // Item description title
        req.setCustom1("This is the custom message 1");
        req.setCustom2("This is the custom message 2");
        req.getCustomer().setFirstName(name);
        req.getCustomer().setLastName(name);
        req.getCustomer().setEmail(email);
        req.getCustomer().setPhone("+94771234567");
        req.getCustomer().getAddress().setAddress("No.1, Galle Road");
        req.getCustomer().getAddress().setCity("Colombo");
        req.getCustomer().getAddress().setCountry("Sri Lanka");

//Optional Params
        req.setNotifyUrl(null);           // Notifiy Url
        req.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
        req.getCustomer().getDeliveryAddress().setCity("Kadawatha");
        req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
        req.getItems().add(new Item(String.valueOf(code), event_name, Integer.valueOf(typeQty), Double.valueOf(totalPrice)));

        Intent intent = new Intent(getApplicationContext(), PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
        paymentLauncher.launch(intent);
    }

    private void InvoicePayment(int payment_id, String event_name, String buyer_name, String buyer_email, int ticket_price, String payment_date, String qty) {

        String payment_ID = String.valueOf(payment_id);
        String ticket_qty = String.valueOf(qty);
        String event_price = String.valueOf(ticket_price);

        PaymentEventDetails paymentEventDetails = new PaymentEventDetails(
                payment_ID,
                ticket_qty,
                event_name,
                buyer_name,
                buyer_email,
                event_price,
                payment_date
        );
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("invoice").add(paymentEventDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i("Payment Success", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Payment Error", e.toString());
            }
        });

    }

}