package com.hallucind.smartaakband.Band;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.Utils.Prefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by albin on 2018-04-22.
 */

public class BandHandler {

    public static ArrayList<Band> bands;

    // laddar kopplade band
    public static void loadBands(Context context) {
        bands = new ArrayList<>();

        int len = Prefs.getInt(context, "bandSize");

        if (len != 0) {
            for (int i = 0; i < len; i++) {
                String name = Prefs.getString(context, "name" + i);
                float color =  Prefs.getFloat(context, "color" + i);
                Band band = new Band(name, color);
                bands.add(band);
            }
        }

        Band band = new Band(null, 0);
        bands.add(band);

    }

    // sparar kopplade band
    public static void saveBands(Context context) {
        for (int i = 0; i < bands.size() - 1; i++) {
            Prefs.saveToPrefs(context, "name" + i, bands.get(i).getName());
            Prefs.saveToPrefs(context, "color" + i, bands.get(i).getcolorID());
        }
        Prefs.saveToPrefs(context, "bandSize", bands.size() - 1);
    }

    // Returnerar ett bands färg i rätt format
    public static GradientDrawable getGradientDrawable(ArrayList<Band> list, int position) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setCornerRadius(4);
        float[] color = new float[] {list.get(position).getcolorID(), 1, 1};
        gd.setColor(Color.HSVToColor(color));
        return gd;
    }
}