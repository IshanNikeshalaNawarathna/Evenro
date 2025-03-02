package lk.evenro.even;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lk.evenro.even.model.EventDetails;
import lk.evenro.even.model.PaymentEventDetails;
import lk.evenro.even.model.UserDetails;
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
    private int code;
    private String date, event_date, event_time, eventID, typeQty, event_name,name,email,eventImage;
    private int event_qty;
    private static final int PAYHERE_REQUEST = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_view), (v, insets) -> {
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
                event_qty = Integer.valueOf(details.getQty());
                event_date = details.getEventDate();
                event_time = details.getEventTime();
                eventID = details.getEventID();
                eventImage = details.getImageUri();


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

        Log.i("TEST CODES EVENT ID", eventID);

        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = (int) (Math.random() * 1000);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = simpleDateFormat.format(new Date());
                paymentMethod();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("lk.evenro.even.data", Context.MODE_PRIVATE);
        String uData = sharedPreferences.getString("userData", null);

        Gson gson = new Gson();
        UserDetails user = gson.fromJson(uData, UserDetails.class);

        if (user != null) {
            name = user.getName();
            email = user.getEmail();
        } else {
            Toast.makeText(getApplicationContext(), "Please Inter your user Cradintal", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess())
                        InvoicePayment(code, event_name, name,email, totalPrice, date, typeQty, event_date, event_time, eventID,eventImage);
                    else
                        Toast.makeText(EventCartActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                else
                  Toast.makeText(EventCartActivity.this,"Result: no response",Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null)
                    Toast.makeText(EventCartActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EventCartActivity.this,"User canceled the request",Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void TicketPriceCale(String qty, int event_price) {

        String newQty = qty;
        int newQtyInt = Integer.valueOf(newQty);
        totalPrice = newQtyInt * event_price;

        cart_item_total.setText(String.valueOf(totalPrice));
    }

    private void paymentMethod() {
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
        startActivityForResult(intent, PAYHERE_REQUEST);
    }

    private void InvoicePayment(int payment_id, String event_name, String buyer_name, String buyer_email, int ticket_price, String payment_date, String qty, String event_date, String event_time, String eventID,String eventImage) {

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
                payment_date,
                event_date,
                event_time,
                eventID,
                eventImage


        );
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("invoice").add(paymentEventDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Payment Success", documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_SHORT).show();

                        qtyUpdate(ticket_qty);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Payment Error", e.toString());
                        Toast.makeText(getApplicationContext(), "Payment Error" + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void qtyUpdate(String ticket_qty) {
        int newQtyEvent = event_qty - Integer.valueOf(ticket_qty);
        String newEventQty = String.valueOf(newQtyEvent);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("event").document(eventID).update("qty", newEventQty)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("Event Update Success", "Update Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Event Update Error", "Update Error" + e.toString());
                    }
                });

    }

}