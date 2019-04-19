package hk.hku.cs.shijian.vis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Global {

    public static void deleteAndMkdirs(File dir) {
        if (dir.exists()) {
            deleteDir(dir);
        }
        dir.mkdirs();
    }

    public static void deleteDir(File dir) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            for (File file: dir.listFiles()) {
                deleteDir(file);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }

    public static Scanner getFileScanner(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "GBK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return scanner;
    }

    public static PrintStream getFilePrintStream(String path) {
        File file = new File(path);
        if (file.exists()) {
            Global.deleteDir(file);
        }

        boolean initIOSucc;
        PrintStream output = null;
        try {
            initIOSucc = file.createNewFile();
            output = new PrintStream(file, "GBK");
        } catch (IOException e) {
            e.printStackTrace();
            initIOSucc =false;
        }

        if (!initIOSucc) {
            return null;
        }

        return output;
    }
}
