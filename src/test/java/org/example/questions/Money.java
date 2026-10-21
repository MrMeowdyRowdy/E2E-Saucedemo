package org.example.questions;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parses amounts like "$29.99" or "Item total: $45.98" without floating point. */
public class Money {
    private static final Pattern AMOUNT = Pattern.compile("\\$\\s*(\\d+(?:\\.\\d+)?)");

    public static BigDecimal parse(String text) {
        Matcher m = AMOUNT.matcher(text);
        if (!m.find()) {
            throw new IllegalArgumentException("No amount in: " + text);
        }
        return new BigDecimal(m.group(1));
    }
}
