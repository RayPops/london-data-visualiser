import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents one record in the COVID dataset.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */

public class CovidData {

    /*
    The date the COVID information (cases & deaths) was collected
    */
    private String date;
   
    /*
    The COVID information is organised by (London) borough
    */
    private String borough;
   
    /*
    The COVID information that's collected daily for each London borough
    */
    private int newCases;
    private int totalCases;
   
    private int newDeaths;
    private int totalDeaths;
   
   
    /*
    Google analysed location data from Android smartphones to measure movement
    in London.  The data shows percent change from the baseline.  For example,
    a negative value means there's less human traffic compared to the baseline.
    */
    private int retailRecreationGMR;
    private int groceryPharmacyGMR;
    private int parksGMR;
    private int transitGMR;
    private int workplacesGMR;
    private int residentialGMR;


    /**
     * This is the constructor for the CovidData class.
     *
     * @param date The date of the record.
     * @param borough The borough of the record.
     * @param retailRecreationGMR The retail and recreation mobility report.
     * @param groceryPharmacyGMR The grocery and pharmacy mobility report.
     * @param parksGMR The parks mobility report.
     * @param transitGMR The transit mobility report.
     * @param workplacesGMR The workplaces mobility report.
     * @param residentialGMR The residential mobility report.
     * @param newCases The new cases.
     * @param totalCases The total cases.
     * @param newDeaths The new deaths.
     * @param totalDeaths The total deaths.
     */
    public CovidData(String date, String borough, int retailRecreationGMR, int groceryPharmacyGMR,
                        int parksGMR, int transitGMR, int workplacesGMR, int residentialGMR,
                        int newCases, int totalCases,int newDeaths,int totalDeaths) {

        this.date = date;
        this.borough = borough;
        this.retailRecreationGMR = retailRecreationGMR;
        this.groceryPharmacyGMR = groceryPharmacyGMR;
        this.parksGMR = parksGMR;
        this.transitGMR = transitGMR;
        this.workplacesGMR = workplacesGMR;
        this.residentialGMR = residentialGMR;
        this.newCases = newCases;
        this.totalCases = totalCases;
        this.newDeaths = newDeaths;
        this.totalDeaths =  totalDeaths;
    }


    /**
     * This function returns the date string.
     *
     * @return The date string.
     */
    public String getDateString() {
        return date;
    }
   
    /**
     * It takes a string in the format of "yyyy-MM-dd" and returns a LocalDate object
     *
     * @return A LocalDate object
     */
    public LocalDate getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dtf);
    }


    /**
     * This function returns the borough.
     *
     * @return The borough.
     */
    public String getBorough() {
        return borough;
    }


    /**
     * This function returns the retail and recreation mobility report
     *
     * @return The retailRecreationGMR is being returned.
     */
    public int getRetailRecreationGMR() {
        return retailRecreationGMR;
    }


    /**
     * This function returns the value of the variable groceryPharmacyGMR
     *
     * @return The value of the variable groceryPharmacyGMR.
     */
    public int getGroceryPharmacyGMR() {
        return groceryPharmacyGMR;
    }


    /**
     * This function returns the google mobility report for parks.
     *
     * @return The value of the parksGMR variable.
     */
    public int getParksGMR() {
        return parksGMR;
    }


    /**
     * This function returns the transit GMR of the borough.
     *
     * @return The transitGMR is being returned.
     */
    public int getTransitGMR() {
        return transitGMR;
    }


    /**
     * This function returns the google mobility report for workplaces.
     *
     * @return The value of the variable workplacesGMR.
     */
    public int getWorkplacesGMR() {
        return workplacesGMR;
    }

    /**
     * This function returns the google mobility report for residential areas.
     *
     * @return The value of the variable residentialGMR.
     */
    public int getResidentialGMR() {
        return residentialGMR;
    }

    /**
     * This function returns the new cases.
     *
     * @return The new cases.
     */
    public int getNewCases() {
        return newCases;
    }

    /**
     * This function returns the total cases.
     *
     * @return The total cases.
     */
    public int getTotalCases() {
        return totalCases;
    }

    /**
     * This function returns the new deaths.
     *
     * @return The new deaths.
     */
    public int getNewDeaths() {
        return newDeaths;
    }

    /**
     * This function returns the total deaths.
     *
     * @return The total deaths.
     */
    public int getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * This function returns the string representation of the CovidData object.
     *
     * @return The string representation of the CovidData object.
     */
    @Override
    public String toString() {
        return "Covid Record {" +
        " date='" + date +'\'' +
        ", borough='" + borough +'\'' +
        ", retailRecreationGMR=" + retailRecreationGMR +
        ", groceryPharmacyGMR=" + groceryPharmacyGMR +
        ", parksGMR=" + parksGMR +
        ", transitGMR=" + transitGMR +
        ", workplacesGMR=" + workplacesGMR +
        ", residentialGMR=" + residentialGMR +
        ", newCases=" + newCases +
        ", totalCases=" + totalCases +
        ", newDeaths=" + newDeaths +
        ", totalDeaths=" + totalDeaths +
        "}";
    }
}