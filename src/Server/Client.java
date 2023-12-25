package Server;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import Shapes.Diamond;
import Shapes.Rectangle;
import Shapes.Shape;
import Shapes.ShapeFactory;
import Shapes.Triangle;
import Shapes.Ellipses;
import Shapes.Line;
import myGUI.GUIDelegate;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Client class manages the network connection to a server, handling sending
 * and receiving requests and responses. It provides methods for various actions
 * like logging in, fetching drawings, adding, updating, and deleting drawings.
 * It also includes utility methods for converting shapes to JSON and colors to
 * string representations.
 */
public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final String token = "ef32cf9b-6c06-476c-86d6-f990ba241988";

    /**
     * Constructs a new Client object, establishing a network connection with a
     * server.
     * Automatically logs in to the server using a predefined token.
     *
     * @param hostName The hostname of the server.
     * @param port     The port number to connect to.
     * @throws IOException If an I/O error occurs when opening the connection.
     */
    public Client(String hostName, int port, GUIDelegate guiDelegate) throws IOException {
        socket = new Socket(hostName, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        login();
        guiDelegate.setConnectedToServer(true);
    }

    /**
     * Sends a JSON request to the server and receives a response.
     * Parses the server's response into a JsonObject.
     *
     * @param request    The JSON object representing the request to be sent.
     * @param actionType represents what type of action sendRequets is completing as
     *                   a string
     * @return JsonObject representing the server's response.
     * @throws IOException If an I/O error occurs in communication with the server.
     */
    public JsonObject sendRequest(JsonObject request, String actionType) throws IOException {
        out.println(request.toString());
        String responseLine = in.readLine();
        try (JsonReader jsonReader = Json.createReader(new StringReader(responseLine))) {
            if (actionType.equals("add")) {
                JsonObject jsonObject = jsonReader.readObject();
                return jsonObject;
            } else if (actionType.equals("get")) {
                JsonArray jsonArray = jsonReader.readArray();
                return Json.createObjectBuilder().add("shapes", jsonArray).build();
            } else if (actionType.equals("update") || actionType.equals("delete")) {
                JsonObject jsonObject = jsonReader.readObject();
                return jsonObject;
            } else {
                return Json.createObjectBuilder()
                        .add("result", "error")
                        .add("message", "Unsupported action type")
                        .build();

            }
        } catch (Exception e) {
            return Json.createObjectBuilder()
                    .add("result", "error")
                    .add("message", "Error parsing JSON response: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Logs into the server using a predefined token.
     * Sends a login request to the server and handles the response.
     *
     * @throws IOException If an I/O error occurs during login.
     */
    public void login() throws IOException {
        JsonObject loginJson = Json.createObjectBuilder()
                .add("action", "login")
                .add("data", Json.createObjectBuilder().add("token", token))
                .build();

        out.println(loginJson);
        out.flush();

        try {
            String res = in.readLine();
            System.out.println(res);
        } catch (IOException e) {
            System.err.println("IOException occurred during login: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error during login: " + e.getMessage());
            throw new IOException("Unexpected error during login.", e);
        }
    }

    /**
     * Parses a JsonArray of shapes, creating Shape objects while handling missing
     * or blank attributes.
     * 
     * @param shapesArray The JsonArray containing shape data.
     * @return A list of Shape objects.
     */
    public List<Shapes.Shape> parseShapes(JsonArray shapesArray) {
        List<Shapes.Shape> shapes = new ArrayList<>();
        ShapeFactory shapeFactory = new ShapeFactory();

        for (JsonValue shapeVal : shapesArray) {
            if (!(shapeVal instanceof JsonObject))
                continue;
            JsonObject jsonShape = (JsonObject) shapeVal;

            Shapes.Shape shape = shapeFactory.createShapeFromJson(jsonShape);
            if (shape != null) {
                shapes.add(shape);
            } else {
                continue;
            }
        }
        return shapes;
    }

    /**
     * Requests a list of existing drawings from the server.
     * Sends a request to retrieve all drawings and parses the response.
     *
     * @return JsonObject containing the server's response to the getDrawings
     *         request.
     * @throws IOException If an I/O error occurs when communicating with the
     *                     server.
     */
    public JsonObject getDrawings() throws IOException {
        JsonObject getDrawingsJson = Json.createObjectBuilder()
                .add("action", "getDrawings")
                .build();

        return sendRequest(getDrawingsJson, "get");
    }

    /**
     * Sends a request to the server to add a new drawing.
     * Converts a Shape object into a JSON representation and sends it to the
     * server.
     *
     * @param shape The Shape object to be added as a drawing.
     * @return JsonObject containing the server's response to the addDrawing
     *         request.
     * @throws IOException If an I/O error occurs when communicating with the
     *                     server.
     */
    public JsonObject addDrawing(Shape shape) throws IOException {
        String type = shape.getClass().getSimpleName().toLowerCase();
        JsonObject properties = getPropertiesJson(shape);
        JsonObject data = Json.createObjectBuilder()
                .add("type", type)
                .add("x", shape.getX())
                .add("y", shape.getY())
                .add("properties", properties)
                .build();
        JsonObject request = Json.createObjectBuilder()
                .add("action", "addDrawing")
                .add("data", data)
                .build();
        return sendRequest(request, "add");
    }

    /**
     * Sends a request to the server to update an existing drawing.
     * Uses the given shape's properties to create a JSON object and sends it to the
     * server.
     *
     * @param id    The unique identifier of the shape to be updated.
     * @param shape The Shape object with updated properties.
     * @return JsonObject containing the server's response to the updateDrawing
     *         request.
     * @throws IOException If an I/O error occurs when communicating with the
     *                     server.
     */
    public JsonObject updateDrawing(String id, Shape shape) throws IOException {
        JsonObject properties = getPropertiesJson(shape);
        JsonObject data = Json.createObjectBuilder()
                .add("id", id)
                .add("properties", properties)
                .build();
        JsonObject request = Json.createObjectBuilder()
                .add("action", "updateDrawing")
                .add("data", data)
                .build();
                System.out.println(request);
        return sendRequest(request, "update");
    }

    /**
     * Sends a request to the server to delete a specific drawing.
     * The request includes the unique identifier of the drawing to be deleted.
     *
     * @param id The unique identifier of the drawing to be deleted.
     * @return JsonObject containing the server's response to the deleteDrawing
     *         request.
     * @throws IOException If an I/O error occurs when communicating with the
     *                     server.
     */
    public JsonObject deleteDrawing(String id) throws IOException {
        JsonObject data = Json.createObjectBuilder()
                .add("id", id)
                .build();
        JsonObject request = Json.createObjectBuilder()
                .add("action", "deleteDrawing")
                .add("data", data)
                .build();
        return sendRequest(request, "delete");
    }


    
    /**
     * Converts a Shape object into a JSON representation of its properties.
     * This method identifies the type of shape and extracts relevant properties
     * such as dimensions, color, rotation, etc., and represents them in a JSON
     * object.
     *
     * @param shape The Shape object to convert into JSON properties.
     * @return JsonObject representing the properties of the given shape.
     */
    private JsonObject getPropertiesJson(Shape shape) {
        JsonObjectBuilder propertiesBuilder = Json.createObjectBuilder();

        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            propertiesBuilder.add("width", rect.getWidth())
                    .add("height", rect.getHeight())
                    .add("rotation", rect.getRotation())
                    .add("borderColor", colorToString(rect.getBorderColor()))
                    .add("borderWidth", rect.getBorderWidth());
            if (rect.getFillColor() != null) {
                propertiesBuilder.add("fillColor", colorToString(rect.getFillColor()));
            }
        } else if (shape instanceof Ellipses) {
            Ellipses ellipse = (Ellipses) shape;
            propertiesBuilder.add("width", ellipse.getWidth())
                    .add("height", ellipse.getHeight())
                    .add("rotation", ellipse.getRotation())
                    .add("borderColor", colorToString(ellipse.getBorderColor()))
                    .add("borderWidth", ellipse.getBorderWidth());
            if (ellipse.getFillColor() != null) {
                propertiesBuilder.add("fillColor", colorToString(ellipse.getFillColor()));
            }
        } else if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            propertiesBuilder.add("x2", triangle.getX2())
                    .add("y2", triangle.getY2())
                    .add("x3", triangle.getX3())
                    .add("y3", triangle.getY3())
                    .add("rotation", triangle.getRotation())
                    .add("borderColor", colorToString(triangle.getBorderColor()))
                    .add("borderWidth", triangle.getBorderWidth());
            if (triangle.getFillColor() != null) {
                propertiesBuilder.add("fillColor", colorToString(triangle.getFillColor()));
            }
        } else if (shape instanceof Line) {
            Line line = (Line) shape;
            propertiesBuilder.add("x2", line.getX2())
                    .add("y2", line.getY2())
                    .add("lineColor", colorToString(line.getColor()))
                    .add("lineWidth", line.getBorderWidth());
        } else if (shape instanceof Diamond) {
            Diamond diamond = (Diamond) shape;
            propertiesBuilder.add("width", diamond.getWidth())
                    .add("height", diamond.getHeight())
                    .add("rotation", diamond.getRotation())
                    .add("borderColor", colorToString(diamond.getBorderColor()))
                    .add("borderWidth", diamond.getBorderWidth());
            if (diamond.getFillColor() != null) {
                propertiesBuilder.add("fillColor", colorToString(diamond.getFillColor()));
            }
        }

        return propertiesBuilder.build();
    }

    /**
     * Converts a Color object to a string representation in hexadecimal format.
     * If the color is null, returns a default color (white).
     *
     * @param color The Color object to be converted.
     * @return A string representing the color in hexadecimal format.
     */
    private String colorToString(Color color) {
        if (color == null) {
            return "white";
        } else {
            return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }
    }

    /**
     * Closes the network resources associated with this client.
     * This includes the input and output streams, as well as the socket connection.
     */
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing network resources: " + e.getMessage());
        }
    }
}