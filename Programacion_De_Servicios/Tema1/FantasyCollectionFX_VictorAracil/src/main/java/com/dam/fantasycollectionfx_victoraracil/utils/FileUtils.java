package com.dam.fantasycollectionfx_victoraracil.utils;

import com.dam.fantasycollectionfx_victoraracil.model.Item;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class FileUtils {

    //Nombre del archivo
    private static final String FILE_NAME = "items.txt";

    //Formato de fecha
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Load all items in the file line to line
     */
    public static List<Item> loadItems() {
        try {
            Path path = Paths.get(FILE_NAME);

            //If file not exist, return empty list
            if (!Files.exists(path)) {
                System.out.println("File not found, list will be empty.");
                return new ArrayList<>();
            }

            //Read all lines
            return Files.lines(path)
                    .skip(1) //skip first line if is heather
                    .map(line -> {
                        String[] parts = line.split(";");
                        if (parts.length == 5) {
                            try {
                                return new Item(
                                        parts[0].trim(),                   // code
                                        parts[1].trim(),                   // name
                                        parts[2].trim(),                   // type
                                        parts[3].trim(),                   // rarity
                                        LocalDate.parse(parts[4].trim(), FORMATTER) // obtainedDate
                                );
                            } catch (Exception e) {
                                System.err.println("Error parsing line: " + line);
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Save the list in the file
     */
    public static void saveItems(List<Item> items) {
        try {
            Path path = Paths.get(FILE_NAME);

            //Buil the file content
            List<String> lines = new ArrayList<>();
            lines.add("code;name;type;rarity;obtained_date"); // cabecera

            for (Item item : items) {
                String line = String.format("%s;%s;%s;%s;%s",
                        item.getCode(),
                        item.getName(),
                        item.getType(),
                        item.getRarity(),
                        item.getObtainedDate().format(FORMATTER));
                lines.add(line);
            }

            //Write the full file
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Items saved successfully to " + FILE_NAME);

        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}

