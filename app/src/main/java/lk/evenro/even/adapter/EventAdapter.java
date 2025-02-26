package lk.evenro.even.adapter;

import android.content.Intent;
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

import lk.evenro.even.DetailEventActivity;
import lk.evenro.even.R;
import lk.evenro.even.model.EventDetails;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {

        //        ImageView event_item_image;
        TextView event_item_title;
        TextView event_item_date;
        TextView event_item_price;
        TextView event_item_location;
        TextView event_item_button;
        ImageView event_item_image;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            event_item_price = itemView.findViewById(R.id.event_item_price);
            event_item_title = itemView.findViewById(R.id.event_item_title);
            event_item_date = itemView.findViewById(R.id.event_item_date);
            event_item_location = itemView.findViewById(R.id.event_item_location);
            event_item_button = itemView.findViewById(R.id.event_item_button);
            event_item_image = itemView.findViewById(R.id.profile_image);

        }
    }

    ArrayList<EventDetails> eventDetails;

    public EventAdapter(ArrayList<EventDetails> eventDetails) {
        this.eventDetails = eventDetails;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_itme, parent, false);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventDetails details = eventDetails.get(position);
        holder.event_item_title.setText(details.getEventName());
        holder.event_item_date.setText(details.getEventDate());
        holder.event_item_price.setText(details.getPrices());
        holder.event_item_location.setText(details.getEventLocations());
        Glide.with(holder.event_item_image.getContext())
                .load(Uri.parse(details.getImageUri()))
                .into(holder.event_item_image);
        holder.event_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailEventActivity.class);
                intent.putExtra("event_details", details);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }

}
