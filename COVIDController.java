import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DateCell;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.awt.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.SVGPath;
import java.net.URISyntaxException;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.File;
import java.util.Arrays;
import javafx.scene.Node;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.stream.Collectors;
import javafx.scene.layout.AnchorPane;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import java.util.HashMap;
import java.awt.Color;
import javafx.scene.paint.*;
import java.util.Map;
import java.util.TreeMap;
import java.time.temporal.ChronoUnit;
import javafx.scene.layout.HBox;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * This class controls the main application.
 * It deals with setting most of the FXML fields
 * as well as handles the switching of panels.
 * It also communicates between the CovidData class
 * and the COVID Gui.
 *
 * @author Rayan Popat, James Coward and Shicheng Li.
 */

public class COVIDController {
    //FXML fields.
    @FXML
    private BorderPane mainPane, welcomePanel, workplacesGMRPanel, parksGMRPanel, totalDeathsPanel, avgTotalCasesPanel, highestDeathPanel;
    @FXML
    private HBox statisticPanel, mapPanel;
    @FXML
    private AnchorPane mainAnchor, mapButtonPanel, mapSVGPanel, graphPanel;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private Text dateRangeText, totalCaseText, dateText, totalDeathText;
    @FXML
    private Text workGMRAvgText, workGMRTotalText, workGMRDenomText, workGMRCalcText;
    @FXML
    private Text parkGMRAvgText, parkGMRTotalText, parkGMRDenomText, parkGMRCalcText;
    @FXML
    private Text avgTotalCasesText, highestDeathText;
    @FXML
    private Button rightButton, leftButton;
    @FXML
    private LineChart<?,?>Linechart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
   
    //Object fields.
    private ArrayList<Pane> panelList, subpanelList;
    private int panelListIndex, subpanelListIndex;
    private Label  boroughNameLabel;
    private Label  selectedDatesLabel;
    private TableView<CovidData> tableView;
    private LocalDate minDate, maxDate, startDate, endDate;
    private boolean DateSelected;
    private CovidDataLoader loader;
    private ArrayList<CovidData> data, filteredData;
    private int maxDeaths;

    /**
     * This is the constructor for the COVIDController class
     * It initializes the panelList and subpanelList
     * It also initializes the minDate and maxDate
     * Then creates a new COVIDDataLoader object.
     *
     */
    public COVIDController() {
        panelList = new ArrayList<>();
        panelListIndex = 0;

        subpanelList = new ArrayList<>();
        subpanelListIndex = 0;

        DateSelected = false;
        minDate = LocalDate.of(2020, 02, 3);
        maxDate = LocalDate.of(2023, 02, 9);
        maxDeaths = 1250;
       
        loader = new CovidDataLoader();
    }

