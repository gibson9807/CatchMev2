import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserNameDAO implements Serializable {
    final static String PATH_TO_FILE = "src/resources/nameUser.txt";

    public void setNameToFile(final String name) {
        if (name != null) {
            try {
                FileWriter fileWriter = new FileWriter(PATH_TO_FILE, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(name);

                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> readNameFromFile() {
        ArrayList<String> userNameList = new ArrayList<>();
        try {
            File myObj = new File(PATH_TO_FILE);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                userNameList.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return userNameList;
    }

    public void clearFile() {
        try {
            File file = new File(PATH_TO_FILE);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeUserNameFromFile(String login) {
        if (login != null) {
            ArrayList<String> userList = readNameFromFile();
            clearFile();
            for (String actual : userList) {
                if (!login.equals(actual)) {
                    setNameToFile(actual);
                }
            }
        }
    }
}
