import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import java.util.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.*;
import javafx.scene.text.Text;
import java.time.LocalDate;

/**
 * This class controls the window that pops up
 * when you click on a borough on the Map panel of
 * the application. It populates a table with CovidData
 * objects filtered by borough and date range,
 * and displays the borough name and date range in labels.
 *
 * @author Rayan Popat, James Coward and Shicheng Li.
 */

public class ShowDetailsController {
    @FXML
    private Label boroughDetailsLabel;
    @FXML
    private TableView tableView;
    @FXML
    private Label boroughNameLabel;
    @FXML
    private Label selectedDatesLabel;

    private ArrayList<CovidData> filteredData;
    private String boroughName;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructor for the class.
     * Passes on the following parameters to the class:
     * @param filteredData The filtered data.
     * @param boroughName The borough name.
     * @param startDate The start date.
     * @param endDate The end date.
     *
     * The following parameters are passed on to the initialize method:
     * @param boroughNameLabel The borough name label.
     * @param selectedDatesLabel The selected dates label.
     * @param tableView The table view.
     */
    public ShowDetailsController(ArrayList<CovidData> filteredData, String boroughName, LocalDate startDate, LocalDate endDate,Label boroughNameLabel,Label selectedDatesLabel, TableView<CovidData> tableView) {
        this.filteredData = filteredData;
        this.boroughName = boroughName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.boroughNameLabel=boroughNameLabel;
        this.selectedDatesLabel = selectedDatesLabel;
        this.tableView = tableView;
    }

    /**
     * This method is called when the window is opened.
     * It sets the borough name label, the selected dates label,
     * and the table view.
     */
    public void initialize() {
        setFilteredData(filteredData);
        populateTable(filteredData, boroughName);
        setBoroughNameText(boroughName);      
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Sets the borough name label.
     * @param buttonName The name of the borough.
     */
    public void setBoroughNameText (String buttonName) {
        boroughNameLabel.setText(buttonName);
    }

    /**
     * Sets the filtered data.
     * @param dataList The filtered data.
     */
    public void setFilteredData (ArrayList<CovidData> dataList) {
        filteredData = dataList;
    }

    /**
     * Populates the table view with the filtered data.
     * @param dataList The filtered data.
     * @param svgName The name of the SVG (which is also the borough name).
     */
    public void populateTable(ArrayList<CovidData> dataList, String svgName) {

        // Define the columns for the TableView
        TableColumn<CovidData, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<CovidData, Integer> retailCol = new TableColumn<>("Retail/Recreation GMR");
        retailCol.setCellValueFactory(new PropertyValueFactory<>("retailRecreationGMR"));

        TableColumn<CovidData, Integer> groceryCol = new TableColumn<>("Grocery/Pharmacy GMR");
        groceryCol.setCellValueFactory(new PropertyValueFactory<>("groceryPharmacyGMR"));

        TableColumn<CovidData, Integer> parksCol = new TableColumn<>("Parks GMR");
        parksCol.setCellValueFactory(new PropertyValueFactory<>("parksGMR"));

        TableColumn<CovidData, Integer> transitCol = new TableColumn<>("Transit GMR");
        transitCol.setCellValueFactory(new PropertyValueFactory<>("transitGMR"));

        TableColumn<CovidData, Integer> workplacesCol = new TableColumn<>("Workplaces GMR");
        workplacesCol.setCellValueFactory(new PropertyValueFactory<>("workplacesGMR"));

        TableColumn<CovidData, Integer> residentialCol = new TableColumn<>("Residential GMR");
        residentialCol.setCellValueFactory(new PropertyValueFactory<>("residentialGMR"));

        TableColumn<CovidData, Integer> newCasesCol = new TableColumn<>("New Cases");
        newCasesCol.setCellValueFactory(new PropertyValueFactory<>("newCases"));

        TableColumn<CovidData, Integer> totalCasesCol = new TableColumn<>("Total Cases");
        totalCasesCol.setCellValueFactory(new PropertyValueFactory<>("totalCases"));

        TableColumn<CovidData, Integer> newDeathsCol = new TableColumn<>("New Death");
        newDeathsCol.setCellValueFactory(new PropertyValueFactory<>("newDeaths"));
        // Add the columns to the TableView
        tableView.getColumns().addAll(dateCol, retailCol, groceryCol, parksCol, transitCol,
            workplacesCol, residentialCol, newCasesCol, totalCasesCol,newDeathsCol);

        for (CovidData data : filteredData) {
            if (data.getBorough().contains(svgName)) {
                tableView.getItems().add(data);
            }
        }
    }    
     
    /**
     * Returns the filtered data.
     * @return The filtered data.
     */
    public ArrayList<CovidData> getfilteredData() {
        return  filteredData;
    }

    /**
     * Returns the borough name.
     * @return The borough name.
     */
    public Label getBoroughNameLabel()
    {
        return boroughNameLabel;
    }

    /**
     * Returns the selected dates label.
     * @return The selected dates label.
     */
    public Label getboroughDetailsLabel()
    {
        return boroughDetailsLabel;
    }

    /**
     * Returns the table view.
     * @return The table view.
     */
    public Label getselectedDatesLabel()
    {
        return selectedDatesLabel;
    }

    /**
     * Returns the borough name label.
     * @return The borough name label.
     */
    public  TableView gettableView()
    {
        return tableView;
    }
}