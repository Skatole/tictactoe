package sample.fxui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    static boolean isEnded = false;
    static boolean hasWonX = false;
    static boolean hasWonO = false;

    public static String[][] board3 = new String[3][3];
    public static String[][] board10 = new String[10][10];
    public static String[][] board15 = new String[15][15];

    int tilesCount;
    int i;
    int j;
    static int movesCount = 0;

    static String currentPlayerSign = "X";
    private Text text = new Text();
    boolean writeAble = text.getText().isEmpty();

    static Buttons nextPlayerDisplay;
    static Buttons winnerDisplay;
    static Buttons currentPlayerText;
    static Buttons winnerText;

    public static Parent createContent(int tilesCount) {
        movesCount = 0;
        double boardSize = 750.0 / tilesCount;

        Pane root = new Pane();
        root.setPrefSize(1200, 750);

        nextPlayerDisplay = new Buttons(120, 120);
        Main.menuBtnCreator(nextPlayerDisplay, "", 915, 100);
        nextPlayerDisplay.changeBorderColor(nextPlayerDisplay.border, Color.WHITESMOKE);
        nextPlayerDisplay.setText(currentPlayerSign);
        nextPlayerDisplay.changeOnMouseColor(Color.WHITESMOKE);

        winnerDisplay = new Buttons(120, 120);
        Main.menuBtnCreator(winnerDisplay, "", 916, 300);
        winnerDisplay.changeBorderColor(winnerDisplay.border, Color.WHITESMOKE);
        winnerDisplay.changeOnMouseColor(Color.WHITESMOKE);

        currentPlayerText = new Buttons(100, 50);
        Main.menuBtnCreator(currentPlayerText, "next:", 850, 133);
        currentPlayerText.changeBorderColor(currentPlayerText.border, Color.WHITESMOKE);
        currentPlayerText.changeFontSize(32);
        currentPlayerText.changeOnMouseColor(Color.WHITESMOKE);

        winnerText = new Buttons(400, 100);
        Main.menuBtnCreator(winnerText, "", 780, 220);
        winnerText.changeBorderColor(winnerText.border, Color.WHITESMOKE);
        winnerText.changeOnMouseColor(Color.WHITESMOKE);
        winnerText.changeFontSize(26);

        root.getChildren().addAll(nextPlayerDisplay, winnerDisplay, currentPlayerText, winnerText);

        for (int i = 0; i < tilesCount; i++) {
            for (int j = 0; j < tilesCount; j++) {
                Tile tile = new Tile(tilesCount);
                tile.setTranslateX(j * boardSize);
                tile.setTranslateY(i * boardSize);
                root.getChildren().add(tile);
                // add some kind of id
                tile.i = i;
                tile.j = j;
                //id++;
                //tile.setId(String.valueOf(id));
            }
        }
        return root;
    }

    public Tile(int tilesCount) {
        eraseBoard(board3);
        eraseBoard(board10);
        eraseBoard(board15);
        double boardSize = 750.0 / tilesCount;
        this.tilesCount = tilesCount;
        Rectangle border = new Rectangle(boardSize, boardSize);
        border.setFill(Color.WHITESMOKE);
        border.setStroke(Color.BLACK);

        switch (tilesCount) {
            case 15:
                text.setFont(Font.font(20));
                break;
            case 10:
                text.setFont(Font.font(28));
                break;
            case 3:
                text.setFont(Font.font(48));
                break;
        }

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (writeAble && !isEnded) {
                    writeAble = false;
                    draw();
                    changePlayer();
                    tileTextToArray();
                    nextPlayerDisplay.setText(currentPlayerSign);
                    movesCount++;
                    checkWinners();
                }
            }
        });
        setOnMouseEntered(event -> {
            border.setFill(Color.WHITESMOKE.brighter());
        });
        setOnMouseExited(event -> {
            border.setFill(Color.WHITESMOKE);
        });
    }

    private void draw() {
        text.setText(currentPlayerSign);
    }

    private void changePlayer() {
        if (currentPlayerSign == "X") {
            currentPlayerSign = "O";
        } else {
            currentPlayerSign = "X";
        }
    }

    private void tileTextToArray() {
        switch (tilesCount) {
            case 3:
                board3[i][j] = this.text.getText();
                break;
            case 10:
                board10[i][j] = this.text.getText();
                break;
            case 15:
                board15[i][j] = this.text.getText();
                break;
        }
    }

    private boolean chickenDinner(String player, String[][] map, int winCount) {
        // DO NOT TOUCH
        // its long, its unreadable, but works
        int rowCounter = 0;
        int colCounter = 0;
        int diagCounter = 0;
        int revDiagcounter = 0;

        int length = map.length;
        int diagonalLines = 2 * length - 1;
        int midpoint = diagonalLines / 2 + 1;
        int itemsInDiagonal = 0;
        int itemsInDiagonal2 = 0;

        // rows and cols
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j].equals(player)) {
                    rowCounter++;
                } else rowCounter = 0;

                if (map[j][i].equals(player)) {
                    colCounter++;
                } else colCounter = 0;

                if (rowCounter == winCount || colCounter == winCount) {
                    return true;
                }
            }
            rowCounter = 0;
            colCounter = 0;
        }

        // bottom to top diagonal
        for (int i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int colIndex;

            if (i <= midpoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = i - j - 1;
                    colIndex = j;
                    if (map[rowIndex][colIndex].equals(player)) {
                        diagCounter++;
                    } else {
                        diagCounter = 0;
                    }
                    if (diagCounter == winCount) {
                        return true;
                    }
                }
                diagCounter = 0;
            }
            if (i > midpoint) {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (length - 1) - j;
                    colIndex = (i - length) + j;
                    if (map[rowIndex][colIndex].equals(player)) {
                        diagCounter++;
                    } else {
                        diagCounter = 0;
                    }
                    if (diagCounter == winCount) {
                        return true;
                    }
                }
                diagCounter = 0;
            }
        }

        // top to bottom
        int rowIndex;
        int colIndex;
        for (int i = 1; i < diagonalLines; i++) {
            if (i > midpoint) {
                itemsInDiagonal2--;
                for (int j = 0; j < itemsInDiagonal2; j++) {
                    rowIndex = j;
                    colIndex = (i - length) + j;
                    if (map[rowIndex][colIndex].equals(player)) {
                        revDiagcounter++;
                    } else {
                        revDiagcounter = 0;
                    }
                    if (revDiagcounter == winCount) {
                        return true;
                    }
                }
                revDiagcounter = 0;
            }
            if (i <= midpoint) {
                itemsInDiagonal2++;
                for (int j = 0; j < itemsInDiagonal2; j++) {
                    rowIndex = (length - 1) - j;
                    colIndex = i - j - 1;
                    if (map[rowIndex][colIndex].equals(player)) {
                        revDiagcounter++;
                    } else {
                        revDiagcounter = 0;
                    }
                    if (revDiagcounter == winCount) {
                        return true;
                    }
                }
                revDiagcounter = 0;
            }
        }
        return false;
    }

    public static void eraseBoard(String[][] board) {
        currentPlayerSign = "X";
        isEnded = false;
        hasWonX = false;
        hasWonO = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = "";
            }
        }
    }

    private void checkWinners() {
        switch (tilesCount) {
            case 3:
                hasWonX = chickenDinner("X", board3, 3);
                hasWonO = chickenDinner("O", board3, 3);
                break;
            case 10:
                hasWonX = chickenDinner("X", board10, 5);
                hasWonO = chickenDinner("O", board10, 5);
                break;
            case 15:
                hasWonX = chickenDinner("X", board15, 5);
                hasWonO = chickenDinner("O", board15, 5);
                break;
        }
        if (hasWonO || hasWonX) {
            isEnded = true;
        }
        if(isEnded) {
            winnerDisplay.setWinnerText();
            nextPlayerDisplay.setText("");
            currentPlayerText.setText("");
            winnerText.setText("winner winner chicken dinner!");
        }
        if(movesCount == tilesCount * tilesCount && !isEnded) {
            nextPlayerDisplay.setText("");
            currentPlayerText.setText("");
            winnerText.setText("oh c'mon");
        }
    }
}
