package lk.evenro.even;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import lk.evenro.even.model.EventDetails;

public class WishlistActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView event_name, event_date, event_location, event_price, event_description, event_organizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("lk.evenro.even.data", Context.MODE_PRIVATE);
        String wishlist_data = sharedPreferences.getString("wishlist_data", null);

        Gson gson = new Gson();
        EventDetails wishlistData = gson.fromJson(wishlist_data, EventDetails.class);

        Log.i("WISHLIST TEST", wishlistData.getEventName());

    }
}