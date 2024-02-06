import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// TODO: Start listening and parsing command line -System.in-.
// There are 4 commands:
// 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
// 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
// 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
// 4) print : Print the whole tree, call CengBookRunner.printTree().

// Commands (quit, add, search, print) are case-insensitive.
public class CengTreeParser {
    public static ArrayList<CengBook> parseBooksFromFile(String filename) {
        ArrayList<CengBook> books = new ArrayList<>();

        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] stringArray = line.split("\\|");

                Integer bookID = Integer.parseInt(stringArray[0]);
                String bookTitle = stringArray[1];
                String author = stringArray[2];
                String genre = stringArray[3];

                books.add(new CengBook(bookID, bookTitle, author, genre));

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void startParsingCommandLine() throws IOException {
        try (Scanner scan = new Scanner(System.in)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] stringArray = line.split("\\|");

                String command = stringArray[0];

                if (command.equals("quit")) {
                    return;
                }
                else if (command.equals("add")) {
                    Integer bookID = Integer.parseInt(stringArray[1]);
                    String bookTitle = stringArray[2];
                    String author = stringArray[3];
                    String genre = stringArray[4];

                    CengBook cengVideo = new CengBook(bookID, bookTitle, author, genre);
                    CengBookRunner.addBook(cengVideo);
                }
                else if (command.equals("search")) {
                    Integer bookID = Integer.parseInt(stringArray[1]);
                    CengBookRunner.searchBook(bookID);
                }
                else if (command.equals("print")) {
                    CengBookRunner.printTree();
                }
            }
        }
    }
}


