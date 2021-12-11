package com.illusion_softworks.kjoerbar.utilities;

import java.util.Locale;

public class FormatTime {
    public static String getFormattedTime(long duration) {
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d", duration / (3600 * 1000),
                duration / (60 * 1000) % 60,
                duration / 1000 % 60);
    }
}
