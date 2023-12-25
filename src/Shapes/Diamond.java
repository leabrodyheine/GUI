package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The Diamond class extends the Shape class and represents a diamond shape.
 * It includes methods for painting the diamond, checking if a point is inside
 * it,
 * moving it, and resizing it.
 */
public class Diamond extends Shape {
    private int width, height;
    private Color borderColor;
    private Color fillColor;

    /**
     * Constructs a Diamond object with specified coordinates, dimensions, border
     * color, border width, and fill color.
     * 
     * @param x           The x-coordinate of the top-left corner of the diamond's
     *                    bounding rectangle.
     * @param y           The y-coordinate of the top-left corner of the diamond's
     *                    bounding rectangle.
     * @param width       The width of the diamond's bounding rectangle.
     * @param height      The height of the diamond's bounding rectangle.
     * @param borderColor The color of the diamond's border.
     * @param borderWidth The width of the diamond's border.
     * @param fillColor   The fill color of the diamond.
     */
    public Diamond(int x, int y, int width, int height, Color borderColor, int borderWidth, Color fillColor) {
        super(x, y, borderColor, borderWidth, fillColor);
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    /**
     * Paints the diamond on the given graphics context.
     *
     * @param g The graphics context to paint on.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        double centerX = getX() + width / 2;
        double centerY = getY() + height / 2;

        double[] xPointsDouble = { getX() + width / 2, getX() + width, getX() + width / 2, getX() };
        double[] yPointsDouble = { getY(), getY() + height / 2, getY() + height, getY() + height / 2 };

        int[] xPoints = new int[xPointsDouble.length];
        int[] yPoints = new int[yPointsDouble.length];

        for (int i = 0; i < xPointsDouble.length; i++) {
            xPoints[i] = (int) Math.round(xPointsDouble[i]);
            yPoints[i] = (int) Math.round(yPointsDouble[i]);
        }

        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(getRotation()));
        g2d.translate(-centerX, -centerY);

        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fillPolygon(xPoints, yPoints, 4);
        }
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(getBorderWidth()));
        g2d.drawPolygon(xPoints, yPoints, 4);

        g2d.dispose();
    }

    /**
     * Checks if a given point is within the bounding rectangle of the diamond.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is within the bounding rectangle, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        return x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height;
    }

    /**
     * Moves the diamond by a specified delta in the x and y directions.
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
     * Resizes the diamond by a specified scale factor.
     *
     * @param scaleFactor The factor by which to scale the diamond.
     */
    @Override
    public void resize(double scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
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
     * Returns the width.
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height.
     * 
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the border color.
     * 
     */
    public Color getBorderColor() {
        return borderColor;
    }
}
