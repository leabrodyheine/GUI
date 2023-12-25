package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * The Line class extends the Shape class and represents a line shape.
 * It includes methods for painting the line, checking if a point is near it,
 * moving it, and resizing it.
 */
public class Line extends Shape {
    private double x2, y2;
    private int width, height;
    private int borderWidth;
    private Color color;

    /**
     * Constructs a Line object with specified start and end coordinates, color, and
     * border width.
     * 
     * @param x           The x-coordinate of the start point of the line.
     * @param y           The y-coordinate of the start point of the line.
     * @param x2          The x-coordinate of the end point of the line.
     * @param y2          The y-coordinate of the end point of the line.
     * @param color       The color of the line.
     * @param borderWidth The width of the line's border.
     */
    public Line(double x, double y, double x2, double y2, Color color, int borderWidth2) {
        super(x, y, color, borderWidth2, color);
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.borderWidth = borderWidth2;
    }

    /**
     * Paints the line on the given graphics context.
     *
     * @param g The graphics context to paint on.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        double centerX = (getX() + x2) / 2;
        double centerY = (getY() + y2) / 2;

        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(getRotation()));
        g2d.translate(-centerX, -centerY);

        int intX1 = (int) Math.round(getX());
        int intY1 = (int) Math.round(getY());
        int intX2 = (int) Math.round(x2);
        int intY2 = (int) Math.round(y2);

        g2d.setColor(getColor());
        g2d.setStroke(new BasicStroke(this.borderWidth));
        g2d.drawLine(intX1, intY1, intX2, intY2);

        g2d.dispose();
    }

    /**
     * Resizes the line by a specified scale factor.
     *
     * @param scaleFactor The factor by which to scale the line.
     */
    @Override
    public void resize(double scaleFactor) {
        this.x2 = getX() + (this.x2 - getX()) * scaleFactor;
        this.y2 = getY() + (this.y2 - getY()) * scaleFactor;
    }

    /**
     * Checks if a given point is within a certain tolerance of the line.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is near the line, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        final double TOLERANCE = 5.0;
        double dist = Line2D.ptSegDist(getX(), getY(), x2, y2, x, y);
        return dist <= TOLERANCE;
    }

    /**
     * Moves the line by a specified delta in the x and y directions.
     *
     * @param deltaX The amount to move in the x-direction.
     * @param deltaY The amount to move in the y-direction.
     */
    @Override
    public void move(int deltaX, int deltaY) {
        setX(getX() + deltaX);
        setY(getY() + deltaY);
        x2 += deltaX;
        y2 += deltaY;
    }

    // Getters and setters
    /**
     * Returns the x-coordinate of the end point of the line.
     * 
     * @return The x-coordinate of the end point.
     */
    public double getX2() {
        return x2;
    }

    /**
     * Sets the x-coordinate of the end point of the line.
     * 
     * @param x2 The new x-coordinate of the end point.
     */
    public void setX2(double x2) {
        this.x2 = x2;
    }

    /**
     * Returns the y-coordinate of the end point of the line.
     * 
     * @return The y-coordinate of the end point.
     */
    public double getY2() {
        return y2;
    }

    /**
     * Sets the y-coordinate of the end point of the line.
     * 
     * @param y2 The new y-coordinate of the end point.
     */
    public void setY2(double y2) {
        this.y2 = y2;
    }

    /**
     * Sets the border width of the line.
     * 
     * @param borderWidth The new border width.
     */
    @Override
    public void setBorderWidth(int borderWidth) {
        super.setBorderWidth(borderWidth);
    }

    /**
     * Returns the border width of the line.
     * 
     * @return The border width.
     */
    @Override
    public int getBorderWidth() {
        return super.getBorderWidth();
    }

     /**
     * Returns the color of the line.
     * 
     * @return The color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color line.
     * 
     * @param color The new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
