package lk.evenro.even;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import lk.evenro.even.model.PaymentEventDetails;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TicketViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView event_name = findViewById(R.id.ticket_event_name);
        TextView event_date = findViewById(R.id.start_event_date);
        TextView event_time = findViewById(R.id.event_start_time);
        TextView event_value_date = findViewById(R.id.value_date);
        TextView event_value_qty = findViewById(R.id.ticket_qtys);
        Button ticket_download = findViewById(R.id.ticket_download);

        PaymentEventDetails paymentEventDetails = (PaymentEventDetails) getIntent().getSerializableExtra("ticket_details");
        if (paymentEventDetails != null) {
            event_name.setText(paymentEventDetails.getEvent_name());
            event_date.setText(paymentEventDetails.getEvent_date());
            event_time.setText(paymentEventDetails.getEvent_time());
            event_value_date.setText(paymentEventDetails.getEvent_date());
            event_value_qty.setText(paymentEventDetails.getQty());


            ticket_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Gson gson = new Gson();
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("email", paymentEventDetails.getBuyer_email());
                            jsonObject.addProperty("name", paymentEventDetails.getBuyer_name());
                            jsonObject.addProperty("orderId", paymentEventDetails.getPayment_ID());
                            jsonObject.addProperty("event_date", paymentEventDetails.getEvent_date());
                            jsonObject.addProperty("event_time", paymentEventDetails.getEvent_time());
                            jsonObject.addProperty("payment_date", paymentEventDetails.getPayment_date());
                            jsonObject.addProperty("ticket_price", paymentEventDetails.getTicket_price());
                            jsonObject.addProperty("ticket_qty", paymentEventDetails.getQty());

                            OkHttpClient okHttpClient = new OkHttpClient();
                            RequestBody requestBody = RequestBody.create(gson.toJson(jsonObject), MediaType.parse("application/json"));
                            Request request = new Request.Builder()
                                    .url("https://8065-2402-d000-a400-225d-c091-f58a-add-d067.ngrok-free.app/EmailSending/EmailSending")
                                    .post(requestBody)
                                    .build();

                            try {
                                Response response = okHttpClient.newCall(request).execute();
                                String responseText = response.body().string();
                                JsonObject responseJson = gson.fromJson(responseText, JsonObject.class);
                                if (responseJson.get("message").getAsString().equals("success")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(TicketViewActivity.this, "Check Your Email", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(TicketViewActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                Log.i("HttpReqest1", responseText);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                }
            });

        }

    }
}