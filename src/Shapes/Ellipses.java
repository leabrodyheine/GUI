package Shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The Ellipses class extends the Shape class and represents an ellipse shape.
 * It includes methods for painting the ellipse, checking if a point is inside
 * it,
 * moving it, and resizing it.
 */
public class Ellipses extends Shape {
    private int width, height;
    private Color fillColor;
    private Color borderColor;
    private int borderWidth;

    /**
     * Constructs an Ellipses object with specified coordinates, dimensions, border
     * color, border width, and fill color.
     * 
     * @param x           The x-coordinate of the ellipse's center.
     * @param y           The y-coordinate of the ellipse's center.
     * @param width       The width of the ellipse.
     * @param height      The height of the ellipse.
     * @param borderColor The color of the ellipse's border.
     * @param borderWidth The width of the ellipse's border.
     * @param fillColor   The fill color of the ellipse.
     */
    public Ellipses(int x, int y, int width, int height, Color borderColor, int borderWidth,
            Color fillColor) {
        super(x, y, borderColor, borderWidth, fillColor);
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    /**
     * Paints the ellipse on the given graphics context.
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

        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fillOval(-width / 2, -height / 2, width, height);
        }

        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawOval(-width / 2, -height / 2, width, height);

        g2d.dispose();
    }

    /**
     * Checks if a given point is inside the ellipse.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is inside the ellipse, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        double dx = (x - (getX() + width / 2.0)) / (width / 2.0);
        double dy = (y - (getY() + height / 2.0)) / (height / 2.0);
        return (dx * dx + dy * dy) <= 1.0;
    }

    /**
     * Moves the ellipse by a specified delta in the x and y directions.
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
     * Resizes the ellipse by a specified scale factor.
     *
     * @param scaleFactor The factor by which to scale the ellipse.
     */
    @Override
    public void resize(double scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    // Getters and Setters
    /**
     * Returns the width of the ellipse.
     * 
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the ellipse.
     * 
     * @param width The new width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the ellipse.
     * 
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the ellipse.
     * 
     * @param height The new height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

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
     * Returns the border width of the ellipse.
     * 
     * @return The border width.
     */
    @Override
    public int getBorderWidth() {
        return super.getBorderWidth();
    }

    /**
     * Returns the border color.
     * 
     */
    public Color getBorderColor() {
        return borderColor;
    }

    
}
