import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.nio.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Terminal {

    public static Parser parser = new Parser();
    public static String currentDirectoryPath;
    public static ArrayList<String> fileLog = new ArrayList<>(35);
    public final static  String homeDirectory = "C:\\Users";
    static public String current,File;
    static public int toFile;
    static public boolean flag;


    public static boolean isEmptyDirectory(File Input) throws IOException{
        if (Input.exists()) {
            if (!Input.isDirectory()) {

                // throw exception if given path is a
                // file
                throw new IllegalArgumentException( "The given input is not a directory: "+ Input);
            }
            else {
                // create a new stream and check for the existance of files
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream( Input.toPath())){
                    // False if a file exists
                    return !directoryStream.iterator().hasNext();
                }
            }
        }
        // return true if no file is present
        return true;
    }

    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }





    public static void mkdir(String dirName){
            Path path = Paths.get(dirName);
            if (Files.exists(path)) {
                System.out.println("The file already exists at " + path.toAbsolutePath());
            } else {
        if(dirName.indexOf('\\') == -1) {
                try {
                    Path path1 = Paths.get(currentDirectoryPath + "\\"+ dirName);
                    Files.createDirectories(path1);
                    System.out.println("The file" + dirName + " has been successfully created.");
                    fileLog.add((path1.toAbsolutePath()).toString());
                    currentDirectoryPath = (path1.toAbsolutePath()).toString();
                } catch (IOException e) {
                    System.out.println("The Directory name given is an invalid one, please use only letters, numbers, underscore and hyphen,\n" +
                            "don’t start or end your Directory's name with a space, period, hyphen, or underline,\n" +
                            "keep your Directory's name to a reasonable length and be sure they are under 31 characters.\n" +
                            "Directory's name are case sensitive, please be aware of that. ");
                }
            }
        else{
            try {
                String absolutePath = FileSystems.getDefault().getPath(dirName).normalize().toAbsolutePath().toString();
                Path path1 = Paths.get(absolutePath);
                Files.createDirectories(path1);
                System.out.println("The file " + dirName + " has been successfully created.");
                fileLog.add((path1.toAbsolutePath()).toString());
                currentDirectoryPath = (path1.toAbsolutePath()).toString();
            } catch (IOException e) {
                System.out.println("The Directory name given is an invalid one, please use only letters, numbers, underscore and hyphen,\n" +
                        "don’t start or end your Directory's name with a space, period, hyphen, or underline,\n" +
                        "keep your Directory's name to a reasonable length and be sure they are under 31 characters.\n" +
                        "Directory's name are case sensitive, please be aware of that,\n" +
                        "please make sure all parent Directories in the given path exist.");
            }
        }
        }

    }

    public static void cd(){
        currentDirectoryPath = homeDirectory;
    }

    public static void cd(String input){
        if(input.equals("..")){
            currentDirectoryPath = fileLog.get(fileLog.size()-1);
            fileLog.add(currentDirectoryPath);
        }
        else{
            Path path = Paths.get(input);
            if(Files.exists(path)){
                currentDirectoryPath = (path.toAbsolutePath()).toString();
                fileLog.add(currentDirectoryPath);
            }else{
                System.out.println("Such file does not exist.");
            }
        }
    }

    public static void touch(String input){
        Path path = Paths.get(input);
        try{
            if(!Files.exists(path)){
                Files.createFile(path);
                currentDirectoryPath = (path.toAbsolutePath()).toString();
            }else{
                System.out.println("The given file already exists.");
            }
        }catch (IOException e){
            System.out.println("The File name given is an invalid one, please use only letters, numbers, underscore and hyphen,\n" +
                    "don’t start or end your filename with a space, period, hyphen, or underline,\n" +
                    "keep your filenames to a reasonable length and be sure they are under 31 characters.\n" +
                    "file names are case sensitive, please be aware of that,\n" +
                    "please make sure all parent Directories in the given path exist.");
        }
    }

    public static void rmdir(String input){
        if(input.equals("*")){
            try {
                //filepath.forEach(files.add());
                Path path = Paths.get(currentDirectoryPath);
                Stream<Path> files = Files.list(path);
                Path[] paths = files.toArray(Path[]::new);
                for(int i=0;i<paths.length;++i){
                    if(((paths[i].toFile()).isDirectory() ) ){
                        if(isEmptyDirectory(paths[i].toFile())){
                            Files.delete(paths[i]);
                        }else{
                            System.out.println("Directory is not empty.");
                        }
                    }System.out.println("Path is not a Directory");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            try{
                Path path = Paths.get(input);
                if(Files.exists(path)){
                    if(path.toFile().isDirectory()){
                        if(isEmptyDirectory(path.toFile())){
                            currentDirectoryPath = (path.getParent().toAbsolutePath()).toString();
                            Files.delete(path);
                        }else{
                            System.out.println("Directory is not empty");
                        }
                    }else{
                        System.out.println("File is not a directory");
                    }
                }else{
                    System.out.println("File does not exist.");
                }
            }catch (IOException e){
                System.out.println("Invalid Input Please try again.");
            }
        }
    }

    public static void pwd() { System.out.println(currentDirectoryPath); }

    public static void cat(String F1) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(F1));
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        }

        String line;
        try {
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("A null pointer exception was thrown");
            e.printStackTrace();
        }

    }

    public static void cat(String F1, String F2) throws IOException {
        File f1 = new File(F1);
        File f2 = new File(F2);
        FileWriter writer = new FileWriter(f2,true);
        BufferedWriter BWriter = new BufferedWriter(writer);
        Scanner sc1;



        try {
            sc1 = new Scanner(f1);
        } catch (FileNotFoundException var9) {
            var9.printStackTrace();
            return;
        }

        Scanner sc2;

        try {
            sc2 = new Scanner(f2);
        } catch (FileNotFoundException var8) {
            var8.printStackTrace();
            return;
        }

        String st;
        while(sc1.hasNextLine()) {
            st = sc1.nextLine();
            BWriter.write(st);
        }

        BWriter.close();
        writer.close();

        while(sc2.hasNextLine()) {
            st = sc2.nextLine();
            System.out.println(st);
        }

    }

    public static void cp(String F1, String F2) throws IOException {
        Path path1 = Paths.get(F1);
        Path path2 = Paths.get(F2);

        File f1 = path1.toFile();
        File f2 = path2.toFile();
        Files.copy(f1.toPath(),f2.toPath(),StandardCopyOption.REPLACE_EXISTING);




    }

    public static void cp_r(String SP, String DP) {
        Path SD = Paths.get(SP);
        Path DD = Paths.get(DP);

        try {
            Files.walk(SD).forEach((sourcePath) -> {
                Path targetPath = DD.resolve(SD.relativize(sourcePath));
                System.out.printf("copying %s to %s%n", sourcePath, targetPath);

                try {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException var5) {
                    var5.printStackTrace();
                }

            });
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    static void print(Vector <String> Lines) {

        if (!flag) {
            for (String line : Lines) {
                System.out.println(line);
            }
        } else {//do the "more" operations
            for (int i = 0; i < Lines.size(); i++) {
                if (i < 5) {
                    System.out.println(Lines.elementAt(i));
                } else {
                    try {
                        System.in.read();
                        System.in.skip(System.in.available());
                        System.out.print(Lines.elementAt(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        flag = false;
    }

    static public void printToFile(String text) throws IOException {
        try {
            text+= System.lineSeparator();
            if (toFile == 1) {
                Path filePath = Paths.get(File);
                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                }
                Files.writeString(filePath, text, StandardOpenOption.CREATE,StandardOpenOption.APPEND);

            } else if (toFile == 2) {
                var br = new PrintWriter(new File(File));
                br.print(text);
                br.close();
            }

            toFile = 0;

        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }

    }

    static public void echo(String TheArg) {
        System.out.println(TheArg);
    }
    static void ls() {
        File file1 = new File((currentDirectoryPath));
        String[] arr = file1.list();
        for(int i = 0; i< Objects.requireNonNull(arr).length; ++i){
            System.out.println(arr[i]);
        }
    }
    static void ls_r () {
        File file1 = new File((currentDirectoryPath));
        String[] arr = file1.list();
        for(int i = arr.length-1;  i>0; --i){
            System.out.println(arr[i]);
        }
    }

    static public void rm(String a) {
        try{
            Path path = Paths.get(a);
            Files.delete(path);
            System.out.println("Deletion is done successfully");
        } catch (IOException e){
            System.out.println("An error occurred.");
        }
    }

    public static void main(String[] args){
        Terminal.currentDirectoryPath = Terminal.homeDirectory;
        System.out.println("Please enter the cp -r and ls -r as cp_r and ls_r respectively.");
        String command, cmdLine;
        Scanner scan = new Scanner(System.in);
        ArrayList<String> arguments = new ArrayList<>();

        while(true){
            try{
            cmdLine = scan.nextLine();
            Terminal.parser.parse(cmdLine);
            command = Terminal.parser.getCommandName();
            arguments = Terminal.parser.getArguments();


                if(command.equals("echo")){
                    if(arguments == null || arguments.size()!=1){
                            System.out.println("This command takes one argument.");
                        }else{
                            Terminal.echo(arguments.get(0));
                        }}

                else if(command.equals("pwd")){
                        Terminal.pwd();
                        }

                else if(command.equals("cd")){
                        if(arguments==null || arguments.size() == 0){
                            Terminal.cd();
                        }
                        else if(arguments.size()==1 ){
                            Path path = Paths.get(arguments.get(0));
                            if(path.toFile().isDirectory() || arguments.get(0).equals("..")){
                                Terminal.cd(arguments.get(0));
                            }else{
                                System.out.println("Invalid Argument.");
                            }
                        }
                    }

                else if(command.equals("ls")){
                        Terminal.ls();
                    }

                else if(command.equals("ls_r")){
                        Terminal.ls_r();
                    }

                else if(command.equals("mkdir")){
                        if(arguments == null){
                            System.out.println("This command takes only one argument");
                        }else {
                            Path path = Paths.get(arguments.get(0));
                            if (arguments.size() != 1) {
                                System.out.println("This command takes only one argument");
                            } else if (path.toFile().isDirectory() || isValidPath(arguments.get(0))) {
                                Terminal.mkdir(arguments.get(0));
                            }else{
                                System.out.println("Invalid Argument.");
                            }
                        }
                    }

                else if(command.equals("rmdir")){
                        if(arguments == null){
                            System.out.println("This command takes only one argument");
                        }else {
                            Path path = Paths.get(arguments.get(0));
                            if (arguments.size() != 1) {
                                System.out.println("This command takes only one argument");
                            } else if(arguments.get(0).equals("*")){
                                Terminal.rmdir(arguments.get(0));
                            } else if(isValidPath(arguments.get(0)) && isEmptyDirectory(path.toFile())){
                                Terminal.rmdir(arguments.get(0));
                            }else{
                                System.out.println("Invalid Argument.");
                            }
                        }
                    }

                else if(command.equals("touch")){
                        if(arguments == null){
                            System.out.println("This command takes one argument");
                        }else{
                            if (arguments.size() != 1) {
                                System.out.println("This command takes one argument");
                            }
                            else if(isValidPath(arguments.get(0))){
                                Terminal.touch(arguments.get(0));
                            }else{
                                System.out.println("Invalid Argument.");
                            }
                        }
                    }

                else if(command.equals("cp")){
                        if(arguments == null){
                            System.out.println("This command takes two argument");
                        }else{
                            if (arguments.size() != 2) {
                                System.out.println("This command takes two argument");
                            }
                            if(isValidPath(arguments.get(0)) && isValidPath(arguments.get(1))){
                                Path path0 = Paths.get(arguments.get(0));
                                Path path1 = Paths.get(arguments.get(1));
                                if(Files.exists(path0) && Files.exists(path1)){
                                    Terminal.cp(arguments.get(0),arguments.get(1));
                                }
                                else{
                                    System.out.println("One or both of the files does not exist. ");
                                }
                            }else{
                                System.out.println("invalid Argument.");
                            }
                        }
                    }

                else if(command.equals("cp_r")){
                        if(arguments == null){
                            System.out.println("This command takes two argument");
                        }else{
                            if (arguments.size() != 2) {
                                System.out.println("This command takes two argument");
                            }
                            if(isValidPath(arguments.get(0)) && isValidPath(arguments.get(1))){
                                Path path0 = Paths.get(arguments.get(0));
                                Path path1 = Paths.get(arguments.get(1));
                                if(Files.exists(path0) && Files.exists(path1)){
                                    if(path0.toFile().isDirectory() && path1.toFile().isDirectory()){
                                        Terminal.cp_r(arguments.get(0),arguments.get(1));
                                    }
                                }
                                else{
                                    System.out.println("One or both of the directories does not exist. ");
                                }
                            }else{
                                System.out.println("invalid Argument.");
                            }
                        }
                    }

                else if(command.equals("rm")){
                        if(arguments == null){
                            System.out.println("This command takes one argument");
                        }else{
                            if (arguments.size() != 1) {
                                System.out.println("This command takes one argument");
                            }
                            else if(isValidPath(arguments.get(0))){
                                Path path = Paths.get(arguments.get(0));
                                if(Files.exists(path) && !path.toFile().isDirectory()){
                                    Terminal.rm(arguments.get(0));
                                }
                            }else{
                                System.out.println("Invalid Argument.");
                            }
                        }
                    }



                else if(command.equals("cat")){
                        if(arguments == null){
                            System.out.println("This command takes at least one argument");
                        }

                        else if(arguments.size() == 1){
                            if(isValidPath(arguments.get(0))){
                                Path path = Paths.get(arguments.get(0));
                                if(Files.exists(path)){
                                    if(!path.toFile().isDirectory()){
                                        Terminal.cat(arguments.get(0));
                                    }
                                }
                            }
                        }

                        else if(arguments.size() == 2){
                            if(isValidPath(arguments.get(0)) && isValidPath(arguments.get(1))){
                                Path path0 = Paths.get(arguments.get(0));
                                Path path1 = Paths.get(arguments.get(1));
                                if(Files.exists(path0) && Files.exists(path1)){
                                    if(!path0.toFile().isDirectory() && !path1.toFile().isDirectory() ){
                                        Terminal.cat(arguments.get(0), arguments.get(1));
                                    }
                                }
                            }
                        }
                    }


                    else if(command.equals("exit")){
                        System.exit(0);
                    }

                    else{
                        System.out.println("The given command is invalid/");
                }





            }catch (Exception e){
                //System.out.println("Invalid Input" + " error type:" + e.getMessage());
                e.printStackTrace();
            }
        }

    }


}
