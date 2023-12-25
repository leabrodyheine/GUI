package Shapes.Tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import Shapes.Line;

import static org.junit.Assert.*;

/**
 * This class contains unit tests for the Line class.
 * It tests various functionalities including creation, movement, resizing,
 * containment, color properties, and graphical rendering of the Line class.
 */
public class LineTests {

    private Line line;

    /**
     * Initializes a Line object before each test.
     */
    @Before
    public void setUp() {
        line = new Line(10, 10, 50, 50, Color.BLACK, 2);
    }

    /**
     * Tests the constructor of the Line class.
     * Verifies if the line is correctly instantiated with specified properties.
     */

    @Test
    public void constructorTest() {
        assertNotNull("Line object should not be null", line);
        assertEquals("Incorrect start X coordinate", 10, line.getX(), 0.01);
        assertEquals("Incorrect start Y coordinate", 10, line.getY(), 0.01);
        assertEquals("Incorrect end X coordinate", 50, line.getX2(), 0.01);
        assertEquals("Incorrect end Y coordinate", 50, line.getY2(), 0.01);
        assertEquals("Incorrect color", Color.BLACK, line.getColor());
        assertEquals("Incorrect border width", 2, line.getBorderWidth());
    }

    /**
     * Tests the movement functionality of the Line class.
     * Verifies that the line's start and end coordinates are updated correctly
     * after movement.
     */

    @Test
    public void moveTest() {
        line.move(5, 10);
        assertEquals("Start X coordinate should update correctly", 15, line.getX(), 0.01);
        assertEquals("Start Y coordinate should update correctly", 20, line.getY(), 0.01);
        assertEquals("End X coordinate should update correctly", 55, line.getX2(), 0.01);
        assertEquals("End Y coordinate should update correctly", 60, line.getY2(), 0.01);
    }

    /**
     * Tests the resizing functionality of the Line class.
     * Verifies that the line's end coordinates are updated correctly after
     * resizing.
     */
    @Test
    public void resizeTest() {
        line.resize(2);
        assertEquals("End X coordinate should update correctly", 90, line.getX2(), 0.01);
        assertEquals("End Y coordinate should update correctly", 90, line.getY2(), 0.01);
    }

    /**
     * Tests the contains method of the Line class for a point near the line.
     * Verifies if the line correctly identifies points near it.
     */
    @Test
    public void containsTestTrue() {
        assertTrue("Line should contain point near it", line.contains(30, 30));
    }

    /**
     * Tests the contains method of the Line class for a point far from the line.
     * Verifies if the line correctly identifies points not near it.
     */
    @Test
    public void containsTestFalse() {
        assertFalse("Line should not contain distant point", line.contains(100, 100));
    }

    /**
     * Tests setting the end X coordinate of the Line.
     * Verifies that the end X coordinate is set and retrieved correctly.
     */
    @Test
    public void setX2Test() {
        line.setX2(60);
        assertEquals("End X coordinate should be updated", 60, line.getX2(), 0.01);
    }

    /**
     * Tests setting the end Y coordinate of the Line.
     * Verifies that the end Y coordinate is set and retrieved correctly.
     */
    @Test
    public void setY2Test() {
        line.setY2(60);
        assertEquals("End Y coordinate should be updated", 60, line.getY2(), 0.01);
    }

    /**
     * Tests setting and getting the border width of the Line.
     * Verifies that the border width is set and retrieved correctly.
     */
    @Test
    public void setBorderWidthTest() {
        line.setBorderWidth(3);
        assertEquals("Border width should be updated", 3, line.getBorderWidth());
    }

    /**
     * Tests setting and getting the color of the Line.
     * Verifies that the color is set and retrieved correctly.
     */
    @Test
    public void setColorTest() {
        line.setColor(Color.RED);
        assertEquals("Color should be updated", Color.RED, line.getColor());
    }

    /**
     * Tests setting the end coordinates of the Line.
     * Verifies that the end coordinates are updated correctly.
     */
    @Test
    public void setEndCoordinatesTest() {
        line.setX2(70);
        line.setY2(70);
        assertEquals("End X coordinate should be updated", 70, line.getX2(), 0.01);
        assertEquals("End Y coordinate should be updated", 70, line.getY2(), 0.01);
    }

    /**
     * Tests the rotation functionality of the Line class.
     * Verifies that the rotation angle is set and retrieved correctly.
     */
    @Test
    public void testRotation() {
        line.rotate(45);
        assertEquals("Rotation should be set to 45", 45, line.getRotation(), 0.01);
    }

    /**
     * Tests the change of color in the Line class.
     * Verifies that the color change is applied and retrieved correctly.
     */
    @Test
    public void testColorChange() {
        line.setColor(Color.BLUE);
        assertEquals("Color should be updated to blue", Color.BLUE, line.getColor());
    }

    /**
     * Tests the change of border width in the Line class.
     * Verifies that the border width change is applied and retrieved correctly.
     */

    @Test
    public void testBorderWidthChange() {
        line.setBorderWidth(4);
        assertEquals("Border width should be updated to 4", 4, line.getBorderWidth());
    }

    /**
     * Tests the contains method of the Line class for a point near the line.
     * Verifies if the line correctly identifies points near it.
     */
    @Test
    public void testContainsNearLine() {
        assertTrue("Point near the line should return true", line.contains(25, 25));
    }

    /**
     * Tests the contains method of the Line class for a point on the line.
     * Verifies if the line correctly identifies points on it.
     */

    @Test
    public void testContainsOnLine() {
        assertTrue("Point on the line should return true", line.contains(30, 30));
    }

    /**
     * Tests that the paint method of the Line class does not throw any exceptions.
     */
    @Test
    public void testPaintDoesNotThrowException() {
        try {
            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            line.paint(g);
        } catch (Exception e) {
            fail("Paint method should not throw exceptions.");
        }
    }
}
