import java.nio.file.Path;
import java.util.ArrayList;
import java.nio.file.Files;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.util.List;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import static java.io.File.separator;

import java.io.IOException;

public class Manager {
    private static Path projectPath;
    private static ProcessBuilder pb;

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Error: Bad argument");
            System.out.println("Example: java Manager <run/build/dist/new>");
            return;
        }

        projectPath = Path.of("").toAbsolutePath();

        switch (args[0].toLowerCase()) {
            case "run" -> run();
            case "build" -> build();
            case "dist" -> dist();
            case "new" -> projectVerify();
            default -> {
                System.out.println(String.format("Error: The task '%s' not exist", args[0]));
                System.out.println("Error: Bad argument");
                System.out.println("Example: java Manager <run/build/dist/new>");
                return;
            }
        }

    }

    private static void projectVerify() throws IOException {

        if (projectPath == null) return;

        Path rlPath = Path.of(projectPath + separator + "dist");
        if (!Files.exists(rlPath)) Files.createDirectory(rlPath);

        rlPath = Path.of(projectPath + separator + "build");
        if (!Files.exists(rlPath)) Files.createDirectory(rlPath);

        rlPath = Path.of(projectPath + separator + "src");
        if (!Files.exists(rlPath)) Files.createDirectory(rlPath);

        rlPath = Path.of(projectPath + separator + "MANIFEST.MF");
        if (!Files.exists(rlPath)) {
            Files.createFile(rlPath);

            String defaultMF = 
                """
                Manifest-Version: 1.0
                Main-Class: MainClass
                """;
            Files.writeString(rlPath, defaultMF);
        }
    }

    private static void run() throws IOException {
        projectVerify();

        String mainClass = obtainMainClass();
        String[] comandList = {"java","--class-path","build",mainClass};

        System.out.println("Runing the project...\n");
        boolean result = executeComand(comandList, true);

        System.out.println(result ? "\nRun succesfully" : "\nRun error");
    }

    private static void build() throws IOException {
        projectVerify();

        Path buildPath = Path.of(projectPath+separator+"build");
        recursiveDelete(buildPath);

        List<String> projectFiles = new ArrayList<String>();
        obtainFilesRecursive(projectFiles, Path.of(projectPath+separator+"src"),".java");
        String[] comandList = new String[projectFiles.size()+3];

        comandList[0] = "javac";
        comandList[1] = "-d";
        comandList[2] = "build";

        for (int i = 3; i< comandList.length; i++) {
            comandList[i] = projectFiles.get(i-3);
        }

        System.out.println("Building the project...");
        boolean result = executeComand(comandList, false);
        System.out.println(result ? "Build succesfully" : "Build error");
    }

    private static void dist() throws IOException {
        projectVerify();

        //TO-DO
        //Implementar las distribuciones tanto en jar como en zip(Si puedo de forma nativa)

        System.out.println("Dist the project");
    }

    private static String obtainMainClass() throws IOException {
        Path rlPath = Path.of(projectPath + separator + "MANIFEST.MF");
        String text = Files.readString(rlPath);
        String[] lines = text.split("\n");
        text = lines[1].split(":")[1].trim();
        return text;
    }

    private static void recursiveDelete(Path directory) throws IOException {
        Path dir;
        Object[] files = Files.list(directory).toArray();
        if (files.length == 0) return;
        for (Object o : files) {
            dir = (Path) o;
            if (Files.isDirectory(dir)) {
                recursiveDelete(dir);
                Files.delete(dir);
                continue;
            } 

            Files.delete(dir);
        }

    }

    private static void obtainFilesRecursive(List<String> fileList, Path projectPath, String format) throws IOException {
        Files.list(projectPath).forEach(filePath -> {
            try {
                if (Files.isDirectory(filePath)) {
                    obtainFilesRecursive(fileList, filePath, format);
                } else if (filePath.toString().endsWith(format)) {
                    fileList.add(filePath.toString());
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            
        });
    }

    private static boolean executeComand(String[] command, boolean vervose) {
        
        if (vervose) {
            System.out.print("Command: ");
            for (String cm : command) {
                System.out.print(cm);
                System.out.print(" ");
            }
            System.out.println();
        }        
        
        pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();

            if (vervose) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // Imprime cada línea de la salida
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) return false;
            return true;
        } catch (InterruptedException |IOException e) {
            return false;
        }
    }
}
