package org.pawkrol.academic.ca.utils;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pawkrol on 4/29/17.
 */
public class ColorHelper {

    private static Map<Integer, Color> colorMap = new HashMap<>();

    public static Color getColor(int value){
        return colorMap.get(value);
    }

    public static void setColor(int value, Color color) {
        colorMap.put(value, color);
    }
}
