package prog01_date;

import java.io.*;
import java.util.Scanner;

public class ReaderLocalFile {
    private ReaderLocalFile() {
    }

    public static boolean printFromFile(String filename) {
        try
        {
            FileReader fr= new FileReader(filename);
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
            System.out.println(filename);
            fr.close();
            return true;
        }
        catch(IOException ex){

            System.out.println("Ошибка при открытии файла " + filename);
        }
        return false;
    }

    public static String getFilenameWithAbsolutePatch(String localPatch, String fileName) {
        String path = new File(".").getAbsolutePath();
        return path + localPatch + fileName;
    }


    //файл существует?
    public static boolean isFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

}
