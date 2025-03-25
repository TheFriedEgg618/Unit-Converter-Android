package com.example.uc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class MathConverter {
    private static final Map<String, String[]> formulas = new HashMap<>();

    static {
        // Store both directions in an array: [forward, reverse]

        // Temperature
        formulas.put("Celsius_Fahrenheit", new String[]{"(x * 9/5) + 32", "(x - 32) * 5/9"});
        formulas.put("Celsius_Kelvin", new String[]{"x + 273.15", "x - 273.15"});
        formulas.put("Fahrenheit_Kelvin", new String[]{"(x - 32) * 5/9 + 273.15", "(x - 273.15) * 9/5 + 32"});

        // Mass
        formulas.put("Kilograms_Pounds", new String[]{"x * 2.20462", "x / 2.20462"});
        formulas.put("Kilograms_Grams", new String[]{"x * 1000", "x / 1000"});
        formulas.put("Kilograms_ounces", new String[]{"x * 35.274", "x / 35.274"});
        formulas.put("Pounds_Grams", new String[]{"x * 453.6", "x / 453.6"});
        formulas.put("Pounds_ounces", new String[]{"x * 16", "x / 16"});
        formulas.put("ounces_Grams", new String[]{"x * 28.35", "x / 28.35"});

        // Length Unit Conversions
        formulas.put("Kilometers_Meters", new String[]{"x * 1000", "x / 1000"});
        formulas.put("Kilometers_Centimeters", new String[]{"x * 100000", "x / 100000"});
        formulas.put("Kilometers_Millimeters", new String[]{"x * 1000000", "x / 1000000"});
        formulas.put("Kilometers_Miles", new String[]{"x / 1.60934", "x * 1.60934"});
        formulas.put("Kilometers_Yards", new String[]{"x * 1093.61", "x / 1093.61"});
        formulas.put("Kilometers_Feet", new String[]{"x * 3280.84", "x / 3280.84"});
        formulas.put("Kilometers_Inches", new String[]{"x * 39370.1", "x / 39370.1"});
        formulas.put("Meters_Miles", new String[]{"x / 1609.34", "x * 1609.34"});
        formulas.put("Meters_Yards", new String[]{"x * 1.09361", "x / 1.09361"});
        formulas.put("Meters_Feet", new String[]{"x * 3.28084", "x / 3.28084"});
        formulas.put("Meters_Inches", new String[]{"x * 39.3701", "x / 39.3701"});
        formulas.put("Meters_Centimeters", new String[]{"x * 100", "x / 100"});
        formulas.put("Meters_Millimeters", new String[]{"x * 1000", "x / 1000"});
        formulas.put("Centimeters_Millimeters", new String[]{"x * 10", "x / 10"});
        formulas.put("Centimeters_Miles", new String[]{"x / 160934", "x * 160934"});
        formulas.put("Centimeters_Yards", new String[]{"x / 91.44", "x * 91.44"});
        formulas.put("Centimeters_Feet", new String[]{"x / 30.48", "x * 30.48"});
        formulas.put("Centimeters_Inches", new String[]{"x / 2.54", "x * 2.54"});
        formulas.put("Millimeters_Miles", new String[]{"x / 1609344", "x * 1609344"});
        formulas.put("Millimeters_Yards", new String[]{"x / 914.4", "x * 914.4"});
        formulas.put("Millimeters_Feet", new String[]{"x / 304.8", "x * 304.8"});
        formulas.put("Millimeters_Inches", new String[]{"x / 25.4", "x * 25.4"});
        formulas.put("Miles_Yards", new String[]{"x * 1760", "x / 1760"});
        formulas.put("Miles_Feet", new String[]{"x * 5280", "x / 5280"});
        formulas.put("Miles_Inches", new String[]{"x * 63360", "x / 63360"});
        formulas.put("Yards_Feet", new String[]{"x * 3", "x / 3"});
        formulas.put("Yards_Inches", new String[]{"x * 36", "x / 36"});
        formulas.put("Feet_Inches", new String[]{"x * 12", "x / 12"});

        // Time
        formulas.put("Second_Minute", new String[]{"x / 60", "x * 60"});
        formulas.put("Second_Hour", new String[]{"x / 3600", "x * 3600"});
        formulas.put("Second_Day", new String[]{"x / 86400", "x * 86400"});
        formulas.put("Second_Week", new String[]{"x / 604800", "x * 604800"});
        formulas.put("Minute_Hour", new String[]{"x / 60", "x * 60"});
        formulas.put("Minute_Day", new String[]{"x / 1440", "x * 1440"});
        formulas.put("Minute_Week", new String[]{"x / 10080", "x * 10080"});
        formulas.put("Hour_Day", new String[]{"x / 24", "x * 24"});
        formulas.put("Hour_Week", new String[]{"x / 168", "x * 168"});
        formulas.put("Day_Week", new String[]{"x / 7", "x * 7"});
    }

    public static double convert(String unitFrom, String unitTo, double value) {
        String key = unitFrom + "_" + unitTo;
        String reverseKey = unitTo + "_" + unitFrom;

        String formula;
        if (formulas.containsKey(key)) {
            formula = Objects.requireNonNull(formulas.get(key))[0]; // Forward conversion
        } else if (formulas.containsKey(reverseKey)) {
            formula = Objects.requireNonNull(formulas.get(reverseKey))[1]; // Reverse conversion
        } else {
            throw new IllegalArgumentException("Conversion not found: " + unitFrom + " to " + unitTo);
        }

        Expression expression = new ExpressionBuilder(formula)
                .variable("x")
                .build()
                .setVariable("x", value);

        return expression.evaluate();
    }

    public static String getFormula(String unitFrom, String unitTo) {
        String key = unitFrom + "_" + unitTo;
        String reverseKey = unitTo + "_" + unitFrom;
        if (formulas.containsKey(key)) {
            return Objects.requireNonNull(formulas.get(key))[0]; // Forward conversion
        } else if (formulas.containsKey(reverseKey)) {
            return Objects.requireNonNull(formulas.get(reverseKey))[1]; // Reverse conversion
        } else {
            throw new IllegalArgumentException("Conversion not found: " + unitFrom + " to " + unitTo);
        }
    }
}
