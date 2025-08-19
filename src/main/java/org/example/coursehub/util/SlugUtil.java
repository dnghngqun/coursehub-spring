package org.example.coursehub.util;

import java.text.Normalizer;

public class SlugUtil {
    public static String slugify(String input) {
        if (input == null) return null;
        String now = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return now;
    }
}