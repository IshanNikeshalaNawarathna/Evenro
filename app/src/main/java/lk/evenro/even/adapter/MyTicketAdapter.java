package lk.evenro.even.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import lk.evenro.even.BottomSheetFragment;
import lk.evenro.even.DetailEventActivity;
import lk.evenro.even.EventUpdateFragment;
import lk.evenro.even.R;
import lk.evenro.even.model.EventDetails;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {

        //        ImageView event_item_image;
        TextView edite_event_item_title,edite_event_item_date,edite_event_item_location,edite_quntity,edite_event_item_price;

        ImageView edite_profile_image;
        Button edite_event_item_button;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            edite_event_item_title = itemView.findViewById(R.id.edite_event_item_title);
            edite_event_item_date = itemView.findViewById(R.id.edite_event_item_date);
            edite_event_item_location = itemView.findViewById(R.id.edite_event_item_location);
            edite_quntity = itemView.findViewById(R.id.edite_quntity);
            edite_event_item_price = itemView.findViewById(R.id.edite_event_item_price);
            edite_event_item_button = itemView.findViewById(R.id.edite_event_item_button);
            edite_profile_image = itemView.findViewById(R.id.edite_profile_image);


        }
    }

    ArrayList<EventDetails> eventDetails;

    public MyTicketAdapter(ArrayList<EventDetails> eventDetails) {
        this.eventDetails = eventDetails;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_ticket_item, parent, false);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventDetails details = eventDetails.get(position);
        holder.edite_event_item_title.setText(details.getEventName());
        holder.edite_event_item_date.setText(details.getEventDate());
        holder.edite_event_item_price.setText(details.getPrices());
        holder.edite_quntity.setText(details.getQty());
        holder.edite_event_item_location.setText(details.getEventLocations());
        Glide.with(holder.edite_profile_image.getContext())
                .load(Uri.parse(details.getImageUri()))
                .into(holder.edite_profile_image);
        holder.edite_event_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof AppCompatActivity) {
                        AppCompatActivity activity = (AppCompatActivity) context;
                        EventUpdateFragment eventUpdateFragment = new EventUpdateFragment();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        eventUpdateFragment.show(fragmentManager, eventUpdateFragment.getTag());
                        return;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
                Log.e("Fragment Error", "Could not find AppCompatActivity context.");
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventDetails.size();
    }

}
