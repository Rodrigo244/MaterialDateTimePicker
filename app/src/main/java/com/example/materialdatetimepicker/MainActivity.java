package com.example.materialdatetimepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    DatePickerDialog datePickerDialog ;
    int Year, Month, Day;
    Calendar calendar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        //getWeekendDays();

        final Button button_datepicker = (Button) findViewById(R.id.button_datepicker);
        button_datepicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(MainActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Date Picker");


                // Setting Min Date to today date
                Calendar min_date_c = Calendar.getInstance();
                min_date_c.set(Calendar.DAY_OF_MONTH, Day + 3);
                datePickerDialog.setMinDate(min_date_c);



                // Setting Max Date to next 32 days
                Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.DAY_OF_MONTH, Day + 28);
                datePickerDialog.setMaxDate(max_date_c);



                //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                    int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                        Calendar[] disabledDays =  new Calendar[1];
                        disabledDays[0] = loopdate;
                        datePickerDialog.setDisabledDays(disabledDays);
                    }
                }



                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(MainActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        // Genera una cadena conforme a los componentes de año, mes y día recibidos.
        String date = String.format(Locale.getDefault(), "%02d", Day) + "-" +
                String.format(Locale.getDefault(), "%02d", (Month + 1)) + "-" +
                String.format(Locale.getDefault(), "%04d", Year);


        Toast.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();

        TextView text_datepicker = (TextView)findViewById(R.id.text_datepicker);
        text_datepicker.setText(date);

    }

}
