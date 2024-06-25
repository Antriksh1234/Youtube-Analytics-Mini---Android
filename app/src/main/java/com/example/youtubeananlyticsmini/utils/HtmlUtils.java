package com.example.youtubeananlyticsmini.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

public class HtmlUtils {
    // Method to decode HTML entities and strip HTML tags
    public static String decodeHtml(String input) {
        Spanned spanned;
        spanned = Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);

        // Convert spanned to string
        return spanned.toString();
    }
}
