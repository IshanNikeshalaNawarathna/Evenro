package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import lk.evenro.even.model.EventDetails;

public class EventCartActivity extends AppCompatActivity {
    TextView cart_item_total;

    private int count = 0;

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

        EventDetails details = (EventDetails) getIntent().getSerializableExtra("cart_details");


        if (details != null) {
            try {
                String event_name = details.getEventName();
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
                        String typeQty = cart_item_type_qty.getText().toString();
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
                        String typeQty = cart_item_type_qty.getText().toString();
                        cart_item_new_qty.setText(String.valueOf(typeQty).toString());
                        TicketPriceCale(typeQty, event_price);
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void TicketPriceCale(String qty, int event_price) {

        String newQty = qty;
        int newQtyInt = Integer.valueOf(newQty);
        int totalPrice = newQtyInt * event_price;

        cart_item_total.setText(String.valueOf(totalPrice));
    }

}