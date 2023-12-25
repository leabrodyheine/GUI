package Model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import Shapes.Shape;
import myGUI.GUIDelegate;
import Shapes.DrawType;
import java.awt.Color;

/**
 * Represents the model for a drawing application, handling shapes, undo/redo
 * functionality, and shape properties.
 */
public class Model {

    private boolean fill;
    private List<Shape> shapes;
    private List<Shape> shapesFromServer;
    private Stack<Object> undoStack;
    private Stack<Object> redoStack;
    private Shape selectedShape;
    private PropertyChangeSupport notifier;
    private java.awt.Color currentColor;
    private DrawType currentShapeType;

    /**
     * Constructs a new Model instance.
     */
    public Model() {
        this.fill = false;
        shapes = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        notifier = new PropertyChangeSupport(this);
        shapesFromServer = new ArrayList<>();
    }

    /**
     * Updates the model with a new value for a specified property.
     *
     * @param propertyName The name of the property to update.
     * @param newValue     The new value for the property.
     */
    public void updateModel(String propertyName, Object newValue) {
        notifier.firePropertyChange(propertyName, null, newValue);
    }

    /**
     * Adds a property change listener to the model.
     *
     * @param listener The listener to be notified of property changes.
     */
    public void addObserver(GUIDelegate listener) {
        notifier.addPropertyChangeListener(listener);
        List<Shape> updatedShapesList = getUpdatedShapesList();
        updateModel("shapes", updatedShapesList);
    }

    /**
     * Returns the current list of shapes managed by the model.
     *
     * @return A list of shapes.
     */
    public List<Shape> getUpdatedShapesList() {
        return shapes;
    }

