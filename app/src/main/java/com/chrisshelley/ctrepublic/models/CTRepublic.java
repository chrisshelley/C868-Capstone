package com.chrisshelley.ctrepublic.models;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class CTRepublic {

    public static void navigateTo(Context context, Class c) {
        Intent navigationIntent = new Intent(context, c);
        context.startActivity(navigationIntent);
    }
}
