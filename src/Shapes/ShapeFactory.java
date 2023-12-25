package Shapes;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 * The ShapeFactory class is responsible for creating Shape objects from JSON
 * data.
 * It interprets the JSON structure and instantiates various Shape types like
 * Line, Rectangle,
 * Ellipses, Triangle, and Diamond, with appropriate properties.
 */
public class ShapeFactory {
    final Map<String, Color> colorMap = new HashMap();

    /**
     * Constructor for the ShapeFactory. Initializes the colorMap with predefined
     * colors.
     */
    public ShapeFactory() {
    }

    /**
     * Creates a Shape object from a given JSON object.
     * The method parses the JSON object and creates a specific Shape type based on
     * the 'type' field.
     *
     * @param json The JSON object containing shape data.
     * @return The created Shape object or null if an error occurs or the type is
     *         unknown.
     */
    public Shape createShapeFromJson(JsonObject var1) {

        boolean isOwner = var1.getBoolean("isOwner");
        String var2 = var1.getString("type", (String) null);

        this.colorMap.put("black", Color.BLACK);
        this.colorMap.put("blue", Color.BLUE);
        this.colorMap.put("red", Color.RED);
        this.colorMap.put("green", Color.GREEN);
        this.colorMap.put("yellow", Color.YELLOW);
        this.colorMap.put("white", Color.WHITE);
        this.colorMap.put("cyan", Color.CYAN);
        this.colorMap.put("magenta", Color.MAGENTA);
        this.colorMap.put("orange", Color.ORANGE);
        this.colorMap.put("pink", Color.PINK);
        this.colorMap.put("gray", Color.GRAY);
        this.colorMap.put("darkGrey", Color.DARK_GRAY);
        this.colorMap.put("lightGrey", Color.LIGHT_GRAY);

        if (var2 == null) {
            return null;
        } else {
            try {
                JsonObject var3 = var1.getJsonObject("properties");
                if (var3 == null) {
                    var3 = Json.createObjectBuilder().build();
                }
                switch (var2) {
                    case "line":
                            Shape newLine = new Line((double) getIntOrDefault(var1, "x", 0), (double) getIntOrDefault(var1, "y", 0),
                                (double) getIntOrDefault(var3, "x2", 0), (double) getIntOrDefault(var3, "y2", 0),
                                this.getColorOrDefault(var3, "color", Color.BLACK),
                                getIntOrDefault(var3, "borderWidth", 1));
                                newLine.setIsOwner(isOwner);
                            return newLine;                
                    case "rectangle":
                            Shape newRectangle = new Rectangle((double) getIntOrDefault(var1, "x", 0),
                                (double) getIntOrDefault(var1, "y", 0), getIntOrDefault(var3, "width", 0),
                                getIntOrDefault(var3, "height", 0),
                                this.getColorOrDefault(var3, "borderColor", Color.BLACK),
                                getIntOrDefault(var3, "borderWidth", 1),
                                this.getColorOrDefault(var3, "fillColor", Color.WHITE));
                                newRectangle.setIsOwner(isOwner);
                            return newRectangle;
                    case "ellipse":
                            Shape newEllipse = new Ellipses(getIntOrDefault(var1, "x", 0), getIntOrDefault(var1, "y", 0),
                                getIntOrDefault(var3, "width", 0), getIntOrDefault(var3, "height", 0),
                                this.getColorOrDefault(var3, "borderColor", Color.BLACK),
                                getIntOrDefault(var3, "borderWidth", 1),
                                this.getColorOrDefault(var3, "fillColor", Color.WHITE));
                                newEllipse.setIsOwner(isOwner);
                            return newEllipse;
                    case "triangle":
                            Shape newTriangle = new Triangle((double) getIntOrDefault(var1, "x", 0),
                                (double) getIntOrDefault(var1, "y", 0), (double) getIntOrDefault(var3, "x2", 0),
                                (double) getIntOrDefault(var3, "y2", 0), (double) getIntOrDefault(var3, "x3", 0),
                                (double) getIntOrDefault(var3, "y3", 0),
                                this.getColorOrDefault(var3, "borderColor", Color.BLACK),
                                getIntOrDefault(var3, "borderWidth", 1),
                                this.getColorOrDefault(var3, "fillColor", Color.WHITE));
                                newTriangle.setIsOwner(isOwner);
                            return newTriangle;
                    case "diamond":
                            Shape newDiamond = new Diamond(getIntOrDefault(var1, "x", 0), getIntOrDefault(var1, "y", 0),
                                getIntOrDefault(var3, "width", 0), getIntOrDefault(var3, "height", 0),
                                this.getColorOrDefault(var3, "borderColor", Color.BLACK),
                                getIntOrDefault(var3, "borderWidth", 1),
                                this.getColorOrDefault(var3, "fillColor", Color.WHITE));
                                newDiamond.setIsOwner(isOwner);
                            return newDiamond;
                    default:
                        throw new IllegalArgumentException("Unknown shape type: " + var2);
                }
            } catch (Exception var6) {
                return null;
            }
        }
    }

    /**
     * Retrieves an integer value from a JSON object based on a specified key,
     * or returns a default value if the key is not present or not a number.
     *
     * @param jsonObject   The JSON object to retrieve the value from.
     * @param key          The key whose value is to be retrieved.
     * @param defaultValue The default value to return if the key is not found or
     *                     not a number.
     * @return The retrieved integer value or the default value.
     */
    private static int getIntOrDefault(JsonObject var0, String var1, int var2) {
        if (var0.containsKey(var1)) {
            JsonValue var3 = (JsonValue) var0.get(var1);
            if (var3.getValueType() == ValueType.NUMBER) {
                return ((JsonNumber) var3).intValue();
            }
        }

        return var2;
    }

    /**
     * Retrieves a Color from a JSON object based on a specified key.
     * The color can be defined by name or hexadecimal value.
     * Returns a default color if the key is not present or the value is invalid.
     *
     * @param jsonObject   The JSON object to retrieve the color from.
     * @param key          The key whose color value is to be retrieved.
     * @param defaultColor The default Color to return if the key is not found or
     *                     the value is invalid.
     * @return The retrieved Color or the default Color.
     */
    private Color getColorOrDefault(JsonObject var1, String var2, Color var3) {
        if (var1.containsKey(var2) && !var1.isNull(var2)) {
            JsonValue var4 = (JsonValue) var1.get(var2);
            if (var4.getValueType() == ValueType.STRING) {
                String var5 = var1.getString(var2);
                if (var5.startsWith("#")) {
                    return this.parseHexColor(var5);
                }

                return (Color) this.colorMap.getOrDefault(var5.toLowerCase(), var3);
            }

            if (var4.getValueType() == ValueType.NUMBER) {
                return new Color(var1.getInt(var2));
            }
        }

        return var3;
    }

    /**
     * Parses a hexadecimal color string and returns the corresponding Color object.
     * Returns null and logs an error if the format is invalid.
     *
     * @param hexColor The hexadecimal color string to parse.
     * @return The corresponding Color object or null if the format is invalid.
     */
    private Color parseHexColor(String var1) {
        try {
            return Color.decode(var1);
        } catch (NumberFormatException var3) {
            System.err.println("Invalid hex color format: " + var1);
            return null;
        }
    }
}
