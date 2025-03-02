package lk.evenro.even.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import lk.evenro.even.EmaityActivity;
import lk.evenro.even.R;

import lk.evenro.even.WishlistActivity;
import lk.evenro.even.model.Watchlist;
import lk.evenro.even.model.Wishlist;


public class WitchlistAdapter extends RecyclerView.Adapter<WitchlistAdapter.WitchlistViewHolder> {

    ArrayList<Watchlist> watchlists;

    public WitchlistAdapter(ArrayList<Watchlist> watchlists) {
        this.watchlists = watchlists;
    }

    @NonNull
    @Override
    public WitchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.witchlist_item, parent, false);
        WitchlistViewHolder holder = new WitchlistViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WitchlistViewHolder holder, int position) {
        Watchlist watchlist = watchlists.get(position);
        holder.witchlist_event_item_title.setText(watchlist.getEventName());
        holder.witchlist_event_item_date.setText(watchlist.getEventDate());
        holder.witchlist_event_item_price.setText(watchlist.getEventPrice());
        holder.witchlist_event_item_location.setText(watchlist.getEventLoaction());
        Glide.with(holder.witchlist_profile_image.getContext())
                .load(Uri.parse(watchlist.getEventImageUri()))
                .into(holder.witchlist_profile_image);
        holder.witchlist_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(holder.getAdapterPosition(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return watchlists.size();
    }

    class WitchlistViewHolder extends RecyclerView.ViewHolder {

        ImageView witchlist_profile_image, witchlist_delete_button;
        TextView witchlist_event_item_title, witchlist_event_item_date, witchlist_event_item_location, witchlist_event_item_price;


        public WitchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            witchlist_profile_image = itemView.findViewById(R.id.witchlist_profile_image);
            witchlist_delete_button = itemView.findViewById(R.id.witchlist_delete_button);
            witchlist_event_item_title = itemView.findViewById(R.id.witchlist_event_item_title);
            witchlist_event_item_date = itemView.findViewById(R.id.witchlist_event_item_date);
            witchlist_event_item_location = itemView.findViewById(R.id.witchlist_event_item_location);
            witchlist_event_item_price = itemView.findViewById(R.id.witchlist_event_item_price);
        }
    }

    private void deleteItem(int position, View view) {
        if (position >= 0 && position < watchlists.size()) {
            Watchlist itemToRemove = watchlists.get(position);

            // Delete from database
            Wishlist wishlistData = new Wishlist(view.getContext(), "evenro.dp", null, 1);
            SQLiteDatabase database = wishlistData.getWritableDatabase();
            database.delete("wishlist", "eventId = ?", new String[]{itemToRemove.getEventID()});
            database.close();

            // Remove from list and update RecyclerView
            watchlists.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, watchlists.size());

            if (watchlists.isEmpty()) {
                Intent intent = new Intent(view.getContext(), WishlistActivity.class);
                view.getContext().startActivity(intent);
            }


        }
    }

}

