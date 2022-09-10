package by.bsu.google.action;

import by.bsu.google.sheets.SheetsService;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class SheetsGoogle {

    private static Sheets sheetsService;

    private static final String SPREADSHEET_ID = "19LiTF2ZjIBBn-v9YspiVFAW5j_e7PvMJCAx8XIZwDDs";

    public static Sheets getSheetsService() {
        return sheetsService;
    }

    private SheetsGoogle() {}

    public static void setUp() throws GeneralSecurityException, IOException {
        SheetsGoogle.sheetsService = SheetsService.getSheetsService();
    }

    public static List<List<Object>> readToSheet() throws IOException {
        try {
            setUp();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        final String range = "Data!A2:B12";
        Sheets service = getSheetsService();
        ValueRange response = service.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        return response.getValues();
    }

    public static void writeToSheet() throws IOException {
        try {
            setUp();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        ValueRange body = new ValueRange();
        body.setValues(
                Arrays.asList(
                        Arrays.asList("books", "30"),
                        Arrays.asList("pens", "10"),
                        Arrays.asList("clothes", "20"),
                        Arrays.asList("shoes", "5")));

        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "A13", body)
                .setValueInputOption("RAW").execute();
        System.out.println("Result: " + result);
    }

    public static void writeToFile(List<List<Object>> values) throws IOException {
        File file = new File("output.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            throw new RuntimeException("No data found.");
        }

        System.out.println("Departments | Number of employees");
        bufferedWriter.write("Faculty of Mechanics and Mathematics");
        bufferedWriter.newLine();
        bufferedWriter.write("Departments--------->Number of employees");
        bufferedWriter.newLine();

        for (List<Object> row : values) {
            // Print columns A and B, which correspond to indices 0 and 1.
            System.out.printf("%3s | %4s\n", row.get(0), row.get(1));
            bufferedWriter.newLine();
            bufferedWriter.write( row.get(0) + "---------->" + row.get(1));
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
