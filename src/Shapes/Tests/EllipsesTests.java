package Shapes.Tests;

import org.junit.Before;
import org.junit.Test;

import Shapes.Ellipses;

import static org.junit.Assert.*;
import java.awt.Color;

/**
 * This class contains unit tests for the Ellipses class.
 * It tests various functionalities including creation, movement, resizing,
 * containment, and color properties for the Ellipses class.
 */
public class EllipsesTests {

    private Ellipses ellipse;

    /**
     * Sets up an Ellipse object for testing.
     * This method is called before each test to initialize an Ellipse object
     * with predefined parameters.
     */
    @Before
    public void setUp() {
        // Create an ellipse with predefined parameters
        ellipse = new Ellipses(10, 10, 100, 50, Color.RED, 5, Color.BLUE);
    }

    /**
     * Tests the creation of an Ellipse instance.
     * Verifies if the ellipse is correctly instantiated with specified properties.
     */
    @Test
    public void testCreation() {
        assertNotNull("Ellipse should not be null", ellipse);
        assertEquals("Incorrect X coordinate", 10, (int) ellipse.getX());
        assertEquals("Incorrect Y coordinate", 10, (int) ellipse.getY());
        assertEquals("Incorrect width", 100, (int) ellipse.getWidth());
        assertEquals("Incorrect height", 50, (int) ellipse.getHeight());
        assertEquals("Incorrect border color", Color.RED, ellipse.getBorderColor());
        assertEquals("Incorrect fill color", Color.BLUE, ellipse.getFillColor());
        assertEquals("Incorrect border width", 5, (int) ellipse.getBorderWidth());
    }

    /**
     * Tests the movement functionality of the Ellipse class.
     * Verifies that the ellipse's position is updated correctly after movement.
     */
    @Test
    public void testMove() {
        ellipse.move(5, 10);
        assertEquals("X coordinate should update correctly", 15, (int) ellipse.getX());
        assertEquals("Y coordinate should update correctly", 20, (int) ellipse.getY());
    }

    /**
     * Tests the resizing functionality of the Ellipse class.
     * Verifies that the ellipse's dimensions are updated correctly after resizing.
     */
    @Test
    public void testResize() {
        ellipse.resize(2);
        assertEquals("Width should double", 200, ellipse.getWidth());
        assertEquals("Height should double", 100, ellipse.getHeight());
    }

    /**
     * Tests setting and getting the border color of the Ellipse.
     * Verifies that the border color is set and retrieved correctly.
     */
    @Test
    public void testSetBorderColor() {
        ellipse.setBorderColor(Color.GREEN);
        assertEquals("Border color should change to green", Color.GREEN, ellipse.getBorderColor());
    }

    /**
     * Tests setting and getting the fill color of the Ellipse.
     * Verifies that the fill color is set and retrieved correctly.
     */
    @Test
    public void testSetFillColor() {
        ellipse.setFillColor(Color.BLUE);
        assertEquals("Fill color should change to yellow", Color.BLUE, ellipse.getFillColor());
    }

    /**
     * Tests setting and getting the border width of the Ellipse.
     * Verifies that the border width is set and retrieved correctly.
     */
    @Test
    public void testSetBorderWidth() {
        ellipse.setBorderWidth(5);
        assertEquals("Border width should change to 10", 5, ellipse.getBorderWidth());
    }

    /**
     * Tests the contains method of the Ellipse class for a point inside the
     * ellipse.
     * Verifies if points inside the ellipse are correctly identified.
     */
    @Test
    public void testContainsTrue() {
        assertTrue("Point inside ellipse should return true", ellipse.contains(60, 35));
    }

    /**
     * Tests the contains method of the Ellipse class for a point outside the
     * ellipse.
     * Verifies if points outside the ellipse are correctly identified.
     */
    @Test
    public void testContainsFalse() {
        assertFalse("Point outside ellipse should return false", ellipse.contains(200, 200));
    }

    /**
     * Tests setting the width and height of the Ellipse.
     * Verifies that width and height are set and retrieved correctly.
     */
    @Test
    public void testSetWidthHeight() {
        ellipse.setWidth(120);
        ellipse.setHeight(60);
        assertEquals("Width should be updated to 120", 120, ellipse.getWidth());
        assertEquals("Height should be updated to 60", 60, ellipse.getHeight());
    }

    /**
     * Tests setting negative values for the width and height of the Ellipse.
     * Verifies that negative values are handled as expected.
     */
    @Test
    public void testNegativeWidthHeight() {
        ellipse.setWidth(-50);
        ellipse.setHeight(-25);
        assertEquals("Width should be set to -50", -50, ellipse.getWidth());
        assertEquals("Height should be set to -25", -25, ellipse.getHeight());
    }

    /**
     * Tests changing the border and fill colors of the Ellipse.
     * Verifies that color changes are applied and retrieved correctly.
     */
    @Test
    public void testColorChange() {
        Color newBorderColor = new Color(255, 0, 0);
        Color newFillColor = new Color(0, 0, 255);
        ellipse.setBorderColor(newBorderColor);
        ellipse.setFillColor(newFillColor);
        assertEquals("Border color should be red", newBorderColor, ellipse.getBorderColor());
        assertEquals("Fill color should be blue", newFillColor, ellipse.getFillColor());
    }
}
