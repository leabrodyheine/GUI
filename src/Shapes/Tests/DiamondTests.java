package Shapes.Tests;

import org.junit.Before;
import org.junit.Test;

import Shapes.Diamond;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * This class contains unit tests for the Diamond class.
 * It tests various functionalities including containment checks, movement,
 * resizing, color and dimension properties for the Diamond class.
 */
public class DiamondTests {
    private Diamond diamond;

    /**
     * Sets up a Diamond object for testing.
     * This method is called before each test to initialize a Diamond object
     * with predefined parameters.
     */
    @Before
    public void setUp() {
        diamond = new Diamond(10, 10, 50, 30, Color.RED, 2, Color.BLUE);
    }

    /**
     * Tests the containment logic of the Diamond class for a point inside the
     * diamond.
     * Verifies that the point within the diamond returns true.
     */
    @Test
    public void testContainsPointInside() {
        assertTrue("Point inside the diamond should return true", diamond.contains(30, 20));
    }

    /**
     * Tests the containment logic of the Diamond class for a point inside the
     * diamond.
     * Verifies that the point within the diamond returns true.
     */
    @Test
    public void testContainsPointOutside() {
        assertFalse("Point outside the diamond should return false", diamond.contains(0, 0));
    }

    /**
     * Tests the movement functionality of the Diamond class.
     * Verifies that the diamond's position is updated correctly after movement.
     */
    @Test
    public void testMove() {
        diamond.move(5, 10);
        assertEquals("X position should be updated", 15, diamond.getX(), 0);
        assertEquals("Y position should be updated", 20, diamond.getY(), 0);
    }

    /**
     * Tests the movement functionality of the Diamond class with negative delta
     * values.
     * Verifies that the diamond's position is correctly updated with negative
     * movements.
     */

    @Test
    public void testMoveNegative() {
        diamond.move(-5, -10);
        assertEquals("X position should be updated", 5, diamond.getX(), 0);
        assertEquals("Y position should be updated", 0, diamond.getY(), 0);
    }

    /**
     * Tests the resizing functionality of the Diamond class to increase its size.
     * Verifies that the diamond's dimensions are doubled correctly.
     */

    @Test
    public void testResizeIncrease() {
        diamond.resize(2);
        assertEquals("Width should double", 100, diamond.getWidth());
        assertEquals("Height should double", 60, diamond.getHeight());
    }

    /**
     * Tests the resizing functionality of the Diamond class to decrease its size.
     * Verifies that the diamond's dimensions are halved correctly.
     */
    @Test
    public void testResizeDecrease() {
        diamond.resize(0.5);
        assertEquals("Width should halve", 25, diamond.getWidth());
        assertEquals("Height should halve", 15, diamond.getHeight());
    }

    /**
     * Tests setting and getting the border color of the Diamond.
     * Verifies that the border color is set and retrieved correctly.
     */
    @Test
    public void testSetBorderColor() {
        diamond.setBorderColor(Color.GREEN);
        assertEquals("Border color should be set to green", Color.GREEN, diamond.getBorderColor());
    }

    /**
     * Tests setting and getting the fill color of the Diamond.
     * Verifies that the fill color is set and retrieved correctly.
     */
    @Test
    public void testSetFillColor() {
        diamond.setFillColor(Color.BLUE);
        assertEquals("Fill color should be set to yellow", Color.BLUE, diamond.getFillColor());
    }

    /**
     * Tests setting and getting the border width of the Diamond.
     * Verifies that the border width is set and retrieved correctly.
     */
    @Test
    public void testSetBorderWidth() {
        diamond.setBorderWidth(5);
        assertEquals("Border width should be 5", 5, diamond.getBorderWidth());
    }

    /**
     * Tests the getWidth method of the Diamond class.
     * Verifies that the method returns the correct width of the diamond.
     */
    @Test
    public void testGetWidth() {
        assertEquals("Width should be 50", 50, diamond.getWidth());
    }

    /**
     * Tests the getHeight method of the Diamond class.
     * Verifies that the method returns the correct height of the diamond.
     */
    @Test
    public void testGetHeight() {
        assertEquals("Height should be 30", 30, diamond.getHeight());
    }

    /**
     * Tests the getBorderColor method of the Diamond class.
     * Verifies that the method returns the correct color set for the diamond's
     * border.
     */
    @Test
    public void testGetBorderColor() {
        assertEquals("Border color should be red", Color.RED, diamond.getBorderColor());
    }

    /**
     * Tests the getFillColor method of the Diamond class.
     * Verifies that the method returns the correct color set for the diamond's
     * fill.
     */
    @Test
    public void testGetFillColor() {
        assertEquals("Fill color should be blue", Color.BLUE, diamond.getFillColor());
    }

    /**
     * Tests the rotation functionality of the Diamond class.
     * Verifies that the rotation of the diamond is updated correctly.
     */
    @Test
    public void testRotation() {
        diamond.rotate(45);
        assertEquals("Rotation should be 45 degrees", 45, diamond.getRotation());
    }

    /**
     * Tests the contains functionality of the Diamond class for a point on the
     * edge.
     * Verifies that points on the edges of the diamond are correctly evaluated.
     */
    @Test
    public void testContainsPointOnEdge() {
        assertTrue("Point on the edge of the diamond should return true", diamond.contains(30, 10));
    }

    /**
     * Tests that the paint method of the Diamond class does not throw exceptions.
     * Verifies that the method executes correctly with a given graphics context.
     */
    @Test
    public void testPaintDoesNotThrowException() {
        try {
            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            diamond.paint(g);
        } catch (Exception e) {
            fail("Paint method should not throw exceptions.");
        }
    }

    /**
     * Tests the initial width and height values of the Diamond class.
     * Verifies that the dimensions match the values provided during construction.
     */
    @Test
    public void testInitialWidthAndHeight() {
        assertEquals("Initial width should match constructor", 50, diamond.getWidth());
        assertEquals("Initial height should match constructor", 30, diamond.getHeight());
    }

}
