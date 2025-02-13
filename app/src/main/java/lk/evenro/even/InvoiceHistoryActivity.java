package lk.evenro.even;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import lk.evenro.even.model.PaymentEventDetails;

public class InvoiceHistoryActivity extends AppCompatActivity {


    private String name = "";
    private String email = "";
    Map<String, Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UserDataBase userData = new UserDataBase(getApplicationContext(), "evenro.dp", null, 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = userData.getReadableDatabase().query("user", null, null, null, null, null, null);

                if (cursor.moveToNext()) {
                    name = cursor.getString(1);
                    email = cursor.getString(2);
                    InvoicePaymentLoad(name, email);
                }

            }
        }).start();


    }

    private void InvoicePaymentLoad(String name, String email) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("invoice").whereEqualTo(
                "buyer_email", email
        ).whereEqualTo(
                "buyer_name", name
        ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();

                    for (DocumentSnapshot snapshot : documents) {
                        data = snapshot.getData();
                        PaymentEventDetails paymentEventDetails = snapshot.toObject(PaymentEventDetails.class);
                        Log.i("TEST CODE", paymentEventDetails.getPayment_ID());
                        Log.i("TEST CODE", paymentEventDetails.getPayment_date());
                        Log.i("TEST CODE", paymentEventDetails.getEvent_name());
                        Log.i("TEST CODE", paymentEventDetails.getBuyer_email());
                        Log.i("TEST CODE", paymentEventDetails.getBuyer_name());
                        Log.i("TEST CODE", paymentEventDetails.getQty());
                        Log.i("TEST CODE", paymentEventDetails.getTicket_price());
                    }

                }
            }
        });
    }

}