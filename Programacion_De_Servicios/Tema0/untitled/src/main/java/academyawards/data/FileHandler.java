package academyawards.data;

import academyawards.data.Nomination;
import academyawards.statistics.FilmStatistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static List<Nomination> readNominationFile(String filePath) {
        List<Nomination> nominations = new ArrayList<>();


        try (BufferedReader inputFile = new BufferedReader(
                new FileReader(filePath))) {

            String line = null;
            while ((line = inputFile.readLine()) != null) {
                String[] parts = line.split("\t");

                try {
                    Nomination nomination = new Nomination(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            parts[3],
                            parts[4],
                            parts[5],
                            Boolean.parseBoolean(parts[6])
                    );
                    nominations.add(nomination);
                }catch (Exception e){
                    System.out.println("Error parsing line: "+line);
                    e.printStackTrace();
                }



            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }


        return nominations;
    }


    public static void writeFilmStatistics(List<FilmStatistics> filmStatisticsList, String filePath) {
        try (PrintWriter printWriter = new PrintWriter (filePath))
        {
            for (FilmStatistics filmStatistics:filmStatisticsList
                 ) {
                printWriter.println(filmStatistics);
            }



        }
        catch (IOException e)
        {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
