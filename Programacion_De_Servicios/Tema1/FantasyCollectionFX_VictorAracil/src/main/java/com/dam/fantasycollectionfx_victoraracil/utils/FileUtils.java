package com.dam.fantasycollectionfx_victoraracil.utils;

import com.dam.fantasycollectionfx_victoraracil.model.Item;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class to load and save Item objects.
 * Reads items.txt from resources (first load),
 * and writes items.txt in the project root when saving.
 */
public class FileUtils {

    //Data format
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Name of file
    private static final String ROOT_FILE = "items.txt";

    /**
     * Loads all items from items.txt.
     * First tries to read from the root (if exists),
     * if no, reads from the /resources/ folder inside the package.
     */
    public static List<Item> loadItems() {
        List<Item> items = new ArrayList<>();

        try {
            Path rootPath = Paths.get(ROOT_FILE);

            BufferedReader reader;

            //root file
            if (Files.exists(rootPath)) {
                System.out.println("Reading items.txt from project root.");
                reader = Files.newBufferedReader(rootPath);

            } else {
                //read in resources if in root not exist
                InputStream resourceStream = FileUtils.class.getResourceAsStream(
                        "/com/dam/fantasycollectionfx_victoraracil/items.txt");

                if (resourceStream == null) {
                    System.out.println("No items.txt found in resources or root — starting empty list.");
                    return new ArrayList<>();
                }

                System.out.println("Reading items.txt from resources.");
                reader = new BufferedReader(new InputStreamReader(resourceStream));
            }

            items = reader.lines()
                    .skip(1) //skip heather
                    .map(line -> {
                        try {
                            String[] parts = line.split(";");
                            if (parts.length == 5) {
                                return new Item(
                                        parts[0].trim(),
                                        parts[1].trim(),
                                        parts[2].trim(),
                                        parts[3].trim(),
                                        LocalDate.parse(parts[4].trim(), FORMATTER)
                                );
                            }
                        } catch (Exception e) {
                            System.err.println("Error parsing line: " + line);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            reader.close();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return items;
    }

    /**
     * Saves the given list of items to items.txt.
     */
    public static void saveItems(List<Item> items) {
        try {
            Path path = Paths.get(ROOT_FILE);

            List<String> lines = new ArrayList<>();
            lines.add("code;name;type;rarity;obtained_date");

            for (Item item : items) {
                lines.add(String.format("%s;%s;%s;%s;%s",
                        item.getCode(),
                        item.getName(),
                        item.getType(),
                        item.getRarity(),
                        item.getObtainedDate().format(FORMATTER)));
            }

            //save in proyect root
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Items saved successfully to project root (" + path.toAbsolutePath() + ")");

        } catch (IOException e) {
            System.err.println("Error saving items: " + e.getMessage());
        }
    }
}
