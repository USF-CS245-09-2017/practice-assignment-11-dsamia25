import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadFile {

    public ReadFile() {

    }

    /**
     * Reads a file and compiles each line as a new node in a list
     *
     * @param fileLocation the location of the file to read
     * @return a list of Strings to sort through
     * @throws IllegalArgumentException in case it doesnt work
     */
    public List<String> readFile(String fileLocation) throws IllegalArgumentException {
        List<String> list = new LinkedList<>();
        if (fileLocation == null) {
            System.out.println("Invalid Input");
            throw new IllegalArgumentException();
        }
        try {
            java.io.FileReader read = new java.io.FileReader(fileLocation);
            BufferedReader buffRead = new BufferedReader(read);
            String line;
            while ((line = buffRead.readLine()) != null) {
                try {
                    try {
                        //Adds the read line to the list to output
                        list.add(line.substring(line.indexOf('['), line.indexOf(']') + 1));
                    } catch (IndexOutOfBoundsException except) {

                    }
                } catch (IllegalArgumentException illegal) {

                }

            }
        } catch (IOException except) {
            except.printStackTrace();
        }
        return list;
    }
}
