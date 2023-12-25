package myGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import Model.Model;
import Shapes.Diamond;
import Shapes.DrawType;
import Shapes.Ellipses;
import Shapes.Line;
import Shapes.Rectangle;
import Shapes.Shape;
import Shapes.Triangle;

import javax.json.JsonArray;
import javax.json.JsonObject;

import Server.Client;

/**
 * This class represents the graphical user interface for the drawing
 * application.
 * It manages user interactions and reflects changes in the model to the user
 * interface.
 */
public class GUIDelegate implements PropertyChangeListener {
    private static final int FRAME_HEIGHT = 900;
    private static final int FRAME_WIDTH = 1000;
    private Shape selectedShape = null;
    private JFrame mainFrame;
    private JPanel drawingCanvas;
    private JToolBar toolbar;
    private JCheckBox solidFill;
    private JButton Undo, Redo, fillColorButton, borderColorButton, rotateButton, rotateButtonLeft, deleteButton;
    private JSpinner borderWidth;
    private Color selectedBorderColor = Color.BLACK;
    private int selectedBorderWidth = 1;
    private Color selectedFillColor = Color.BLACK;
    private Model model;
    private DrawType drawType;
    private Shape currentDrawingShape;
    private int startPointX;
    private int startPointY;
    private int endPointX;
    private int endPointY;
    private MouseHandler handler;
    private String[] shapeOptions = { "line", "rectangle", "ellipse", "triangle", "diamond" };
    private JComboBox<String> shapeSelector = new JComboBox<>(shapeOptions);
    private Client client;
    private List<Shape> shapes;
    private boolean connectedToServer;
    private JMenuBar menuBar;

