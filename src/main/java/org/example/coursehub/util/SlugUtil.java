package org.example.coursehub.util;

import java.text.Normalizer;

public class SlugUtil {
    //chuẩn hóa chuỗi thành slug ("Ha Noi 2025!" → "ha-noi-2025-")
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