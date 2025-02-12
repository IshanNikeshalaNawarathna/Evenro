package lk.evenro.even;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EventCartActivity extends AppCompatActivity {

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
        TextView cart_item_total = findViewById(R.id.cart_item_total);

        ImageButton increment_button = findViewById(R.id.cart_item_increment_button);
        ImageButton decrement_button = findViewById(R.id.cart_item_decrement_button);

    }
}