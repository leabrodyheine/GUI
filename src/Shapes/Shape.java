package Shapes;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The Shape class is an abstract class that provides a template for shapes in a
 * drawing application.
 * It includes common properties and methods applicable to all shapes, such as
 * position, color, and border properties.
 */
public abstract class Shape {

    protected double x;
    protected double y;
    protected Color fillColor;
    protected Color borderColor;
    protected int borderWidth;
    protected int rotation;
    protected String id;
    private Boolean isOwner;

    /**
     * Constructs a Shape object with specified coordinates, border properties, and
     * fill color.
     * 
     * @param x2          The x-coordinate of the shape.
     * @param y2          The y-coordinate of the shape.
     * @param borderColor The color of the shape's border.
     * @param borderWidth The width of the shape's border.
     * @param fillColor   The fill color of the shape.
     */
    public Shape(double x2, double y2, Color borderColor, int borderWidth, Color fillColor) {
        this.x = x2;
        this.y = y2;
        this.borderWidth = borderWidth;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    /**
     * Abstract method to paint the shape on a graphics context. Must be implemented
     * by all subclasses.
     * 
     * @param g The graphics context to paint on.
     */
    public abstract void paint(Graphics g);

    /**
     * Abstract method to resize the shape. Must be implemented by all subclasses.
     * 
     * @param d The factor by which the shape should be resized.
     */
    public abstract void resize(double d);

    /**
     * Moves the shape by a specified delta in the x and y directions.
     * 
     * @param deltaX The amount to move in the x-direction.
     * @param deltaY The amount to move in the y-direction.
     */
    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    /**
     * Checks if a given point is inside the shape. The default implementation
     * always returns false.
     * Subclasses should override this method with specific logic.
     * 
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is inside the shape, false otherwise.
     */
    public boolean contains(int x, int y) {
        return false;
    }

    /**
     * Rotates the shape by a given angle.
     * 
     * @param angle The angle in degrees by which the shape should be rotated.
     */
    public void rotate(int angle) {
        this.rotation += angle % 360;
    }

    /**
     * Returns the x-coordinate of the shape.
     * 
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the shape.
     * 
     * @param x The new x-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the shape.
     * 
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the shape.
     * 
     * @param y The new y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the fill color of the shape.
     * 
     * @return The fill color.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Returns the border color of the shape.
     * 
     * @return The border color.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Returns the border width of the shape.
     * 
     * @return The border width.
     */
    public int getBorderWidth() {
        return borderWidth;
    }

    /**
     * Sets the border width of the shape.
     * 
     * @param borderWidth The new border width.
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Returns the rotation angle of the shape.
     * 
     * @return The rotation angle in degrees.
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation angle of the shape.
     * 
     * @param rotation The new rotation angle in degrees.
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns the ID of the shape.
     * 
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the shape.
     * 
     * @param id The new ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the fill color of the shape.
     * 
     * @param newColor The new fill color.
     */
    public void setFillColor(Color newColor) {
        this.fillColor = newColor;
    }

    /**
     * Sets the border color of the shape.
     * 
     * @param borderColor The new border color.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Returns the is owner boolean.
     * 
     * @return The boolean.
     */
    public Boolean getIsOwner() {
        return isOwner;
    }

    /**
     * Sets the boolean.
     * 
     * @param borderColor The is owner boolean.
     */
    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }
}
