package lk.evenro.even;

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

import com.bumptech.glide.Glide;

import lk.evenro.even.model.Watchlist;

public class WishlistActivity extends AppCompatActivity {


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
//
//        Wishlist wishlistData = new Wishlist(getApplicationContext(), "evenro.dp", null, 1);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SQLiteDatabase database = wishlistData.getReadableDatabase();
//                Cursor cursor = database.rawQuery("SELECT * FROM `wishlist`",null);
//                while (cursor.moveToNext()){
//                    String name =  cursor.getString(0);
//                    Log.i("SIGN UP", name);
//                }
//
//            }
//        }).start();


    }
}
