package lk.evenro.even;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lk.evenro.even.adapter.EventAdapter;
import lk.evenro.even.adapter.InvoiceAdapter;
import lk.evenro.even.model.PaymentEventDetails;
import lk.evenro.even.model.UserDetails;

public class InvoiceHistoryActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<PaymentEventDetails> paymentDetailsList = new ArrayList<>();
    private InvoiceAdapter invoiceAdapter;


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



    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.invoice_history_recyclerView); // Replace with your RecyclerView's ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        invoiceAdapter = new InvoiceAdapter((ArrayList<PaymentEventDetails>) paymentDetailsList);
        recyclerView.setAdapter(invoiceAdapter);



        SharedPreferences sharedPreferences = getSharedPreferences("lk.evenro.even.data", Context.MODE_PRIVATE);
        String uData = sharedPreferences.getString("userData", null);

        Gson gson = new Gson();
        UserDetails user = gson.fromJson(uData, UserDetails.class);

        if (user != null) {
             String nameUser = user.getName();
            String emailUser = user.getEmail();
            InvoicePaymentLoad(nameUser,emailUser);


            Log.e("test code email",nameUser+emailUser);

        } else {
            Toast.makeText(getApplicationContext(), "Please Inter your user Cradintal", Toast.LENGTH_SHORT).show();
        }


    }

    private void InvoicePaymentLoad(String name, String email) {
        Log.e("test code email",name+email);
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
                                Log.d("InvoicePaymentLoad", "PaymentEventDetails: " + paymentEventDetails.toString());
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