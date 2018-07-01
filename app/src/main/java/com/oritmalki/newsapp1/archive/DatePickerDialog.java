package com.oritmalki.newsapp1.archive;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.oritmalki.newsapp1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerDialog extends AlertDialog {


private Calendar calendar;
private EditText editText;
 private android.app.DatePickerDialog.OnDateSetListener datePicked;


    protected DatePickerDialog(@NonNull final Context context) {
        super(context);
//        editText = findViewById(R.id.from_date_edit_text);
        calendar = Calendar.getInstance();
        setView(editText);
        datePicked = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLable();
            }
        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.DatePickerDialog(context, datePicked, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLable() {

        String format = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);

        editText.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state=super.onSaveInstanceState();

        state.putString(getContext().getString(R.string.settings_from_date_key), editText.getText().toString());

        return(state);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        editText.setText(state.getString(getContext().getString(R.string.settings_from_date_key)));
    }
}
