package lk.evenro.even;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.adapter.InvoiceAdapter;
import lk.evenro.even.model.PaymentEventDetails;

public class InvoiceHistoryActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<PaymentEventDetails> paymentDetailsList = new ArrayList<>();
    private InvoiceAdapter invoiceAdapter;
    private String name, email;

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

        recyclerView = findViewById(R.id.invoice_history_recyclerView); // Replace with your RecyclerView's ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        invoiceAdapter = new InvoiceAdapter((ArrayList<PaymentEventDetails>) paymentDetailsList);
        recyclerView.setAdapter(invoiceAdapter);

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
        firebaseFirestore.collection("invoice")
                .whereEqualTo("buyer_email", email)
                .whereEqualTo("buyer_name", name)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        paymentDetailsList.clear(); // Clear the list before adding new data
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            PaymentEventDetails paymentEventDetails = snapshot.toObject(PaymentEventDetails.class);
                            if (paymentEventDetails != null) {
                                paymentDetailsList.add(paymentEventDetails);
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(() -> {
                            invoiceAdapter.notifyDataSetChanged();
                        });
                    } else {
                        Log.e("InvoicePaymentLoad", "Error getting documents: ", task.getException());
                    }
                });
    }

}