package com.example.android.quakereport;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by asmit on 9/8/17.
 */

public class NoInternetFrament extends DialogFragment {
    AlertDialog alertDialog;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Sorry! no connection Right Now")
                 .setIcon(R.mipmap.ic_launcher)
                .setMessage("We need connection for retriving the data!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.google.com"));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Connect to network ", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }).create();
        return alertDialog;
    }

}