    /**
     * This method is called when the application is first loaded
     * It initializes the data arraylist
     * It then adds the panels to the panelList
     * It then adds the subpanels to the subpanelList
     * It then formats the datepickers.
     *
     */
    public void initialize() {
        data = loader.load();
       
        panelList.add(welcomePanel);
        panelList.add(mapPanel);
        panelList.add(statisticPanel);
        panelList.add(graphPanel);

       
        subpanelList.add(workplacesGMRPanel);
        subpanelList.add(parksGMRPanel);
        subpanelList.add(totalDeathsPanel);
        subpanelList.add(avgTotalCasesPanel);
        subpanelList.add(highestDeathPanel);
       
        startDatePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});

        endDatePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});

        startDatePicker.setShowWeekNumbers(false);
        endDatePicker.setShowWeekNumbers(false);

        startDatePicker.setValue(minDate);
        endDatePicker.setValue(maxDate);

        // Add an onAction event handler to each SVG in mappanel        
        for (Node node : mapSVGPanel.getChildren()) {
            if (node instanceof SVGPath) {
                SVGPath svg = (SVGPath) node;
                Tooltip svgHoverName = new Tooltip(svg.getId());
                svgHoverName.setShowDelay(Duration.seconds(0));
                svgHoverName.setHideDelay(Duration.seconds(0));
               
                Tooltip.install(svg, svgHoverName);
                svg.setOnMouseClicked(e -> {
                            try {
                                // Load the new FXML file
                                URL url = getClass().getResource("showdetails.fxml");
                                FXMLLoader FXMLLoader = new FXMLLoader(url);

                                //pass the required data to the new Controller
                                FXMLLoader.setController(new ShowDetailsController(filteredData, svg.getId(), startDate, endDate, boroughNameLabel,selectedDatesLabel,  tableView));

                                Parent root = FXMLLoader.load();

                                // Create a new scene and add the FXML file to it
                                Scene scene = new Scene(root);

                                // Create a new stage and set the scene as its scene
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                    });
            }
        }
    }

    /**
     * This method is called when the user clicks the right arrow button
     * It hides the current panel and calls the nextPanel method
     *
     * @throws IOException
     */
    public void nextPanel() {
        panelListIndex++;
        try {
            panelList.get(panelListIndex).setVisible(true);
        } catch (Exception e) {
            panelListIndex = 0;
            panelList.get(panelListIndex).setVisible(true);
        }
    }

    /**
     * This method is called when the user clicks the left arrow button
     * It hides the current panel and calls the prevPanel method
     *
     * @throws IOException
     */
    public void prevPanel() {
        panelListIndex--;
        try {
            panelList.get(panelListIndex).setVisible(true);
        } catch (Exception e) {
            panelListIndex = panelList.size() - 1;
            panelList.get(panelListIndex).setVisible(true);
        }
    }

    /**
     * This method is called when the user clicks the right arrow button
     * It hides the current panel and calls the nextPanel method
     *
     * @throws IOException
     */
    public void rightArrowClick() throws IOException {
        panelList.get(panelListIndex).setVisible(false);
        nextPanel();    
    }


    /**
     * This method is called when the user clicks the left arrow button
     * It hides the current panel and calls the prevPanel method
     *
     * @throws IOException
     */
    public void leftArrowClick() throws IOException {
        panelList.get(panelListIndex).setVisible(false);
        prevPanel();
    }


    /**
     * This function is called when the user selects a date range.
     * It validates the dates, sets the date range text,
     * loads new data, sets the statistics, and colors the boroughs.
     */
    public void handleDateSelection() {
        // DateSelected=true;
        startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();
        Alert alert = new Alert(AlertType.ERROR);

        if (!validateDates(startDate, endDate)) {
            alert.setTitle("Invalid Dates Selected");
            alert.setHeaderText("Please select a valid date range.");
            alert.showAndWait();

            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            return;
        }

        DateSelected = true;
        dateRangeText.setText("Selected date range: "+ startDate.toString() + " to " + endDate.toString() + ".");
        loadNewData(startDate, endDate);
        setStatistics(this.getFilteredData());
        colorBoroughs(endDate, this.getFilteredData());
        rightButton.setDisable(false);
        leftButton.setDisable(false);
    }

    /**
     * If the start date is after the end date, or the end date is after the max date, or the start
     * date is before the min date, then return false. Otherwise, return true.
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return If the dates are valid.
     */
    private boolean validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }

        if (startDate.isAfter(endDate) || endDate.isAfter(maxDate) || startDate.isBefore(minDate)) {
            return false;
        }

        else {
            return true;
        }
    }

    /**
     * For each SVGPath in the mapSVGPanel, if the SVGPath's id is a key in the boroughDeaths HashMap,
     * set the SVGPath's fill to a color calculated from the value in the HashMap
     *
     * @param lastDate The date of the last data point in the data set.
     * @param dataSet The ArrayList of CovidData objects that we're using to get the data from.
     */
    public void colorBoroughs(LocalDate lastDate, ArrayList<CovidData> dataSet) {

        HashMap<String, Integer> boroughDeaths = dataSet.stream()
        .filter(data -> data.getDate().isEqual(lastDate))
        .collect(Collectors.toMap(CovidData::getBorough, CovidData::getTotalDeaths, Integer::sum, HashMap::new));

        for (Node node : mapSVGPanel.getChildren()) {
            if (node instanceof SVGPath) {
                SVGPath svg = (SVGPath) node;
                String svgName = svg.getId();

                if (boroughDeaths.containsKey(svgName)) {
                    int totalDeaths = boroughDeaths.get(svgName);
                    svg.setFill(calculateRedValue(totalDeaths, maxDeaths));
                }
                else {
                    System.out.println("No borough with name " + svgName + " found.");
                }
            }
        }
    }

    /**
     * Returns a color object, where the redness,
     * depends on how many deaths there were in the borough.
     *
     * @param totalDeaths The number of deaths in the borough
     * @param maxDeaths The maximum number of deaths in a single borough.
     * @return A color object.
     */
    public javafx.scene.paint.Color calculateRedValue(int totalDeaths, int maxDeaths) {
        float redness = totalDeaths / (float) maxDeaths;
        int RGBDiff = Math.round(255 * (1 - redness));
        return javafx.scene.paint.Color.rgb(255, RGBDiff, RGBDiff);
    }

    /**
     * This method is called when the user clicks the right arrow button
     * on the statistics panel.
     *
     * @throws IOException
     */
    public void subnextPanel() {
        subpanelListIndex++;
        try {
            subpanelList.get(subpanelListIndex).setVisible(true);
        } catch (Exception e) {
            subpanelListIndex = 0;
            subpanelList.get(subpanelListIndex).setVisible(true);
        }
    }

    /**
     * This method is called when the user clicks the left arrow button
     * on the statistics panel.
     *
     * @throws IOException
     */
    public void subprevPanel() {
        subpanelListIndex--;
        try {
            subpanelList.get(subpanelListIndex).setVisible(true);
        } catch (Exception e) {
            subpanelListIndex = subpanelList.size() - 1;
            subpanelList.get(subpanelListIndex).setVisible(true);
        }
    }

    /**
     * This method is called when the user clicks the right arrow button
     * on the statistics panel.
     *
     * @throws IOException
     */
    public void subrightArrowClick() throws IOException {
        subpanelList.get(subpanelListIndex).setVisible(false);
        subnextPanel();    
    }

    /**
     * This method is called when the user clicks the left arrow button
     * on the statistics panel.
     *
     * @throws IOException
     */
    public void subleftArrowClick() throws IOException {
        subpanelList.get(subpanelListIndex).setVisible(false);
        subprevPanel();

    }
   
    /**
     * This function filters the data to only include data between the start and end dates
     *
     * @param startDate The start date of the data you want to load.
     * @param endDate The end date of the data to be loaded.
     */
    public void loadNewData(LocalDate startDate, LocalDate endDate) {
        filteredData = data.stream()
        .filter(covidData -> (covidData.getDate().isAfter(startDate) || covidData.getDate().isEqual(startDate))
        && (covidData.getDate().isBefore(endDate) || covidData.getDate().isEqual(endDate)))
        .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This function returns the filtered data
     *
     * @return The filtered data is being returned.
     */
    public ArrayList<CovidData> getFilteredData() {
        return filteredData;
    }

    /**
     * It takes in an ArrayList of CovidData objects, and then calculates the average GMR values for
     * parks and workplaces, the total number of deaths, the average number of cases per borough, and
     * the date with the highest number of deaths
     *
     * @param dataList The list of CovidData objects that have been selected by the user.
     */
    public void setStatistics(ArrayList<CovidData> dataList) {

        XYChart.Series series1 = new XYChart.Series();
       
        int parksGMRSum = 0;
        int workplacesGMRSum = 0;
        int GMRDenominator = dataList.size();
       
        int totalDeathSum = 0;
        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;
       
        int totalCaseSum = 0;
        int noOfBoroughs = 33;

        TreeMap<String, Integer> newCasesByDate = new TreeMap<String, Integer>();

       
        for (CovidData data : dataList) {
            parksGMRSum = parksGMRSum + data.getParksGMR();
            workplacesGMRSum = workplacesGMRSum + data.getWorkplacesGMR();
           
            if (data.getDate().equals(endDate)) {
                totalDeathSum = totalDeathSum + data.getTotalDeaths();
                totalCaseSum = totalCaseSum + data.getTotalCases();
            }
           
            if (newCasesByDate.containsKey(data.getDateString())) {
                int totalNewCases = newCasesByDate.get(data.getDateString()) + data.getNewCases();
                newCasesByDate.put(data.getDateString(), totalNewCases);
            } else {
                newCasesByDate.put(data.getDateString(), data.getNewCases());
            }
           
        }

        int parksGMRAvg = parksGMRSum / GMRDenominator;
        int workplacesGMRAvg = workplacesGMRSum / GMRDenominator;
       
        int averageTotalCases = totalCaseSum / noOfBoroughs;
       
        String highestTotalDeathDay = endDate.toString();

        for (Map.Entry<String, Integer> entry : newCasesByDate.entrySet()) {
            String date = entry.getKey();
            int totalNewCases = entry.getValue();
            series1.getData().add(new XYChart.Data(date, totalNewCases));
        }
       
        Linechart.getData().clear();
        Linechart.getData().add(series1);
       
        workGMRAvgText.setText("The average workplaces GMR value over the selected period is " + workplacesGMRAvg + ".");
        workGMRTotalText.setText("Cumulative sum: " + workplacesGMRSum);
        workGMRDenomText.setText("Number of entries: " + GMRDenominator);
        workGMRCalcText.setText("Calculation: " + workplacesGMRSum + " / " + GMRDenominator + " = " + workplacesGMRAvg);
       
        parkGMRAvgText.setText("The average parks GMR value over the selected period is " + parksGMRAvg + ".");
        parkGMRTotalText.setText("Cumulative sum: " + parksGMRSum);
        parkGMRDenomText.setText("Number of entries: " + GMRDenominator);
        parkGMRCalcText.setText("Calculation: " + parksGMRSum + " / " + GMRDenominator + " = " + parksGMRAvg);
               
        totalDeathText.setText("The total number of COVID deaths over the selected period was " + totalDeathSum + " over " + days + " days.");
       
        avgTotalCasesText.setText("The average number of COVID cases per borough at the end of the period is " + averageTotalCases + ".");
       
        highestDeathText.setText("The date with the highest number of total COVID deaths in the period is " + highestTotalDeathDay + ", the last selected day.");
    }
} 