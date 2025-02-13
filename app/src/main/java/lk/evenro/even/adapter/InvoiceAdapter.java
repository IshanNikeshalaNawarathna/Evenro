package lk.evenro.even.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lk.evenro.even.DetailEventActivity;
import lk.evenro.even.R;
import lk.evenro.even.TicketViewActivity;
import lk.evenro.even.model.EventDetails;
import lk.evenro.even.model.PaymentEventDetails;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.EventViewHolder> {

    class EventViewHolder extends RecyclerView.ViewHolder {

        //        ImageView event_item_image;
        TextView purchase_event_name;
        TextView purchase_ticket_date;
        TextView purchase_ticket_qty;
        TextView purchase_ticket_price;
        TextView purchase_ticket_view_button;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            purchase_event_name = itemView.findViewById(R.id.event_name);
            purchase_ticket_date = itemView.findViewById(R.id.buying_date);
            purchase_ticket_qty = itemView.findViewById(R.id.ticket_qty);
            purchase_ticket_price = itemView.findViewById(R.id.buying_ticket_price);
            purchase_ticket_view_button = itemView.findViewById(R.id.ticket_view_button);

        }
    }

    ArrayList<PaymentEventDetails> paymentEventDetails;

    public InvoiceAdapter( ArrayList<PaymentEventDetails> paymentEventDetails) {
        this.paymentEventDetails = paymentEventDetails;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.invoice_payment_item, parent, false);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        PaymentEventDetails details = paymentEventDetails.get(position);
        holder.purchase_event_name.setText(details.getEvent_name());
        holder.purchase_ticket_date.setText(details.getPayment_date());
        holder.purchase_ticket_qty.setText(details.getQty());
        holder.purchase_ticket_price.setText(details.getTicket_price());
        holder.purchase_ticket_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TicketViewActivity.class);
                intent.putExtra("ticket_details", details);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentEventDetails.size();
    }


}
