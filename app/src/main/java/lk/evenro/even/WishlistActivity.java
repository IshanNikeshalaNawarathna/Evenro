package lk.evenro.even;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import lk.evenro.even.model.EventDetails;

public class WishlistActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView event_name, event_date, event_location, event_price, event_description, event_organizer;
    private ImageButton deleteWishlist;
    private String wishlist_data;

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



    }

    @Override
    protected void onStart() {
        super.onStart();

        event_name = findViewById(R.id.wishlist_event_titale);
        event_date = findViewById(R.id.wishlist_event_date);
        event_location = findViewById(R.id.wishlist_event_location);
        event_price = findViewById(R.id.wishlist_event_ticket_price);
        event_description = findViewById(R.id.wishlist_event_description);
        event_organizer = findViewById(R.id.wishlist_oragnaizer_name);
        imageView = findViewById(R.id.wishlist_image);
        deleteWishlist = findViewById(R.id.wishlist_delete_button);

        SharedPreferences sharedPreferences = getSharedPreferences("lk.evenro.even.data", Context.MODE_PRIVATE);
        wishlist_data = sharedPreferences.getString("wishlist_data", null);

        Gson gson = new Gson();
        EventDetails wishlistData = gson.fromJson(wishlist_data, EventDetails.class);

        if (wishlistData != null) {
            event_name.setText(wishlistData.getEventName());
            event_location.setText(wishlistData.getEventLocations());
            event_description.setText(wishlistData.getEventDescriptions());
            event_date.setText(wishlistData.getEventDate());
            event_organizer.setText(wishlistData.getOrganizerName());
            Glide.with(this).load(Uri.parse(wishlistData.getImageUri())).into(imageView);
            event_price.setText(wishlistData.getPrices());
        }else{
            Intent intent = new Intent(WishlistActivity.this,SearchActivity.class);
            startActivity(intent);
        }

        deleteWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("wishlist_data");
                editor.apply();
                Intent intent = new Intent(WishlistActivity.this, EmaityActivity.class);
                startActivity(intent);
            }
        });

    }
}