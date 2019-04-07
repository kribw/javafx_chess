import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindWordNoUI {
    private static List<File> allFiles = new ArrayList<>();
    private static int scannedDirectories = 0;
    private static int scannedFiles = 0;
    private static int wordsFound = 0;


    public static void main(String[] args) {
        System.out.print("Enter a directory or file name: ");
        Scanner input = new Scanner(System.in);
        String path = input.next();
        listFiles(path);
        System.out.print("Enter a word to search for: ");
        String word = input.next();
        searchFiles(allFiles, word);
    }

    private static void listFiles(String path) {
        scannedFiles = 0;
        scannedDirectories = 0;
        wordsFound = 0;

        File dir = new File(path);

        if (dir.isDirectory()) {
            scannedDirectories++;
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles(file.getPath());
                    scannedDirectories++;
                } else if (file.isFile()) {
                    allFiles.add(file);
                    scannedFiles++;
                }
            }
        } else if (dir.isFile()) {
            allFiles.add(dir);
            scannedFiles++;
        }
    }

    private static void searchFiles(List<File> files, String word) {
        Scanner input;
        System.out.println("Search start.");
        System.out.println("\n-----------------------------");

        int i = 0;
        for (File file : files) {
            try {
                input = new Scanner(file);
                while (input.hasNextLine()) {
                    i++;
                    String line = input.nextLine();
                    if (line.toLowerCase().contains(word.toLowerCase())) {
                        System.out.println("contains");
                        String[] wordAmount = line.split(" ");
                        for (String amount : wordAmount) {
                            if (amount.toLowerCase().contains(word.toLowerCase())) {
                                wordsFound++;
                            }
                        }
                        System.out.println("\n" + file.getPath() + ":      " + line);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                e.printStackTrace();
            }
        }
        System.out.println(i);

        System.out.println("\n-----------------------------");

        if(wordsFound > 0) {
            System.out.println("\nSearch end.");
            System.out.println("\nSearched " + scannedDirectories + " directories and " + scannedFiles + " files.");
            System.out.println("\nFound " + wordsFound + " occurences of '" + word.toLowerCase() + "'.");
        } else {
            System.out.println("\n'" + word.toLowerCase() + "' does not exist in any file.");
        }

        System.out.println("\n\n");
    }
}
