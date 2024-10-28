import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Conviction {
    String id;
    String charge;
    String chargeType;
    String disposition;
    String dispositionDate;

    public Conviction(String id, String charge, String chargeType, String disposition, String dispositionDate) {
        this.id = id;
        this.charge = charge;
        this.chargeType = chargeType;
        this.disposition = disposition;
        this.dispositionDate = dispositionDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Charge: " + charge + ", Charge Type: " + chargeType +
                ", Disposition: " + disposition + ", Disposition Date: " + dispositionDate;
    }
}

interface DataSet {

    public static List<Conviction> readFelonyConvictions(String filePath) {
        List<Conviction> felonyConvictions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the header line
            String header = br.readLine();
            String[] headers = header.split(",");
            int idIndex = findIndex(headers, "id");
            int chargeIndex = findIndex(headers, "charge");
            int chargeTypeIndex = findIndex(headers, "charge_type");
            int dispositionIndex = findIndex(headers, "disposition");
            int dispositionDateIndex = findIndex(headers, "disposition_date");

            // Read the data lines
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > chargeTypeIndex && data[chargeTypeIndex].equalsIgnoreCase("Felony")) {
                    String id = data[idIndex];
                    String charge = data[chargeIndex];
                    String chargeType = data[chargeTypeIndex];
                    String disposition = data[dispositionIndex];
                    String dispositionDate = data[dispositionDateIndex];

                    felonyConvictions.add(new Conviction(id, charge, chargeType, disposition, dispositionDate));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return felonyConvictions;
    }

    private static int findIndex(String[] headers, String target) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1; // Not found
    }

    public static void main(String[] args) {
        String filePath = "North_Carolina_Criminal_Records.csv"; // Update this to your CSV file path
        List<Conviction> felonyConvictions = readFelonyConvictions(filePath);

        // Print felony convictions
        for (Conviction conviction : felonyConvictions) {
            System.out.println(conviction);
        }
    }
}
