package lk.evenro.even;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EventAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton date_image_button = findViewById(R.id.date_add_bottom_sheet_button);
        date_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment  bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        ImageButton time_image_button = findViewById(R.id.time_bottom_sheet_button);
        time_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBottomSheetFragment timeBottomSheetFragment = new TimeBottomSheetFragment();
                timeBottomSheetFragment.show(getSupportFragmentManager(), timeBottomSheetFragment.getTag());
            }
        });

    }
}