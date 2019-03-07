package com.dat055.model.menu;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 * A class that saves or loads from the config file
 *
 * @author Pontus Johansson
 * @version 2019-02-27
 */
public class ConfigIO {

    /**
     * method that saves to the file
     * @param map the map that is to be saved
     * @param fileName the name of the file
     * @throws IOException if file cannot be written
     */
    public static void save(Map<String,String> map,String fileName) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
        for (Map.Entry<String,String> e: map.entrySet())
            printWriter.println(e.getKey() + ":" + e.getValue());
        printWriter.close();
    }

    /**
     * A method that loads from a file
     * @param fileName the name of the file
     * @return the map containing the strings from the file
     * @throws IOException if file cannot be read
     */
    public static Map<String,String> load(String fileName)throws IOException {
        Map<String,String> resultMap = new TreeMap();
        Scanner scanner = new Scanner(new FileReader(fileName));
        while (scanner.hasNextLine()) {
            String[] row = scanner.nextLine().split(":");
            checkRow(row);
            resultMap.put(row[0],row[1]);
        }
        scanner.close();
        return resultMap;
    }

    /**
     * makes sure that the rows are the correct length
     * @param row the row that is checked
     * @throws IOException if row is not correct length
     */
    private static void checkRow(String[] row) throws IOException{
        if (row.length != 2)
            throw new IOException("Illegal data format in config file.");
    }
}
