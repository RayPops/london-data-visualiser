import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the ShowDetailsController class.
 * It tests the constructor and the initialize method.
 * It also tests the setBoroughNameLabel method, the setSelectedDatesLabel method,
 * and the setTableView method.
 *
 * @author Rayan Popat, James Coward and Shicheng Li.
 */
public class ShowDetailsControllerTest {
    private ShowDetailsController controller;
    private ArrayList<CovidData> testData;
    private String testBoroughName;
    private LocalDate testStartDate;
    private LocalDate testEndDate;

    private Label boroughNameLabel;
    private Label selectedDatesLabel;
    private TableView<CovidData> tableView;


    /**
     * This method is called before each test.
     * It creates a new ShowDetailsController object.
     * It also creates a new ArrayList of CovidData objects,
     * and adds three test CovidData objects to it.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        testData = new ArrayList<CovidData>();
        testData.add(new CovidData("2022-01-01", "Borough 1", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        testData.add(new CovidData("2022-01-02", "Borough 1", 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        testData.add(new CovidData("2022-01-03", "Borough 2", 21, 22, 23, 24, 25, 26, 27, 28, 29, 30));
        tableView = new TableView<>();
        boroughNameLabel=new Label("Borough 1");
        testStartDate = LocalDate.parse("2022-01-01");
        testEndDate = LocalDate.parse("2022-01-02");
        testBoroughName="Borough 1";
        controller = new ShowDetailsController(testData, testBoroughName, testStartDate, testEndDate, boroughNameLabel, selectedDatesLabel, tableView);
    }

    /**
     * This method asserts that the borough name label is set correctly.
     */
    @Test
    public void testSetBoroughNameText() {
        ShowDetailsController controller = new ShowDetailsController(null, null, null, null,boroughNameLabel,selectedDatesLabel, tableView );
        controller.setBoroughNameText("Test Borough");
        assertEquals("Test Borough", controller.getBoroughNameLabel().getText());
    }

    /**
     * This method asserts that the table view is set correctly.
     */
    @Test
    public void testSetFilteredData() {
        ArrayList<CovidData> newData = new ArrayList<CovidData>();
        newData.add(new CovidData("2022-01-01", "Borough 1", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        controller.setFilteredData(newData);
        assertEquals(newData, controller.getfilteredData());
    }

    /**
     * This method asserts that the table view is populated correctly.
     */
    @Test
    public void testPopulateTable() {
       
        controller.populateTable(testData, testBoroughName);
        assertEquals(2, controller.gettableView().getItems().size());
    }
}
