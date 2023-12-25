package Shapes.Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Shapes.Shape;

import java.awt.Color;

/**
 * This class contains unit tests for the abstract Shape class.
 * It tests various functionalities including movement, rotation, setting and
 * getting colors,
 * border properties, and the contains method. A ConcreteShape subclass is used
 * for testing.
 */
public class ShapesTests {

    private Shape shape;

    /**
     * A concrete implementation of the abstract Shape class for testing purposes.
     */
    private class ConcreteShape extends Shape {
        public ConcreteShape(double x, double y, Color borderColor, int borderWidth, Color fillColor) {
            super(x, y, borderColor, borderWidth, fillColor);
        }

        @Override
        public void paint(java.awt.Graphics g) {
        }

        @Override
        public void resize(double d) {
        }
    }

    /**
     * Initializes a ConcreteShape object before each test.
     */
    @Before
    public void setUp() {
        shape = new ConcreteShape(10, 20, Color.BLACK, 5, Color.RED);
    }

    /**
     * Tests the movement functionality of the Shape.
     */
    @Test
    public void testMove() {
        shape.move(5, 10);
        assertEquals("X should be updated", 15, shape.getX(), 0.01);
        assertEquals("Y should be updated", 30, shape.getY(), 0.01);
    }

    /**
     * Tests the rotation functionality of the Shape.
     */
    @Test
    public void testRotate() {
        shape.rotate(45);
        assertEquals("Rotation should be 45", 45, shape.getRotation());
    }

    /**
     * Tests setting and getting the fill color of the Shape.
     */
    @Test
    public void testSetAndGetFillColor() {
        shape.setFillColor(Color.BLUE);
        assertEquals("Fill color should be blue", Color.BLUE, shape.getFillColor());
    }

    /**
     * Tests setting and getting the border color of the Shape.
     */
    @Test
    public void testSetAndGetBorderColor() {
        shape.setBorderColor(Color.GREEN);
        assertEquals("Border color should be green", Color.GREEN, shape.getBorderColor());
    }

    /**
     * Tests setting and getting the border width of the Shape.
     */
    @Test
    public void testSetAndGetBorderWidth() {
        shape.setBorderWidth(10);
        assertEquals("Border width should be 10", 10, shape.getBorderWidth());
    }

    /**
     * Tests setting and getting the rotation of the Shape.
     */
    @Test
    public void testSetAndGetRotation() {
        shape.setRotation(90);
        assertEquals("Rotation should be 90", 90, shape.getRotation());
    }

    /**
     * Tests setting and getting the ID of the Shape.
     */
    @Test
    public void testSetAndGetID() {
        shape.setId("shape1");
        assertEquals("ID should be 'shape1'", "shape1", shape.getId());
    }

    /**
     * Tests setting and getting the coordinates of the Shape.
     */

    @Test
    public void testSetAndGetCoordinates() {
        shape.setX(30);
        shape.setY(40);
        assertEquals("X should be 30", 30, shape.getX(), 0.01);
        assertEquals("Y should be 40", 40, shape.getY(), 0.01);
    }

    /**
     * Tests the default contains behavior of the Shape.
     */

    @Test
    public void testContainsDefaultBehavior() {
        assertFalse("Default contains implementation should return false", shape.contains(0, 0));
    }

    /**
     * Tests setting and getting the ownership status of the Shape.
     */
    @Test
    public void testSetAndGetIsOwner() {
        shape.setIsOwner(true);
        assertTrue("IsOwner should be true", shape.getIsOwner());
        shape.setIsOwner(false);
        assertFalse("IsOwner should be false", shape.getIsOwner());
    }

    /**
     * Tests the movement functionality of the Shape with negative values.
     */
    @Test
    public void testMoveWithNegativeValues() {
        shape.move(-5, -10);
        assertEquals("X should be updated", 5, shape.getX(), 0.01);
        assertEquals("Y should be updated", 10, shape.getY(), 0.01);
    }

    /**
     * Tests rotation functionality with a large rotation value.
     */
    @Test
    public void testLargeRotation() {
        shape.rotate(720); // Two full rotations
        assertEquals("Rotation should be 720", 720 % 360, shape.getRotation());
    }
}
