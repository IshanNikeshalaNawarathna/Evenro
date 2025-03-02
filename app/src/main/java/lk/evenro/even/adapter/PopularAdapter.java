package lk.evenro.even.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import lk.evenro.even.DetailEventActivity;
import lk.evenro.even.R;
import lk.evenro.even.model.EventDetails;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {

        //        ImageView event_item_image;
        TextView popular_event_name;
        TextView popular_event_date;
        TextView popular_event_location;
        Button popular_event_button;
        ImageView popular_image_view;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            popular_event_name = itemView.findViewById(R.id.popular_event_name);
            popular_event_date = itemView.findViewById(R.id.popular_event_date);
            popular_event_location = itemView.findViewById(R.id.popular_event_location);
            popular_event_button = itemView.findViewById(R.id.popular_event_button);
            popular_image_view = itemView.findViewById(R.id.popular_image_view);


        }
    }

    ArrayList<EventDetails> eventDetails;

    public PopularAdapter(ArrayList<EventDetails> eventDetails) {
        this.eventDetails = eventDetails;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.popular_event, parent, false);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventDetails details = eventDetails.get(position);
        holder.popular_event_name.setText(details.getEventName());
        holder.popular_event_date.setText(details.getEventDate());
        holder.popular_event_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Glide.with(holder.popular_image_view.getContext())
                .load(Uri.parse(details.getImageUri()))
                .into(holder.popular_image_view);
        holder.popular_event_button.setOnClickListener(new View.OnClickListener() {
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
