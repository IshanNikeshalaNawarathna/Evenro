package lk.evenro.even.model;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lk.evenro.even.R;


public class CustomAlert {
    public static void showDatePickerDialog(Context context, DatePickerListener listener) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.date_picker, null);

        DatePicker datePicker = dialogView.findViewById(R.id.date_pick);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                (view, year, month, dayOfMonth) -> {

                    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                    String monthName = months[month];

                    String selectedDate = dayOfMonth + " " + monthName + " " + year;

                    listener.onDateSelected(selectedDate);

                    alertDialog.dismiss();
                });
    }

    public interface DatePickerListener {
        void onDateSelected(String selectedDate);
    }

    public static void showTimePickerDialog(Context context, TimePickerListener listener) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.time_picker, null);

        TimePicker timePicker = dialogView.findViewById(R.id.time_picker);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        timePicker.setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(Calendar.getInstance().get(Calendar.MINUTE));

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String timeFormat = "hh:mm a";
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
                String selectedTime = sdf.format(calendar.getTime());

                listener.onTimeSelected(selectedTime);


                timePicker.invalidate();
                alertDialog.dismiss();
            }
        });
    }

    public interface TimePickerListener {
        void onTimeSelected(String selectedTime);
    }

}
