package Model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import Shapes.Shape;
import Shapes.Triangle;
import Shapes.DrawType;
import Shapes.Ellipses;
import Shapes.Rectangle;

/**
 * This class contains unit tests for the Model class.
 * It tests the functionality of adding, removing, undoing, redoing, and
 * modifying shapes.
 */
public class ModelTest {

    private Model model;

    /**
     * Sets up a new Model instance before each test.
     */
    @Before
    public void setUp() {
        model = new Model();
    }

    /**
     * Tests the getUpdatedShapesList method if its empty.
     */
    @Test
    public void testGetUpdatedShapesList_Empty() {
        assertTrue("List should be empty initially", model.getUpdatedShapesList().isEmpty());
    }

    /**
     * Tests the getUpdatedShapesList method if it has shapes.
     */
    @Test
    public void testGetUpdatedShapesList_WithShapes() {
        model.addShape(new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE));
        assertEquals("List should contain one shape", 1, model.getUpdatedShapesList().size());
    }

    /**
     * Tests adding a single shape to the model.
     * Verifies that the shape is correctly added.
     */
    @Test
    public void testAddShape() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(shape);
        assertEquals("Model should have one shape after adding", 1, model.getShapes().size());
    }

    /**
     * Tests adding multiple shapes to the model.
     * Verifies that all shapes are correctly added and tracked by the model.
     */
    @Test
    public void testAddMultipleShapes() {
        Shape rect = new Rectangle(10, 10, 50, 50, Color.BLACK, 1, Color.WHITE);
        Shape ellipse = new Ellipses(20, 20, 30, 40, Color.BLUE, 2, null);
        model.addShape(rect);
        model.addShape(ellipse);
        assertEquals("Model should have two shapes", 2, model.getShapes().size());
    }

    /**
     * Tests adding a shape from the server.
     */
    @Test
    public void testAddShapeFromServer() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        model.addShapeFromServer(shape);
        assertTrue("Shape from server should be added to the list", model.getShapesFromServer().contains(shape));
    }

    /**
     * Tests the addition of multiple shapes from the server to the model.
     * Verifies that the model correctly tracks shapes received from the server.
     */
    @Test
    public void testAddMultipleShapesFromServer() {
        Shape rect = new Rectangle(10, 10, 50, 50, Color.BLACK, 1, Color.WHITE);
        Shape ellipse = new Ellipses(20, 20, 30, 40, Color.BLUE, 2, null);
        model.addShapeFromServer(rect);
        model.addShapeFromServer(ellipse);
        assertEquals("Model should have two shapes from server", 2, model.getShapesFromServer().size());
    }

    /**
     * Tests the undo functionality in the model.
     * Verifies that the last added shape is removed from the model.
     */
    @Test
    public void testUndo() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(shape);
        model.undo();
        assertTrue("Model should have no shapes after undo", model.getShapes().isEmpty());
    }

    /**
     * Tests the undo operation when there is no shape to undo.
     */
    @Test
    public void testUndo_NoShape() {
        model.undo();
        assertTrue("Undo should have no effect when no shapes have been added", model.getShapes().isEmpty());
    }

    /**
     * Tests adding multiple shapes and performing undo operations.
     */
    @Test
    public void testUndo_MultipleShapes() {
        Shape shape1 = new Rectangle(0, 0, 50, 50, Color.BLACK, 1, Color.WHITE);
        Shape shape2 = new Rectangle(10, 10, 60, 60, Color.RED, 2, Color.GREEN);
        model.addShape(shape1);
        model.addShape(shape2);

        model.undo();
        assertEquals("Model should have one shape after one undo", 1, model.getShapes().size());
        assertTrue("Model should contain the first shape", model.getShapes().contains(shape1));

        model.undo();
        assertTrue("Model should have no shapes after two undos", model.getShapes().isEmpty());
    }

    /**
     * Tests the functionality of undoing an action after performing a redo.
     */
    @Test
    public void testUndoAfterRedo() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        model.addShape(shape);
        model.undo();
        model.redo();
        model.undo();
        assertTrue("Model should have no shapes after undoing the redo", model.getShapes().isEmpty());
    }

    /**
     * Tests the redo operation when there is no shape to redo.
     */
    @Test
    public void testRedo_NoShape() {
        model.redo();
        assertTrue("Redo should have no effect when no shapes have been undone", model.getShapes().isEmpty());
    }

    /**
     * Tests the redo functionality in the model.
     * Verifies that an undone shape addition is redone correctly.
     */

    @Test
    public void testRedo() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(shape);
        model.undo();
        model.redo();
        assertEquals("Model should have one shape after redo", 1, model.getShapes().size());
    }

    /**
     * Tests performing redo after multiple undos.
     */
    @Test
    public void testRedo_AfterMultipleUndos() {
        Shape shape1 = new Rectangle(0, 0, 50, 50, Color.BLACK, 1, Color.WHITE);
        Shape shape2 = new Rectangle(10, 10, 60, 60, Color.RED, 2, Color.GREEN);
        model.addShape(shape1);
        model.addShape(shape2);

        model.undo();
        model.undo();
        model.redo();
        assertEquals("Model should have one shape after redo", 1, model.getShapes().size());
        assertTrue("Model should contain the first shape after redo", model.getShapes().contains(shape1));
    }

    /**
     * Tests the functionality of changing the border color of a shape.
     */
    @Test
    public void testChangeShapeBorderColor() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        model.addShape(shape);
        model.changeShapeBorderColor(shape, Color.GREEN);
        assertEquals("Border color of shape should be changed to GREEN", Color.GREEN, shape.getBorderColor());
    }

    /**
     * Tests the functionality of changing the fill color of a shape.
     * Verifies that an attempt to change the fill color does not affect the shape.
     */
    @Test
    public void testChangeShapeFillColor() {
        Color initialFillColor = Color.BLUE;
        Color attemptedNewFillColor = Color.YELLOW;
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, initialFillColor);
        model.addShape(shape);
        model.changeShapeFillColor(shape, attemptedNewFillColor);
        assertEquals("Fill color of shape should remain unchanged", initialFillColor, shape.getFillColor());
    }

    /**
     * Tests the functionality of changing colors for different shapes.
     */
    @Test
    public void testChangeColorOfDifferentShapes() {
        Shape rect = new Rectangle(10, 10, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(rect);
        model.changeShapeFillColor(rect, Color.GREEN);
        assertEquals("Rectangle fill color should remain WHITE", Color.WHITE, rect.getFillColor());

        Shape ellipse = new Ellipses(20, 20, 50, 50, Color.BLACK, 1, Color.WHITE);
        model.addShape(ellipse);
        model.changeShapeFillColor(ellipse, Color.BLUE);
        assertEquals("Ellipse fill color should remain WHITE", Color.WHITE, ellipse.getFillColor());

        Shape triangle = new Triangle(30, 30, 40, 40, 35, 25, Color.BLACK, 1, Color.WHITE);
        model.addShape(triangle);
        model.changeShapeFillColor(triangle, Color.RED);
        assertEquals("Triangle fill color should remain WHITE", Color.WHITE, triangle.getFillColor());
    }

    /**
     * Tests the functionality of changing the border width of a shape.
     */
    @Test
    public void testChangeShapeBorderWidth() {
        int initialBorderWidth = 2;
        int attemptedNewBorderWidth = 5;
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, initialBorderWidth, Color.BLUE);
        model.addShape(shape);
        model.changeShapeBorderWidth(shape, attemptedNewBorderWidth);
        assertEquals("Border width of shape should remain unchanged", initialBorderWidth, shape.getBorderWidth());
    }

    /**
     * Tests changing the border width with an invalid value.
     */
    @Test
    public void testInvalidBorderWidth() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        model.addShape(shape);
        model.changeShapeBorderWidth(shape, -1);
        assertEquals("Border width should remain unchanged for invalid input", 2, shape.getBorderWidth());
    }

    /**
     * Tests the functionality of rotating a shape with a valid ID.
     */
    @Test
    public void testRotateShape_ValidShape() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        String shapeId = "1"; // Use a string as ID
        shape.setId(shapeId); // Set the ID
        model.addShape(shape);
        model.rotateShape(shapeId, 90); // Use the string ID
        assertEquals("Shape should be rotated by 90 degrees", 90, shape.getRotation());
    }

    /**
     * Tests rotating a shape with a negative angle.
     */
    @Test
    public void testRotateShapeNegativeAngle() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        String shapeId = "1";
        shape.setId(shapeId);
        model.addShape(shape);
        model.rotateShape(shapeId, -45);
        assertEquals("Shape should be rotated by -45 degrees", -45, shape.getRotation());
    }

    /**
     * Tests rotating a shape beyond 360 degrees and verifying modular rotation.
     */
    @Test
    public void testRotateShapeOver360Degrees() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        String shapeId = "1";
        shape.setId(shapeId);
        model.addShape(shape);
        model.rotateShape(shapeId, 450);
        int expectedRotation = 450 % 360; // Expecting modular rotation
        assertEquals("Shape should be rotated by 90 degrees (450 % 360)", expectedRotation, shape.getRotation());
    }

    /**
     * Tests the functionality of rotating a shape with an invalid ID.
     */
    @Test
    public void testRotateShape_InvalidShape() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        String validShapeId = "1";
        shape.setId(validShapeId);
        model.addShape(shape);
        int originalRotation = shape.getRotation();

        String invalidShapeId = "99";
        model.rotateShape(invalidShapeId, 90);

        assertEquals("Rotation of existing shape should not change", originalRotation, shape.getRotation());
    }

    /**
     * Tests selecting a shape with a valid ID.
     */
    @Test
    public void testSelectShape_ValidShape() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        String shapeId = "1"; // Using a String for the shape ID
        shape.setId(shapeId);
        model.addShape(shape);
        model.selectShape(shapeId);
        assertEquals("Selected shape should be the one with ID " + shapeId, shape, model.getSelectedShape());
    }

    /**
     * Tests re-selecting a different shape.
     */
    @Test
    public void testReSelectingShape() {
        Shape shape1 = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        Shape shape2 = new Ellipses(20, 20, 30, 40, Color.GREEN, 3, null);
        String shapeId1 = "1"; // Using String for the shape ID
        String shapeId2 = "2"; // Using String for the shape ID
        shape1.setId(shapeId1);
        shape2.setId(shapeId2);
        model.addShape(shape1);
        model.addShape(shape2);
        model.selectShape(shapeId1);
        model.selectShape(shapeId2);
        model.selectShape(shapeId1);
        assertEquals("Selected shape should be shape1 again", shape1, model.getSelectedShape());
    }

    /**
     * Tests selecting a shape with an invalid ID.
     */
    @Test
    public void testSelectShape_InvalidShape() {
        String invalidShapeId = "invalid_id"; // Using a String that does not correspond to any shape
        model.selectShape(invalidShapeId);
        assertNull("Selected shape should be null for invalid ID", model.getSelectedShape());
    }

    /**
     * Tests the functionality of removing a shape from the model.
     */
    @Test
    public void testRemoveShape() {
        Shape shape1 = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        model.addShape(shape1);
        model.removeShape(shape1);
        assertFalse("Model should not contain the removed shape", model.getShapes().contains(shape1));
    }

    /**
     * Tests removing a shape that is not present in the model.
     */
    @Test
    public void testRemoveShapeNotInModel() {
        Shape shape1 = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        Shape shape2 = new Rectangle(20, 20, 50, 50, Color.GREEN, 3, Color.YELLOW); // Not added to model
        model.addShape(shape1);
        model.removeShape(shape2);
        assertTrue("Model should still contain shape1", model.getShapes().contains(shape1));
        assertEquals("Model should have only one shape", 1, model.getShapes().size());
    }

    /**
     * Tests removing multiple shapes from the model.
     */
    @Test
    public void testRemoveMultipleShapes() {
        Shape shape1 = new Rectangle(10, 10, 100, 100, Color.RED, 2, Color.BLUE);
        Shape shape2 = new Ellipses(20, 20, 30, 40, Color.GREEN, 3, null);
        model.addShape(shape1);
        model.addShape(shape2);
        model.removeShape(shape1);
        model.removeShape(shape2);
        assertTrue("Model should have no shapes after removing both shapes", model.getShapes().isEmpty());
    }

    /**
     * Tests setting the fill property of shapes.
     */
    @Test
    public void testSetFill_True() {
        model.setFill(true);
        assertTrue("Fill should be set to true", model.getFill());
    }

    /**
     * Tests setting the fill property of shapes.
     */
    @Test
    public void testSetFill_False() {
        model.setFill(false);
        assertFalse("Fill should be set to false", model.getFill());
    }

    /**
     * Tests the setCurrentShapeType method.
     * line
     */
    @Test
    public void testSetCurrentShapeType_Line() {
        model.setCurrentShapeType(DrawType.line);
        assertEquals("Current shape type should be LINE", DrawType.line, model.getCurrentShapeType());
    }

    /**
     * Tests the setCurrentShapeType method.
     * rectangle
     */
    @Test
    public void testSetCurrentShapeType_Rectangle() {
        model.setCurrentShapeType(DrawType.rectangle);
        assertEquals("Current shape type should be RECTANGLE", DrawType.rectangle, model.getCurrentShapeType());
    }

    /**
     * Tests the notifyShapeChanged method.
     */
    @Test
    public void testNotifyShapeChanged() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(shape);
        model.notifyShapeChanged(shape);
        assertTrue("Model should still contain the shape", model.getShapes().contains(shape));
    }

    /**
     * Tests the shapeUpdated method.
     */
    @Test
    public void testShapeUpdated() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(shape);
    }

    /**
     * Tests retrieving the fill property status from the model.
     */
    @Test
    public void testGetFill() {
        model.setFill(true);
        assertTrue("getFill should return true when set to true", model.getFill());
        model.setFill(false);
        assertFalse("getFill should return false when set to false", model.getFill());
    }

    /**
     * Tests the getCurrentColor method.
     */
    @Test
    public void testGetCurrentColor() {
        Color testColor = Color.BLUE;
        model.setCurrentColor(testColor);
        assertEquals("getCurrentColor should return the set color", testColor, model.getCurrentColor());
    }

    /**
     * Tests setting and getting the current color in the model.
     */
    @Test
    public void testSetCurrentColor() {
        Color newColor = Color.MAGENTA;
        model.setCurrentColor(newColor);
        assertEquals("Current color should be set to MAGENTA", newColor, model.getCurrentColor());
    }

    /**
     * Tests the getCurrentShapeType method.
     */
    @Test
    public void testGetCurrentShapeType() {
        model.setCurrentShapeType(DrawType.rectangle);
        assertEquals("getCurrentShapeType should return RECTANGLE", DrawType.rectangle, model.getCurrentShapeType());
        model.setCurrentShapeType(DrawType.ellipse);
        assertEquals("getCurrentShapeType should return ELLIPSE", DrawType.ellipse, model.getCurrentShapeType());
    }

    /**
     * Tests the setCurrentShapeType method with a null value.
     */
    @Test
    public void testSetCurrentShapeType_Null() {
        model.setCurrentShapeType(null);
        assertNull("Current shape type should be null when set to null", model.getCurrentShapeType());
    }

    /**
     * Tests setting a null color as the current color.
     */
    @Test
    public void testSetCurrentColor_Null() {
        model.setCurrentColor(null);
        assertNull("Current color should be null when set to null", model.getCurrentColor());
    }

    /**
     * Tests the getShapes method.
     * empty
     */
    @Test
    public void testGetShapes_Empty() {
        assertTrue("getShapes should return an empty list for a new model", model.getShapes().isEmpty());
    }

    /**
     * Tests the getShapes method.
     * with shapes
     */
    @Test
    public void testGetShapes_WithShapes() {
        Shape shape = new Rectangle(0, 0, 100, 100, Color.BLACK, 1, Color.WHITE);
        model.addShape(shape);
        assertEquals("getShapes should return a list with one shape", 1, model.getShapes().size());
        assertTrue("The list should contain the added shape", model.getShapes().contains(shape));
    }

    /**
     * Tests retrieval of shapes added from the server.
     */
    @Test
    public void testGetShapesFromServer() {
        Shape shapeFromServer = new Rectangle(20, 20, 50, 50, Color.GREEN, 1, Color.YELLOW);
        model.addShapeFromServer(shapeFromServer);
        assertTrue("List should contain the shape added from the server",
                model.getShapesFromServer().contains(shapeFromServer));
    }

    /**
     * Tests the getShapesFromServer method when no shapes are added from the
     * server.
     */
    @Test
    public void testGetShapesFromServer_Empty() {
        assertTrue("List of shapes from server should be empty initially", model.getShapesFromServer().isEmpty());
    }
}
