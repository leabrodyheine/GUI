# GUI

## Overview
This Java Swing application allows users to create and manipulate simple vector graphics. It follows the Model-Delegate (MD) design pattern.

## Features
- **Shapes**: Supports lines, rectangles, ellipses, triangles, diamonds.
- **Solid Fill**: Shapes can have a solid fill, defaulting to black, or no fill.
- **Border Width**: Customizable border width.
- **Color**: Separate fill and border color options.
- **Rotate**: Rotate shapes by 30 degrees left or right.
- **Undo/Redo**: For reverting or reapplying actions.
- **Scale**: Scale shapes up or down by 10%.
- **Square & Circle**: press shift to create a perfect square or circle.
- **Delete**: Permanent deletion of shapes.
- **Move**: Click and drag to move shapes.
- **File Menu**:
    - *Save*: Save as PNG.
    - *Clear Screen*: Clears all shapes.
- **Server Menu**:
    - *Reconnect to Server*: Reconnect without server drawings.
    - *Disconnect*: Disconnect from the server.
    - *Delete My Drawing*: Delete your drawing from the server.
    - *Update Drawing*: Update your drawing on the server.
    - *Upload to Server*: Upload shapes to the server.
    - *Get Server Drawings*: View all drawings on the server.

## Running the Application
Execute via the run button in your IDE.

## Running JUnit Tests
### Test Environment Setup
Ensure JUnit is installed and configured in your IDE.

### Executing Tests
Compile and run JUnit tests