    /**
     * Constructor for GUIDelegate.
     * Initializes the main frame, drawing canvas, toolbar, and sets up component
     * interactions.
     *
     * @param model The model instance containing application data and logic.
     * @throws IOException If an error occurs during initialization.
     */
    public GUIDelegate(Model model) throws IOException {
        this.connectedToServer = false;

        drawingCanvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (Shape shape : model.getShapes()) {
                    shape.paint(g);
                }
                if (connectedToServer) {
                    for (Shape serverShape : model.getShapesFromServer()) {
                        serverShape.paint(g);
                    }
                }
            }
        };
        this.mainFrame = new JFrame();
        handler = new MouseHandler();
        this.model = model;
        menuBar = new JMenuBar();
        toolbar = new JToolBar();
        this.drawType = DrawType.line;
        setupComponents();
        model.addObserver(this);
        this.client = new Client("cs5001-p3.dynv6.net", 8080, this);
    }

    /**
     * Sets up the menu for the GUI.
     * This includes creating menus and menu items for file and server operations.
     */
    private void setUpMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenu serverMenu = new JMenu("Server");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            saveDrawingToFile();
        });

        JMenuItem uploadToServerMenuItem = new JMenuItem("Upload to Server");
        uploadToServerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadShapesToServer();
            }
        });

        JMenuItem connectToServerMenuItem = new JMenuItem("Get Server Drawings");
        connectToServerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServerAndDisplayShapes();
            }
        });

        JMenuItem deleteDrawingMenuItem = new JMenuItem("Delete My Drawing");
        deleteDrawingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    try {
                        JsonObject response = client.deleteDrawing(String.valueOf(selectedShape.getId()));
                        if ("ok".equals(response.getString("result"))) {
                            model.removeShape(selectedShape);
                            selectedShape = null;
                            drawingCanvas.repaint();
                            JOptionPane.showMessageDialog(mainFrame, "Shape deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Error: " + response.getString("message"));
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error communicating with the server.");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "No shape selected for deletion.");
                }
            }
        });

        JMenuItem updateDrawingMenuItem = new JMenuItem("Update Drawing");
        updateDrawingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    try {
                        JsonObject response = client.updateDrawing(String.valueOf(selectedShape.getId()),
                                selectedShape);
                        if ("ok".equals(response.getString("result"))) {
                            JOptionPane.showMessageDialog(mainFrame, "Shape updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Error: " + response.getString("message"));
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error communicating with the server.");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "No shape selected for update.");
                }
            }
        });

        JMenuItem clearScreenMenuItem = new JMenuItem("Clear Screen");
        clearScreenMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clearShapes();
                drawingCanvas.repaint();
            }
        });

        JMenuItem disconnectMenuItem = new JMenuItem("Disconnect");
        disconnectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setConnectedToServer(false);
                model.clearServerShapes();
                client.close();
                JOptionPane.showMessageDialog(mainFrame, "Disconnected from server.");
                drawingCanvas.repaint();
            }
        });
        JMenuItem reconnectMenuItem = new JMenuItem("Reconnect to Server");
        serverMenu.add(reconnectMenuItem);

        reconnectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (client != null) {
                        client.close();
                    }
                    client = new Client("cs5001-p3.dynv6.net", 8080, GUIDelegate.this);
                    setConnectedToServer(true);
                    JOptionPane.showMessageDialog(mainFrame, "Reconnected to server.");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Error reconnecting to server: " + ioException.getMessage());
                }
            }
        });

        serverMenu.add(disconnectMenuItem);
        serverMenu.add(reconnectMenuItem);
        serverMenu.add(deleteDrawingMenuItem);
        serverMenu.add(updateDrawingMenuItem);
        serverMenu.add(uploadToServerMenuItem);
        serverMenu.add(connectToServerMenuItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(clearScreenMenuItem);

        this.menuBar.add(fileMenu);
        this.menuBar.add(serverMenu);

        mainFrame.setJMenuBar(menuBar);
    }

    /**
     * Updates the server connection status and refreshes the canvas to display
     * server shapes if connected.
     *
     * @param connected The status of connection to the server.
     */
    public void setConnectedToServer(boolean connected) {
        this.connectedToServer = connected;
        drawingCanvas.repaint();
    }

    /**
     * Inner class for handling mouse events on the drawing canvas.
     * It manages mouse interactions for selecting, dragging, and releasing shapes.
     */
    private class MouseHandler extends MouseAdapter {
        private boolean isDragging = false;
        private int prevX, prevY;

        /**
         * Handles mouse pressed events on the canvas.
         * Identifies if a shape is selected and prepares for potential dragging.
         *
         * @param e The MouseEvent object containing details about the mouse event.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            isDragging = false;

            for (Shape shape : model.getShapes()) {
                if (shape.contains(x, y)) {
                    selectedShape = shape;
                    prevX = x;
                    prevY = y;
                    isDragging = true;
                    break;
                }
            }

            if (!isDragging) {
                startPointX = x;
                startPointY = y;
            }
        }

        /**
         * Handles mouse dragged events on the canvas.
         * Responsible for dragging the selected shape across the canvas.
         *
         * @param e The MouseEvent object containing details about the mouse event.
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            if (isDragging && selectedShape != null) {
                int deltaX = e.getX() - prevX;
                int deltaY = e.getY() - prevY;
                selectedShape.move(deltaX, deltaY);
                prevX = e.getX();
                prevY = e.getY();
                model.notifyShapeChanged(selectedShape);
                drawingCanvas.repaint();
            }
        }

        /**
         * Handles mouse released events on the canvas.
         * Finalizes the position of a shape after being dragged and adds new shapes.
         *
         * @param e The MouseEvent object containing details about the mouse event.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isDragging) {
                endPointX = e.getX();
                endPointY = e.getY();

                int width = Math.abs(endPointX - startPointX);
                int height = Math.abs(endPointY - startPointY);

                int baseMidX = (startPointX + endPointX) / 2;

                int apexY = (startPointY < endPointY) ? startPointY - height : startPointY + height;

                Color fill = solidFill.isSelected() ? selectedFillColor : null;

                if (e.isShiftDown()) {
                    int minDimension = Math.min(width, height);
                    width = height = minDimension;
                }

                switch (drawType) {
                    case line:
                        currentDrawingShape = new Line(startPointX, startPointY, endPointX, endPointY,
                                selectedBorderColor, selectedBorderWidth);
                        break;
                    case rectangle:
                        int rectX = Math.min(startPointX, endPointX);
                        int rectY = Math.min(startPointY, endPointY);
                        currentDrawingShape = new Rectangle(rectX, rectY, width, height, selectedBorderColor,
                                selectedBorderWidth, fill);
                        break;
                    case ellipse:
                        currentDrawingShape = new Ellipses(startPointX, startPointY, width, height, selectedBorderColor,
                                selectedBorderWidth, fill);
                        break;
                    case triangle:
                        currentDrawingShape = new Triangle(baseMidX, apexY, startPointX, endPointY, endPointX,
                                endPointY,
                                selectedBorderColor, selectedBorderWidth, fill);
                        break;
                    case diamond:
                        int diamondX = Math.min(startPointX, endPointX);
                        int diamondY = Math.min(startPointY, endPointY);
                        currentDrawingShape = new Diamond(diamondX, diamondY, width, height, selectedBorderColor,
                                selectedBorderWidth, fill);
                        break;
                }
                drawingCanvas.requestFocusInWindow();
                model.addShape(currentDrawingShape);
            }

            isDragging = false;
        }
    }

    /**
     * Initialises the toolbar to contain the buttons, label, input field, etc. and
     * adds the toolbar to the main frame.
     * Listeners are created for the buttons and text field which translate user
     * events to model object method calls (controller aspect of the delegate)
     */
    private void setupToolbar() {
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        toolbar.setBackground(new Color(200, 200, 200));

        // solif fill check box
        solidFill = new JCheckBox("Solid Fill");
        solidFill.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        solidFill.setPreferredSize(new Dimension(80, 30));
        solidFill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setFill(solidFill.isSelected());
            }
        });

        // shape selector indicating which shape you're drawing
        shapeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedShape = (String) shapeSelector.getSelectedItem();
                shapeSelector.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                switch (selectedShape) {
                    case "line":
                        selectedShape = null;
                        drawType = DrawType.line;
                        break;
                    case "rectangle":
                        selectedShape = null;
                        drawType = DrawType.rectangle;
                        break;
                    case "ellipse":
                        selectedShape = null;
                        drawType = DrawType.ellipse;
                        break;
                    case "triangle":
                        selectedShape = null;
                        drawType = DrawType.triangle;
                        break;
                    case "diamond":
                        selectedShape = null;
                        drawType = DrawType.diamond;
                        break;
                }
                model.setCurrentShapeType(drawType);
            }
        });

        // undo button
        Undo = new JButton("Undo Shape");
        styleButton(Undo);
        Undo.setPreferredSize(new Dimension(80, 34));
        Undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.undo();
            }
        });

        // redo button
        Redo = new JButton("Redo Shape");
        styleButton(Redo);
        Redo.setPreferredSize(new Dimension(80, 34));
        Redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.redo();
            }
        });

        // rotate right button (rotates by 30 degrees)
        rotateButton = new JButton("Rotate Right");
        styleButton(rotateButton);
        rotateButton.setPreferredSize(new Dimension(80, 34));
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GUIDelegate.this.selectedShape != null) {
                    GUIDelegate.this.selectedShape.rotate(30);
                    drawingCanvas.repaint();
                }
            }
        });

        // rotate left button (rotates by 30 degrees)
        rotateButtonLeft = new JButton("Rotate Left");
        styleButton(rotateButtonLeft);
        rotateButtonLeft.setPreferredSize(new Dimension(80, 34));
        rotateButtonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GUIDelegate.this.selectedShape != null) {
                    GUIDelegate.this.selectedShape.rotate(-30);
                    drawingCanvas.repaint();
                }
            }
        });

        // scale up button (scale by 10%)
        JButton scaleUpButton = new JButton("Scale Up");
        styleButton(scaleUpButton);
        scaleUpButton.setPreferredSize(new Dimension(70, 34));
        scaleUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    selectedShape.resize(1.1);
                    drawingCanvas.repaint();
                }
            }
        });

        // scale down button (scale by 10%)
        JButton scaleDownButton = new JButton("Scale Down");
        styleButton(scaleUpButton);
        scaleDownButton.setPreferredSize(new Dimension(70, 34));
        scaleDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    selectedShape.resize(0.9);
                    drawingCanvas.repaint();
                }
            }
        });

        // delete shape button
        deleteButton = new JButton("Delete Shape");
        styleButton(deleteButton);
        deleteButton.setPreferredSize(new Dimension(90, 34));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedShape();
            }
        });

        // sets the border width
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
        borderWidth = new JSpinner(spinnerModel);
        borderWidth.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        borderWidth.setPreferredSize(new Dimension(45, 34));
        borderWidth.addChangeListener(e -> {
            int newWidth = (Integer) borderWidth.getValue();
            if (selectedShape != null) {
                model.changeShapeBorderWidth(selectedShape, newWidth);
            } else {
                selectedBorderWidth = newWidth;
            }
        });

        // lable for border width setter
        JLabel borderWidthLabel = new JLabel("Border Width:");
        borderWidthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        borderWidthLabel.setPreferredSize(new Dimension(80, 34));

        // Create a panel for the two buttons that deal with color
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new BoxLayout(buttonPanel2, BoxLayout.Y_AXIS));
        buttonPanel2.setBackground(new Color(200, 200, 200));

        Dimension buttonSize2 = new Dimension(100, 17);

        // fill Color Button
        fillColorButton = new JButton("Fill Color");
        styleButton(fillColorButton);
        fillColorButton.setPreferredSize(buttonSize2);
        fillColorButton.setMaximumSize(buttonSize2);
        fillColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(mainFrame, "Choose Fill Color", selectedFillColor);
                if (newColor != null) {
                    if (selectedShape != null) {
                        model.changeShapeFillColor(selectedShape, newColor);
                    } else {
                        selectedFillColor = newColor;
                    }
                }
            }
        });
        buttonPanel2.add(fillColorButton);

        // border Color Button
        borderColorButton = new JButton("Border Color");
        styleButton(borderColorButton);
        borderColorButton.setPreferredSize(buttonSize2);
        borderColorButton.setMaximumSize(buttonSize2);
        borderColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(mainFrame, "Choose Border Color", selectedBorderColor);
                if (newColor != null) {
                    if (selectedShape != null) {
                        model.changeShapeBorderColor(selectedShape, newColor);
                    } else {
                        selectedBorderColor = newColor;
                    }
                }
            }
        });
        buttonPanel2.add(borderColorButton);

        // Add components to the toolbar
        toolbar.add(solidFill);
        toolbar.add(shapeSelector);
        toolbar.add(borderWidthLabel);
        toolbar.add(borderWidth);
        toolbar.add(buttonPanel2);
        toolbar.add(rotateButton);
        toolbar.add(rotateButtonLeft);
        toolbar.add(Undo);
        toolbar.add(Redo);
        toolbar.add(scaleUpButton);
        toolbar.add(scaleDownButton);
        toolbar.add(deleteButton);

        styleButton(Undo);
        styleButton(Redo);
        styleButton(fillColorButton);
        styleButton(borderColorButton);
        styleButton(rotateButton);
        styleButton(scaleUpButton);
        styleButton(scaleDownButton);
        styleButton(deleteButton);

        // Add toolbar to the main frame
        mainFrame.add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Sets up the main components of the GUI, including the drawing canvas and
     * toolbar.
     */
    private void setupComponents() {
        drawingCanvas.addMouseMotionListener(handler);
        drawingCanvas.addMouseListener(handler);
        drawingCanvas.setFocusable(true);
        drawingCanvas.requestFocusInWindow();
        mainFrame.add(drawingCanvas, BorderLayout.CENTER);
        setUpMenu();
        setupToolbar();
        mainFrame.getContentPane().setBackground(new Color(230, 230, 230));
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Styles a given JButton with a consistent look and feel.
     *
     * @param button The JButton to apply the style to.
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        button.setBackground(new Color(100, 100, 100));
    }

    /**
     * Handles property changes in the observed model.
     * This method is called in response to model updates and is responsible for
     * updating the GUI based on the changes.
     *
     * @param event The property change event that contains information about
     *              the changed property and its new value.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent event) {
        if ("selectedShape".equals(event.getPropertyName())) {
            this.selectedShape = (Shape) event.getNewValue();
        }
        drawingCanvas.repaint();
        if (event.getSource() == model && event.getPropertyName().equals("shapes")) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    drawingCanvas.repaint();
                }
            });
        }
    }

    /**
     * Uploads all shapes from the model to the server.
     * Displays a message indicating success or failure for each shape.
     */
    private void uploadShapesToServer() {
        for (Shape shape : model.getShapes()) {
            try {
                JsonObject response = client.addDrawing(shape);
                String ID = response.getJsonObject("data").getString("id");

                shape.setId(ID);

                if ("ok".equals(response.getString("result"))) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Successfully uploaded shape with ID: " + response.getJsonObject("data").getString("id"));
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Error: " + response.getString("message"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error communicating with the server.");
            }
        }
    }

    /**
     * Connects to the server to fetch and display shapes.
     * Handles server responses and updates the model with shapes from the server.
     */
    private void connectToServerAndDisplayShapes() {
        try {
            client.login();
            JsonObject response = client.getDrawings();

            if (response.containsKey("result") && "error".equals(response.getString("result"))) {
                JOptionPane.showMessageDialog(mainFrame, "Error fetching shapes: " + response.getString("message"));
                return;
            }
            JsonArray shapesArray = response.getJsonArray("shapes");
            if (shapesArray != null) {
                shapes = client.parseShapes(shapesArray);
                displayShapes(shapes);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "No shapes received from server.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays a list of shapes retrieved from the server.
     *
     * @param shapes The list of shapes to display.
     */
    private void displayShapes(List<Shapes.Shape> shapes) {
        if (shapes == null || shapes.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No shapes received or empty list of shapes.");
            return;
        }
        for (Shapes.Shape shape : shapes) {
            model.addShapeFromServer(shape);
        }
        drawingCanvas.repaint();
    }

    /**
     * Deletes the currently selected shape from the model and updates the display.
     */
    private void deleteSelectedShape() {
        if (selectedShape != null) {
            model.removeShape(selectedShape);
            selectedShape = null;
            drawingCanvas.repaint();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No shape selected for deletion.");
        }
    }

    /**
     * Saves the current drawing to a file.
     * Prompts the user to specify the file location and format.
     */
    private void saveDrawingToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int userSelection = fileChooser.showSaveDialog(mainFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave + ".png");
            }

            try {
                int width = 800;
                int height = 600;
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();

                for (Shape shape : model.getShapes()) {
                    shape.paint(g2d);
                }
                g2d.dispose();

                if (!ImageIO.write(image, "png", fileToSave)) {
                    throw new IOException("Failed to write the image file.");
                }

                JOptionPane.showMessageDialog(mainFrame, "Image saved successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error saving image: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}