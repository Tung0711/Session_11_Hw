package Ra;

import Ra.imp.Book;

import java.util.List;
import java.util.Scanner;

public interface IBook {
    void inputData(Scanner scanner, List<Book> bookList);

    void displayData();
}
