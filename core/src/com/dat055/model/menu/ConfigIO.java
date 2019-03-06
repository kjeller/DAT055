package com.dat055.model.menu;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ConfigIO {
    public static void save(Map<String,String> bindings,String fileName) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
        for (Map.Entry<String,String> e: bindings.entrySet())
            printWriter.println(e.getKey() + ":" + e.getValue());
        printWriter.close();
    }
    public static Map<String,String> load(String fileName)throws IOException {
        Map<String,String> result = new TreeMap();
        Scanner scanner = new Scanner(new FileReader(fileName));
        while (scanner.hasNextLine()) {
            String[] row = scanner.nextLine().split(":");
            checkRow(row);
            result.put(row[0],row[1]);
        }
        scanner.close();
        return result;
    }
    private static void checkRow(String[] row) throws IOException{
        if (row.length != 2)
            throw new IOException("Illegal data format in config file.");
    }
}