    /**
     * Notifies observers about changes to the shape list.
     */
    public void notifyObservers() {
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Adds a shape to the model.
     *
     * @param shape The shape to be added.
     */
    public void addShape(Shape shape) {
        shapes.add(shape);
        undoStack.push(shape);
        redoStack.clear();
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Adds a shape received from the server to the model.
     *
     * @param shape The shape received from the server.
     */
    public void addShapeFromServer(Shape shape) {
        shapesFromServer.add(shape);
        undoStack.push(shape);
        redoStack.clear();
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Undoes the last action (shape addition or modification).
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Object action = undoStack.pop();
            if (action instanceof Shape) {
                Shape shape = (Shape) action;
                shapes.remove(shape);
                redoStack.push(shape);
            } else if (action instanceof RemovalMarker) {
                Shape shape = ((RemovalMarker) action).getShape();
                shapes.add(shape);
                redoStack.push(new RemovalMarker(shape));
            }
            notifyObservers();
        }
    }
    
    /**
     * Redoes the last undone action.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Object action = redoStack.pop();
            if (action instanceof Shape) {
                Shape shape = (Shape) action;
                shapes.add(shape);
                undoStack.push(shape); // Add back to undoStack for possible future undo
            } else if (action instanceof RemovalMarker) {
                Shape shape = ((RemovalMarker) action).getShape();
                shapes.remove(shape);
                undoStack.push(new RemovalMarker(shape)); // Add removal marker back to undoStack for possible future undo
            }
            notifyObservers();
        }
    }

    /**
     * Removes a specified shape from the model.
     *
     * @param shapeToRemove The shape to be removed.
     */
    public void removeShape(Shape shapeToRemove) {
        if (shapes.remove(shapeToRemove)) {
            undoStack.remove(shapeToRemove);
            redoStack.remove(shapeToRemove);
            notifyObservers();
        }
    }

    /**
     * Represents a removal marker for a shape that has been removed from the model.
     * This marker is used to keep track of removals in the undo stack.
     */
    class RemovalMarker {
        private Shape shape;

        /**
         * Constructs a new RemovalMarker for a given shape.
         *
         * @param shape The shape associated with this removal marker.
         */
        public RemovalMarker(Shape shape) {
            this.shape = shape;
        }

        /**
         * Gets the shape associated with this removal marker.
         *
         * @return The shape associated with this marker.
         */
        public Shape getShape() {
            return shape;
        }
    }


    /**
     * Changes the border color of a specified shape.
     *
     * @param shape    The shape whose border color is to be changed.
     * @param newColor The new border color.
     */
    public void changeShapeBorderColor(Shape shape, Color newColor) {
        shape.setBorderColor(newColor);
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Changes the fill color of a specified shape.
     *
     * @param shape    The shape whose fill color is to be changed.
     * @param newColor The new fill color.
     */

    public void changeShapeFillColor(Shape shape, Color newColor) {
        shape.setFillColor(newColor);
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Changes the border width of a specified shape.
     *
     * @param shape    The shape whose border width is to be changed.
     * @param newWidth The new border width.
     */
    public void changeShapeBorderWidth(Shape shape, int newWidth) {
        shape.setBorderWidth(newWidth);
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Rotates a shape by a specified angle.
     *
     * @param shapeId The ID of the shape to rotate.
     * @param angle   The rotation angle in degrees.
     */
    public void rotateShape(String shapeId, int angle) {
        for (Shape shape : shapes) {
            if (shape.getId() == shapeId) {
                shape.rotate(angle);
                notifier.firePropertyChange("shapes", null, shapes);
                break;
            }
        }
    }

    /**
     * Selects a shape based on its ID.
     *
     * @param shapeId The ID of the shape to select.
     */
    public void selectShape(String shapeId) {
        selectedShape = shapes.stream()
                .filter(s -> s.getId() == shapeId)
                .findFirst()
                .orElse(null);

        notifier.firePropertyChange("selectedShape", null, selectedShape);
    }

    /**
     * Sets the fill status for new shapes.
     *
     * @param fill The fill status to set.
     */
    public void setFill(boolean fill) {
        this.fill = fill;
        notifier.firePropertyChange("fill", !this.fill, fill);
    }

    /**
     * Sets the current color for new shapes.
     *
     * @param color The current color to set.
     */
    public void setCurrentColor(java.awt.Color color) {
        this.currentColor = color;
        notifier.firePropertyChange("currentColor", null, color);
    }

    /**
     * Sets the current shape type for new shapes.
     *
     * @param drawType The current shape type to set.
     */
    public void setCurrentShapeType(DrawType drawType) {
        this.currentShapeType = drawType;
        notifier.firePropertyChange("currentShapeType", null, drawType);
    }

    /**
     * Notifies observers about changes to a specific shape.
     *
     * @param shape The shape that has changed.
     */
    public void notifyShapeChanged(Shape shape) {
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Notifies observers about updates to the current drawing shape.
     *
     * @param currentDrawingShape The shape that has been updated.
     */
    public void shapeUpdated(Shape currentDrawingShape) {
        notifier.firePropertyChange("shapes", null, shapes);
    }

    /**
     * Returns the fill status for new shapes.
     *
     * @return The fill status.
     */
    public boolean getFill() {
        return fill;
    }

    /**
     * Returns the current color for new shapes.
     *
     * @return The current color.
     */
    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Returns the current shape type for new shapes.
     *
     * @return The current shape type.
     */
    public DrawType getCurrentShapeType() {
        return currentShapeType;
    }

    /**
     * Returns the list of shapes managed by the model.
     *
     * @return A list of shapes.
     */
    public List<Shape> getShapes() {
        return shapes;
    }

    /**
     * Returns the currently selected shape.
     *
     * @return The selected shape, or null if no shape is selected.
     */
    public Shape getSelectedShape() {
        return selectedShape;
    }

    /**
     * Returns the list of shapes retrieved from the server.
     *
     * @return A list of shapes from the server.
     */
    public List<Shape> getShapesFromServer() {
        return shapesFromServer;
    }

    /**
     * Clears all shapes (both local and from server) from the model.
     */

    public void clearShapes() {
        shapes.clear();
        shapesFromServer.clear();
        notifyObservers();
    }

    /**
     * Clears only the shapes retrieved from the server from the model.
     */
    public void clearServerShapes() {
        shapesFromServer.clear();
        notifyObservers();
    }
}