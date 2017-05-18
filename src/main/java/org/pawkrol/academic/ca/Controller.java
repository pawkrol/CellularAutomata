package org.pawkrol.academic.ca;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.pawkrol.academic.ca.automate.AutomataResolver;
import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.*;
import org.pawkrol.academic.ca.automate.seed.NoSeeder;
import org.pawkrol.academic.ca.automate.seed.RadiusRandomSeeder;
import org.pawkrol.academic.ca.automate.seed.RandomSeeder;
import org.pawkrol.academic.ca.automate.seed.Seeder;
import org.pawkrol.academic.ca.automate.strategy.Fredkin;
import org.pawkrol.academic.ca.automate.strategy.GameOfLife;
import org.pawkrol.academic.ca.automate.strategy.NaiveSeedGrowth;
import org.pawkrol.academic.ca.automate.strategy.Strategy;
import org.pawkrol.academic.ca.utils.ColorHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private Canvas canvas;

    @FXML private ComboBox<Strategy> strategyCombo;
    @FXML private ComboBox<Neighbourhood> neighbourhoodCombo;
    @FXML private ComboBox<Seeder> seederCombo;

    @FXML private TextField colsText;
    @FXML private TextField rowsText;
    @FXML private TextField stepsText;
    @FXML private TextField seedNText;
    @FXML private TextField seedRText;

    @FXML private Label iterationText;

    @FXML private CheckBox cyclicCheck;

    @FXML private Button startBtn;

    private GraphicsContext gc;

    private double width;
    private double height;
    private double xOffset;
    private double yOffset;

    private double cellSize;

    private int n;
    private int r;

    private AutomataResolver automataResolver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);

        width = canvas.getWidth();
        height = canvas.getHeight();

        initControls();
    }

    @FXML
    public void onStart() throws Exception{
        automataResolver.start();
        startBtn.setDisable(true);
    }

    @FXML
    public void onInit() throws Exception {
        initResolver();
        startBtn.setDisable(false);

        iterationText.setText("0");
    }

    @FXML
    public void onCanvasClicked(MouseEvent event){
        if (automataResolver == null) return;

        double mx = event.getX();
        double my = event.getY();

        automataResolver.getGrid().forEach(
            c -> {
                if ( isInCell(mx, my, c) ) {
                    automataResolver.switchState(c);
                    draw(automataResolver.getGrid());
                }
            }
        );
    }

    private void fetchFromControls(AutomataResolver automataResolver) {
        int cols = Integer.parseInt(colsText.getText());
        int rows = Integer.parseInt(rowsText.getText());
        int steps = Integer.parseInt(stepsText.getText());

        n = Integer.parseInt(seedNText.getText());
        r = Integer.parseInt(seedRText.getText());

        Grid grid = new Grid(cols, rows, cyclicCheck.isSelected());
        Strategy strategy = strategyCombo.getSelectionModel().getSelectedItem();
        Neighbourhood neighbourhood = neighbourhoodCombo.getSelectionModel().getSelectedItem();
        Seeder seeder = seederCombo.getSelectionModel().getSelectedItem();

        automataResolver.setSteps(steps);
        automataResolver.setGrid(grid);
        automataResolver.setStrategy(strategy);
        automataResolver.setNeighbourhood(neighbourhood);
        automataResolver.setSeeder(seeder);
    }

    private void initResolver() throws Exception{
        automataResolver = new AutomataResolver();
        fetchFromControls(automataResolver);

        automataResolver.init();
        automataResolver.seed(n, r);
        automataResolver.setOnSucceeded( event ->
                Platform.runLater(() -> {
                        draw((Grid) event.getSource().getValue());
                        automataResolver.reset();
                        startBtn.setDisable(false);
                        iterationText.setText(""
                                + automataResolver.getIteration());
                    }
                )
        );

        initGrid(automataResolver.getGrid());
        draw(automataResolver.getGrid());
    }

    private void initControls(){
        startBtn.setDisable(true);

        strategyCombo.getItems().addAll(
                new GameOfLife(),
                new Fredkin(),
                new NaiveSeedGrowth()
        );
        strategyCombo.getSelectionModel().selectFirst();

        neighbourhoodCombo.getItems().addAll(
                new Moore(),
                new VonNeumann(),
                HexagonalLeft.getInstance(),
                HexagonalRight.getInstance(),
                new HexagonalRandom(),
                new PentagonalRandom()
        );
        neighbourhoodCombo.getSelectionModel().selectFirst();

        seederCombo.getItems().addAll(
                new NoSeeder(),
                new RandomSeeder(),
                new RadiusRandomSeeder()
        );
        seederCombo.getSelectionModel().selectFirst();
    }

    private void initGrid(Grid grid){
        int gw = grid.getWidth();
        int gh = grid.getHeight();
        double cw = width / gw;
        double ch = height / gh;

        xOffset = 0;
        yOffset = 0;

        if (cw < ch) {
            cellSize = cw;
            yOffset = height/2 - ((gh * cellSize) / 2);
        } else {
            cellSize = ch;
            xOffset = width/2 - ((gw * cellSize) / 2);
        }

    }

    private void draw(Grid grid){
        gc.clearRect(0, 0, width, height);

        drawCells(grid);
        drawGrid(grid);
    }

    private void drawCells(Grid grid){
        grid.forEach(c -> {
            gc.setFill(ColorHelper.getColor(c.getState()));
            gc.fillRect(
                    xOffset + c.getX() * cellSize,
                    yOffset + c.getY() * cellSize,
                    cellSize, cellSize
            );
        });
    }

    private void drawGrid(Grid grid){
        //horizontal lines
        for (int y = 0; y < grid.getHeight() + 1; y++){
            gc.strokeLine(
                    xOffset,
                    yOffset + snap(y * cellSize),
                    xOffset + grid.getWidth() * cellSize,
                    yOffset + snap(y * cellSize)
            );
        }

        //vertical lines
        for (int x = 0; x < grid.getWidth() + 1; x++) {
            gc.strokeLine(
                    xOffset + snap(x * cellSize),
                    yOffset,
                    xOffset + snap(x * cellSize),
                    yOffset + grid.getHeight() * cellSize
            );
        }
    }

    private double snap(double y) {
        return ((int) y) + .5;
    }

    private boolean isInCell(double mx, double my, Cell c){
        double cx = xOffset + c.getX() * cellSize;
        double cy = yOffset + c.getY() * cellSize;

        return mx < cx + cellSize && my < cy + cellSize
                && mx > cx && my > cy;
    }
}
