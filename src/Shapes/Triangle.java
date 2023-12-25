package Shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The Triangle class extends the Shape class and represents a triangle shape.
 * It includes methods for painting the triangle, checking if a point is inside
 * it,
 * moving it, and resizing it.
 */
public class Triangle extends Shape {
    private double x2, y2, x3, y3;
    private Color borderColor;
    private int borderWidth;
    private Color fillColor;

    /**
     * Constructs a Triangle object with specified coordinates, border color, border
     * width, and fill color.
     *
     * @param x           The x-coordinate of the first vertex.
     * @param y           The y-coordinate of the first vertex.
     * @param x2          The x-coordinate of the second vertex.
     * @param y2          The y-coordinate of the second vertex.
     * @param x3          The x-coordinate of the third vertex.
     * @param y3          The y-coordinate of the third vertex.
     * @param borderColor The color of the triangle's border.
     * @param borderWidth The width of the triangle's border.
     * @param fillColor   The fill color of the triangle.
     */
    public Triangle(double x, double y, double x2, double y2, double x3, double y3, Color borderColor,
            int borderWidth, Color fillColor) {
        super(x, y, borderColor, borderWidth, fillColor);
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        this.fillColor = fillColor;
    }

    /**
     * Paints the triangle on the given graphics context.
     *
     * @param g The graphics context to paint on.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        double centroidX = (getX() + x2 + x3) / 3.0;
        double centroidY = (getY() + y2 + y3) / 3.0;

        g2d.translate(centroidX, centroidY);
        g2d.rotate(Math.toRadians(getRotation()));

        int[] xPoints = { (int) Math.round(getX() - centroidX), (int) Math.round(x2 - centroidX),
                (int) Math.round(x3 - centroidX) };
        int[] yPoints = { (int) Math.round(getY() - centroidY), (int) Math.round(y2 - centroidY),
                (int) Math.round(y3 - centroidY) };

        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawPolygon(xPoints, yPoints, 3);

        g2d.dispose();
    }

    /**
     * Checks if a given point is inside the triangle.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is inside the triangle, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        double x1 = getX(), y1 = getY(), x2 = this.x2, y2 = this.y2, x3 = this.x3, y3 = this.y3;
        double denominator = ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
        double a = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / denominator;
        double b = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / denominator;
        double c = 1 - a - b;
        return 0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1;
    }

    /**
     * Moves the triangle by a specified delta in the x and y directions.
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
        x3 += deltaX;
        y3 += deltaY;
    }

    /**
     * Resizes the triangle by a specified scale factor.
     *
     * @param scaleFactor The factor by which to scale the triangle.
     */
    @Override
    public void resize(double scaleFactor) {
        double cx = (getX() + x2 + x3) / 3;
        double cy = (getY() + y2 + y3) / 3;

        setX(resizeCoordinate(getX(), cx, scaleFactor));
        setY(resizeCoordinate(getY(), cy, scaleFactor));
        x2 = resizeCoordinate(x2, cx, scaleFactor);
        y2 = resizeCoordinate(y2, cy, scaleFactor);
        x3 = resizeCoordinate(x3, cx, scaleFactor);
        y3 = resizeCoordinate(y3, cy, scaleFactor);
    }

    /**
     * Helper method to resize a coordinate based on a scale factor and a center
     * point.
     *
     * @param coordinate  The coordinate to resize.
     * @param center      The center point for resizing.
     * @param scaleFactor The scale factor.
     * @return The resized coordinate.
     */
    private int resizeCoordinate(double coordinate, double center, double scaleFactor) {
        return (int) Math.round((coordinate - center) * scaleFactor + center);
    }

    // Getters and Setters
    /**
     * Sets the border color for the triangle.
     * 
     * @param borderColor The new border color.
     */
    @Override
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Sets the fill color for the triangle.
     * 
     * @param fillColor The new fill color.
     */
    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Sets the border width for the triangle.
     * 
     * @param borderWidth The new border width.
     */
    @Override
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Returns the x-coordinate of the second vertex of the triangle.
     * 
     * @return The x-coordinate of the second vertex.
     */
    public double getX2() {
        return x2;
    }

    /**
     * Sets the x-coordinate of the second vertex of the triangle.
     * 
     * @param x2 The new x-coordinate of the second vertex.
     */
    public void setX2(double x2) {
        this.x2 = x2;
    }

    /**
     * Returns the y-coordinate of the second vertex of the triangle.
     * 
     * @return The y-coordinate of the second vertex.
     */
    public double getY2() {
        return y2;
    }

    /**
     * Sets the y-coordinate of the second vertex of the triangle.
     * 
     * @param y2 The new y-coordinate of the second vertex.
     */
    public void setY2(double y2) {
        this.y2 = y2;
    }

    /**
     * Returns the x-coordinate of the third vertex of the triangle.
     * 
     * @return The x-coordinate of the third vertex.
     */
    public double getX3() {
        return x3;
    }

    /**
     * Sets the x-coordinate of the third vertex of the triangle.
     * 
     * @param x3 The new x-coordinate of the third vertex.
     */
    public void setX3(double x3) {
        this.x3 = x3;
    }

    /**
     * Returns the y-coordinate of the third vertex of the triangle.
     * 
     */
    public double getY3() {
        return y3;
    }

    /**
     * Sets the y-coordinate of the third vertex of the triangle.
     * 
     * @param y3 The new y-coordinate of the third vertex.
     */
    public void setY3(double y3) {
        this.y3 = y3;
    }

    /**
     * Returns the border color of the triangle.
     * 
     */
    public Color getBorderColor() {
        return borderColor;
    }
}
