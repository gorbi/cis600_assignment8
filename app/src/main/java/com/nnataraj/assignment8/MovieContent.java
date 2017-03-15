package com.nnataraj.assignment8;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nagaprasad on 3/14/17.
 */

public class MovieContent {

    /**
     * An array of sample (movie) items.
     */
    public static List<MovieItem> ITEMS = new ArrayList<>();

    /**
     * A Movie item representing a piece of content.
     */
    public static class MovieItem {
        public JSONObject details;
        public Bitmap image;

        @Override
        public String toString() {
            try {
                return details.getString("id");
            } catch (Exception ae) {
                return null;
            }
        }
    }
}
