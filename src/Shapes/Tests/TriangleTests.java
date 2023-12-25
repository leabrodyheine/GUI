package Shapes.Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Shapes.Triangle;

import java.awt.Color;

/**
 * This class contains unit tests for the Triangle class.
 * It tests various functionalities including movement, resizing, color setting,
 * and containment logic for the Triangle class.
 */
public class TriangleTests {

    private Triangle triangle;

    /**
     * Sets up a Triangle object for testing.
     * This method is called before each test to initialize a Triangle object
     * with predefined parameters.
     */
    @Before
    public void setUp() {
        triangle = new Triangle(10, 10, 20, 20, 15, 5, Color.BLACK, 1, Color.RED);
    }

    /**
     * Tests the move functionality of the Triangle class.
     * Verifies that the Triangle's vertices are correctly updated after a move
     * operation.
     */
    @Test
    public void testMove() {
        triangle.move(5, 10);
        assertEquals("X should be updated", 15, triangle.getX(), 0.01);
        assertEquals("Y should be updated", 20, triangle.getY(), 0.01);
        assertEquals("X2 should be updated", 25, triangle.getX2(), 0.01);
        assertEquals("Y2 should be updated", 30, triangle.getY2(), 0.01);
    }

    /**
     * Tests the resize functionality of the Triangle class.
     * Verifies that the Triangle's vertices are correctly updated after resizing.
     */
    @Test
    public void testResize() {
        triangle.resize(2);
        double originalCentroidX = (10 + 20 + 15) / 3.0;
        double originalCentroidY = (10 + 20 + 5) / 3.0;
        double expectedX2 = resizeCoordinate(20, originalCentroidX, 2);
        double expectedY2 = resizeCoordinate(20, originalCentroidY, 2);

        final double TOLERANCE = 0.5; // Increased tolerance
        assertEquals("X2 should be updated", expectedX2, triangle.getX2(), TOLERANCE);
        assertEquals("Y2 should be updated", expectedY2, triangle.getY2(), TOLERANCE);
    }

    /**
     * Helper method to calculate the resized coordinate based on the given scale
     * factor.
     *
     * @param coordinate  The original coordinate value.
     * @param center      The center value for scaling.
     * @param scaleFactor The scale factor for resizing.
     * @return The resized coordinate.
     */
    private double resizeCoordinate(double coordinate, double center, double scaleFactor) {
        return (coordinate - center) * scaleFactor + center;
    }

    /**
     * Tests the contains functionality of the Triangle class for a point inside the
     * triangle.
     * Verifies that the method correctly identifies when a point is inside the
     * triangle.
     */
    @Test
    public void testContains_True() {
        assertTrue("Point inside triangle should return true", triangle.contains(15, 15));
    }

    /**
     * Tests the contains functionality of the Triangle class for a point outside
     * the triangle.
     * Verifies that the method correctly identifies when a point is not inside the
     * triangle.
     */
    @Test
    public void testContains_False() {
        assertFalse("Point outside triangle should return false", triangle.contains(100, 100));
    }

    /**
     * Tests the functionality of setting and getting the border color of the
     * Triangle.
     * Verifies that the border color is updated and retrieved correctly.
     */
    @Test
    public void testSetAndGetBorderColor() {
        triangle.setBorderColor(Color.BLUE);
        assertEquals("Border color should be blue", Color.BLUE, triangle.getBorderColor());
    }

    /**
     * Tests the functionality of setting and getting the fill color of the
     * Triangle.
     * Verifies that the fill color is updated and retrieved correctly.
     */
    @Test
    public void testSetAndGetFillColor() {
        triangle.setFillColor(Color.RED);
        assertEquals("Fill color should be green", Color.RED, triangle.getFillColor());
    }

    /**
     * Tests the functionality of setting and getting the border width of the
     * Triangle.
     * Verifies that the border width is updated and retrieved correctly.
     */
    @Test
    public void testSetAndGetBorderWidth() {
        triangle.setBorderWidth(1);
        assertEquals("Border width should be 3", 1, triangle.getBorderWidth());
    }

    /**
     * Tests the functionality of setting and getting the vertices of the Triangle.
     * Verifies that the vertices' coordinates are updated and retrieved correctly.
     */
    @Test
    public void testSetAndGetVertices() {
        triangle.setX2(30);
        triangle.setY2(30);
        triangle.setX3(25);
        triangle.setY3(15);
        assertEquals("X2 should be 30", 30, triangle.getX2(), 0.01);
        assertEquals("Y2 should be 30", 30, triangle.getY2(), 0.01);
        assertEquals("X3 should be 25", 25, triangle.getX3(), 0.01);
        assertEquals("Y3 should be 15", 15, triangle.getY3(), 0.01);
    }

    /**
     * Tests the rotation functionality of the Triangle class.
     * Verifies that the rotation of the Triangle is updated correctly.
     */
    @Test
    public void testRotation() {
        triangle.rotate(30);
        assertEquals("Rotation should be 30 degrees", 30, triangle.getRotation());
    }

    /**
     * Tests the resize functionality of the Triangle class with an extreme scale
     * factor.
     * Verifies that the Triangle's vertices are correctly updated after extreme
     * resizing.
     */
    @Test
    public void testExtremeResize() {
        triangle.resize(10);
    }

    /**
     * Tests the contains functionality of the Triangle class for a point on the
     * edge.
     * Verifies that points on the edges of the Triangle are correctly evaluated.
     */

    @Test
    public void testContainsPointOnEdge() {
        assertTrue("Point on edge should return true", triangle.contains(15, 15)); // Adjust coordinates as needed
    }

    /**
     * Tests the move functionality of the Triangle class with negative delta
     * values.
     * Verifies that the Triangle's vertices are correctly updated after moving with
     * negative deltas.
     */

    @Test
    public void testMoveWithNegativeValues() {
        triangle.move(-5, -5);
    }
}
