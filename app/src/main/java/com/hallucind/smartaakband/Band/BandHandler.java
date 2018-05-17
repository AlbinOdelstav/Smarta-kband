package com.hallucind.smartaakband.Band;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    public static void swapLastElements() {
        Collections.swap(BandHandler.bands, BandHandler.bands.size() - 1, BandHandler.bands.size() - 2);
    }

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

    public static void saveBands(Context context) {
        for (int i = 0; i < bands.size() - 1; i++) {
            Prefs.saveToPrefs(context, "name" + i, bands.get(i).getName());
            Prefs.saveToPrefs(context, "color" + i, bands.get(i).getcolorID());
        }
        Prefs.saveToPrefs(context, "bandSize", bands.size() - 1);
    }
}