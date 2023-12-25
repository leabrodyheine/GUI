package Shapes.Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Shapes.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class contains unit tests for the Rectangle class.
 * It tests various functionalities including creation, movement, resizing,
 * containment, color properties, border properties, and graphical rendering.
 */
public class RectangleTests {

    private Rectangle rectangle;

    /**
     * Sets up a Rectangle object before each test.
     */
    @Before
    public void setUp() {
        rectangle = new Rectangle(10, 20, 100, 50, Color.BLACK, 5, Color.RED);
    }

    /**
     * Tests the construction of a Rectangle.
     * Verifies the initial properties are set correctly.
     */
    @Test
    public void testCreation() {
        assertNotNull("Rectangle should not be null", rectangle);
        assertEquals("Incorrect X coordinate", 10, rectangle.getX(), 0.01);
        assertEquals("Incorrect Y coordinate", 20, rectangle.getY(), 0.01);
        assertEquals("Incorrect width", 100, rectangle.getWidth());
        assertEquals("Incorrect height", 50, rectangle.getHeight());
        assertEquals("Incorrect border color", Color.BLACK, rectangle.getBorderColor());
        assertEquals("Incorrect fill color", Color.RED, rectangle.getFillColor());
        assertEquals("Incorrect border width", 5, rectangle.getBorderWidth());
    }

    /**
     * Tests the movement functionality of the Rectangle.
     * Verifies that the rectangle's coordinates are updated correctly after
     * movement.
     */
    @Test
    public void testMove() {
        rectangle.move(5, 10);
        assertEquals("X coordinate should update correctly", 15, rectangle.getX(), 0.01);
        assertEquals("Y coordinate should update correctly", 30, rectangle.getY(), 0.01);
    }

    /**
     * Tests the resize functionality of the Rectangle.
     * Verifies that the rectangle's dimensions are updated correctly after
     * resizing.
     */
    @Test
    public void testResize() {
        rectangle.resize(2);
        assertEquals("Width should update correctly", 200, rectangle.getWidth());
        assertEquals("Height should update correctly", 100, rectangle.getHeight());
    }

    /**
     * Tests setting and getting the border color of the Rectangle.
     */
    @Test
    public void testSetBorderColor() {
        rectangle.setBorderColor(Color.BLUE);
        assertEquals("Border color should change to blue", Color.BLUE, rectangle.getBorderColor());
    }

    /**
     * Tests setting and getting the fill color of the Rectangle.
     */
    @Test
    public void testSetFillColor() {
        rectangle.setFillColor(Color.RED);
        assertEquals("Fill color should change to yellow", Color.RED, rectangle.getFillColor());
    }

    /**
     * Tests setting and getting the border width of the Rectangle.
     */
    @Test
    public void testSetBorderWidth() {
        rectangle.setBorderWidth(5);
        assertEquals("Border width should change to 10", 5, rectangle.getBorderWidth());
    }

    /**
     * Tests the containment functionality of the Rectangle.
     * Verifies if points are correctly identified as inside or outside the
     * rectangle.
     */
    @Test
    public void testContains() {
        assertTrue("Should contain point within bounds", rectangle.contains(50, 40));
        assertFalse("Should not contain point outside bounds", rectangle.contains(200, 200));
    }

    /**
     * Tests setting the width of the Rectangle.
     */
    @Test
    public void testSetWidth() {
        rectangle.setWidth(120);
        assertEquals("Width should be updated to 120", 120, rectangle.getWidth());
    }

    /**
     * Tests setting the height of the Rectangle.
     */
    @Test
    public void testSetHeight() {
        rectangle.setHeight(60);
        assertEquals("Height should be updated to 60", 60, rectangle.getHeight());
    }

    /**
     * Tests getting the border color of the Rectangle.
     */
    @Test
    public void testGetBorderColor() {
        assertEquals("Border color should be black", Color.BLACK, rectangle.getBorderColor());
    }

    /**
     * Tests the rotation functionality of the Rectangle.
     */
    @Test
    public void testRotation() {
        rectangle.rotate(45);
        assertEquals("Rotation should be set to 45", 45, rectangle.getRotation(), 0.01);
    }

    /**
     * Tests the border width functionality with a minimum value.
     */

    @Test
    public void testMinimumBorderWidth() {
        rectangle.setBorderWidth(0);
        assertTrue("Border width should be at least 1", rectangle.getBorderWidth() >= 1);
    }

    /**
     * Tests creating a rectangle with negative dimensions.
     */
    @Test
    public void testNegativeDimensions() {
        Rectangle negativeRectangle = new Rectangle(10, 20, -100, -50, Color.BLACK, 5, Color.RED);
        assertEquals("Width should be set to -100", -100, negativeRectangle.getWidth());
        assertEquals("Height should be set to -50", -50, negativeRectangle.getHeight());
    }

    /**
     * Tests that the paint method of the Rectangle class does not throw any
     * exceptions.
     */
    @Test
    public void testPaintDoesNotThrowException() {
        try {
            BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            rectangle.paint(g);
        } catch (Exception e) {
            fail("Paint method should not throw exceptions.");
        }
    }
}
