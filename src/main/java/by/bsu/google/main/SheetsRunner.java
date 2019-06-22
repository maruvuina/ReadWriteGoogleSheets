package by.bsu.google.main;

import by.bsu.google.action.SheetsGoogle;

import java.io.IOException;
import java.util.List;


public class SheetsRunner {
    public static void main(String[] args) {
        List<List<Object>> lists;
        try {
            SheetsGoogle.writeToSheet();
            lists = SheetsGoogle.readToSheet();
            SheetsGoogle.writeToFile(lists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
