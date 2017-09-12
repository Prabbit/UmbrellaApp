package com.weatherapp.umbrella.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.events.ParamsChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import static com.weatherapp.umbrella.utils.Constants.UNIT;
import static com.weatherapp.umbrella.utils.Constants.US;
import static com.weatherapp.umbrella.utils.Constants.ZIPCODE;


/**
 * Created by CodeWord on 9/11/2017.
 */

public class UiUtility {

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
        return progressDialog;
    }


    public static void showUnitsDialog(Context context, final SharedPreferences sharedPreferences) {
        final CharSequence[] items = {"Celsius", "Fahrenheit"};

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select unit");
        int selectedUnit = sharedPreferences.getInt(UNIT, US);
        builder.setSingleChoiceItems(items, selectedUnit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        sharedPreferences.edit().putInt(Constants.UNIT, item).apply();
                    }
                });
        builder.setPositiveButton("OK", listener);
        builder.setNegativeButton("Cancel", listener);
        builder.show();
    }


    public static void showChangeZipDialog(Context context, final SharedPreferences
            sharedPreferences, final EventBus eventBus) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.zip_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText zipField = (EditText) dialogView.findViewById(R.id.zipField);

        dialogBuilder.setTitle("Enter zip code");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (StringUtils.isNotEmpty(zipField.getText())) {
                    sharedPreferences.edit().putString(ZIPCODE, zipField.getText().toString()).apply();
                    eventBus.postSticky(new ParamsChangeEvent());
                    dialog.dismiss();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
