import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileTest {
    static List<File> allFiles = new ArrayList<>();
    static List<File> directories = new ArrayList<>();
    static int scannedDirectories = 0;
    static int scannedFiles = 0;
    static int wordsFound = 0;


    public static void main(String[] args) {
        String word = "123";
        String path = "C:/Users/Kristoffer/Dropbox/IdeaProjects/tekst";
        try {
            listFiles(path);
            System.out.println(allFiles);
            searchFiles(allFiles, word);
        } catch (Exception e) {
            // fuck off
        }
    }

    public static void listFiles(String path) throws Exception {
        File dir = new File(path);
        File[] files = dir.listFiles();

        for(int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                //directories.add(files[i]);
                listFiles(files[i].getPath());
                scannedDirectories++;
            } else {
                allFiles.add(files[i]);
                scannedFiles++;
            }
        }

    }

    public static void searchFiles(List<File> files, String word) throws Exception {
        //BufferedReader read = new BufferedReader(new FileReader(files.get(i).getPath()));
        for(int i = 0; i < files.size(); i++) {
            Scanner input = new Scanner(files.get(i).getPath());
            while(input.hasNextLine()) {
                System.out.println("x");
                if(input.nextLine().contains(word)){
                    System.out.println(word + " found in file " + files.get(i).getPath());
                    wordsFound++;
                }
            }
        }
        System.out.println("Scanned " + scannedDirectories + " directories and " + scannedFiles + " files.");
        System.out.println(wordsFound + " occurences of '" + word + "'.");
    }

}
