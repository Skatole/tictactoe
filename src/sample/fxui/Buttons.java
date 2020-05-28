package sample.fxui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Buttons extends StackPane {
    public Text text = new Text();
    Rectangle border;
    int xSize;
    int ySize;

    public Parent createButton(Buttons button) {
        Pane root = new Pane();
        root.setPrefSize(xSize, ySize);

        root.getChildren().add(button);

        return root;
    }

    public Buttons(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        border = new Rectangle(xSize, ySize);
        border.setFill(Color.WHITESMOKE);
        border.setStyle("-fx-border-width: 5");
        border.setStroke(Color.BLACK);
        text.setFont(Font.font(36));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        setOnMouseEntered(event -> {
            border.setFill(Color.WHITESMOKE.brighter());
        });

        setOnMouseExited(event -> {
            border.setFill(Color.WHITESMOKE);
        });
    }

    public void changeBorderColor(Rectangle rectangle, Color color) {
        rectangle.setStroke(color);
    }

    public void changeFillColor(Rectangle rectangle, Color color) {
        rectangle.setFill(color);
    }

    public void changeFontSize(int size) {
        this.text.setFont(Font.font(size));
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setWinnerText() {
        if(Tile.hasWonX) {
            this.text.setText("X");
        } else {
            this.text.setText("O");
        }
    }
}