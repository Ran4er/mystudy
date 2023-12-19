import java.io. *;
import com.opencsv.CSVReader;

public class Main {
    public static void main(String[] args) {
                String fileName = "/home/ru6ik/Desktop/mystudy/Programming/lab5/src/main/java/ru/ifmo/prog/lab5/file.csv";

                try (FileInputStream fos = new FileInputStream(fileName); BufferedInputStream bufferedReader = new BufferedInputStream(fos)) {

                    var csvBuilder = new StringBuilder();
                    int charEquals;

                    while ((charEquals = bufferedReader.read()) != -1) {

                        csvBuilder.append((char) charEquals);

                    }

                     String[] finalParseArgs = csvBuilder.substring(80).split(",");

                    for(String element : finalParseArgs) System.out.print(element + " ");

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
    }
}