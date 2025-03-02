package lk.evenro.even;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import lk.evenro.even.adapter.MyTicketAdapter;
import lk.evenro.even.adapter.WitchlistAdapter;
import lk.evenro.even.model.EventDetails;
import lk.evenro.even.model.Watchlist;
import lk.evenro.even.model.Wishlist;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Watchlist> fullEventList;


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
        fullEventList = new ArrayList<>();

        Wishlist wishlistData = new Wishlist(getApplicationContext(), "evenro.dp", null, 1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase database = wishlistData.getReadableDatabase();
                Cursor cursor = database.rawQuery("SELECT * FROM `wishlist`",null);
                while (cursor.moveToNext()){
                    String eventId =  cursor.getString(0);
                    String eventName =  cursor.getString(1);
                    String eventLocation =  cursor.getString(2);
                    String eventPrice =  cursor.getString(3);
                    String eventDate =  cursor.getString(4);
                    String eventImage =  cursor.getString(5);

                    Watchlist watchlist = new Watchlist(eventId,eventName,eventLocation,eventPrice,eventDate,eventImage);
                    fullEventList.add(watchlist);
                }
                cursor.close();
                database.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateRecyclerView(fullEventList);
                    }
                });

            }
        }).start();

        recyclerView = findViewById(R.id.witchlist_recycler_view);

    }
    private void updateRecyclerView(ArrayList<Watchlist> list) {
        if (list.isEmpty()) {
            Intent intent = new Intent(WishlistActivity.this, EmaityActivity.class);
            startActivity(intent);
            finish();
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new WitchlistAdapter(list));
        }
    }

}
