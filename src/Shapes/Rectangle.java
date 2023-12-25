package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The Rectangle class extends the Shape class and represents a rectangle shape.
 * It includes methods for painting the rectangle, checking if a point is inside
 * it,
 * moving it, and resizing it.
 */
public class Rectangle extends Shape {
    private int width, height;
    private Color fillColor;
    private Color borderColor;
    private int borderWidth;

    /**
     * Constructs a Rectangle object with specified coordinates, dimensions, border
     * color, border width, and fill color.
     * 
     * @param x           The x-coordinate of the rectangle's top-left corner.
     * @param y           The y-coordinate of the rectangle's top-left corner.
     * @param width       The width of the rectangle.
     * @param height      The height of the rectangle.
     * @param borderColor The color of the rectangle's border.
     * @param borderWidth The width of the rectangle's border.
     * @param fillColor   The fill color of the rectangle.
     */
    public Rectangle(double x, double y, int width, int height, Color borderColor, int borderWidth, Color fillColor) {
        super(x, y, borderColor, borderWidth, fillColor);
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    /**
     * Paints the rectangle on the given graphics context.
     *
     * @param g The graphics context to paint on.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        double centerX = getX() + width / 2;
        double centerY = getY() + height / 2;

        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(getRotation()));
        g2d.translate(-width / 2, -height / 2);

        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fillRect(0, 0, width, height);
        }

        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRect(0, 0, width, height);

        g2d.dispose();
    }

    /**
     * Checks if a given point is inside the rectangle.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is inside the rectangle, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        return x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height;
    }

    /**
     * Moves the rectangle by a specified delta in the x and y directions.
     *
     * @param deltaX The amount to move in the x-direction.
     * @param deltaY The amount to move in the y-direction.
     */
    @Override
    public void move(int deltaX, int deltaY) {
        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }

    /**
     * Resizes the rectangle by a specified scale factor.
     *
     * @param scaleFactor The factor by which to scale the rectangle.
     */
    @Override
    public void resize(double scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    // Getters and Setters
    /**
     * Sets the border color.
     * 
     * @param borderColor The new border color.
     */
    @Override
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Sets the fill color.
     * 
     * @param fillColor The new fill color.
     */
    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Sets the border width.
     * 
     * @param borderWidth The new border width.
     */
    @Override
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Returns the width of the rectangle.
     * 
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the rectangle.
     * 
     * @param width The new width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the rectangle.
     * 
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle.
     * 
     * @param height The new height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the border color.
     * 
     */
    public Color getBorderColor() {
        return borderColor;
    }
}
