package Ra.imp;

import Ra.IBook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Book implements IBook, Serializable {
    private int bookId;
    private String bookName;
    private float importPrice;
    private float exportPrice;
    private String author;
    private Date created;
    private String description;

    public Book() {
    }

    public Book(int bookId, String bookName, float importPrice, float exportPrice,
                String author, Date created, String description) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.author = author;
        this.created = created;
        this.description = description;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(float importPrice) {
        this.importPrice = importPrice;
    }

    public float getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(float exportPrice) {
        this.exportPrice = exportPrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void inputData(Scanner scanner, List<Book> bookList) {
        this.bookId = bookIdAuto(bookList);
        this.bookName = inputBookName(scanner, bookList);
        this.importPrice = inputImportPrice(scanner);
        this.exportPrice = inputExportPrice(scanner);
        this.author = inputAuthor(scanner);
        this.created = inputCreated(scanner);
        this.description = inputDescription(scanner);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã sách: %d - Tên sách: %s - Giá nhập: %.1f - Giá xuất: %.1f" +
                        " - Tên tác giả: %s - Ngày tháng: %s - Tiêu đề: %s\n", this.bookId, this.bookName,
                this.importPrice, this.exportPrice, this.author, this.created, this.description);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", importPrice=" + importPrice +
                ", exportPrice=" + exportPrice +
                ", author='" + author + '\'' +
                ", created=" + created +
                ", description='" + description + '\'' +
                '}';
    }

    public int bookIdAuto(List<Book> bookList) {
        //Ma sach tu dong tang
        if (bookList.size() == 0) {
            return 1;
        } else {
            int max = bookList.get(0).getBookId();
            for (int i = 1; i < bookList.size(); i++) {
                if (max < bookList.get(i).getBookId()) {
                    max = bookList.get(i).getBookId();
                }
            }
            return max + 1;
        }
    }

    public String inputBookName(Scanner scanner, List<Book> bookList) {
        //Nhập tên sách
        System.out.println("Nhập vào tên sách: ");
        do {
            try {
                String bookName = scanner.nextLine();
                if (bookName.length() == 4) {
                    boolean isExit = false;
                    for (int i = 0; i < bookList.size(); i++) {
                        if (bookList.get(i).getBookName().equals(bookName)) {
                            isExit = true;
                            break;
                        }
                    }
                    if (isExit) {
                        System.err.println("Tên sách đã tồn tại, vui lòng nhập lại!");
                    } else {
                        return bookName;
                    }
                } else {
                    System.err.println("Tên sách gồm 4 ký tự, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public float inputImportPrice(Scanner scanner) {
        System.out.println("Nhập giá nhập của sách: ");
        do {
            try {
                float importPrice = Float.parseFloat(scanner.nextLine());
                if (importPrice > 0) {
                    return importPrice;
                } else {
                    System.err.println("Giá nhập của sách phải có giá trị lớn hơn 0, vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public float inputExportPrice(Scanner scanner) {
        System.out.println("Nhập giá bán của sách: ");
        do {
            try {
                float exportPrice = Float.parseFloat(scanner.nextLine());
                if (exportPrice > importPrice) {
                    return exportPrice;
                } else {
                    System.err.println("Giá bán của sách phải có giá trị lớn hơn giá nhập, vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public String inputAuthor(Scanner scanner) {
        //Nhập tên sách
        System.out.println("Nhập vào tên tác giả: ");
        do {
            String author = scanner.nextLine();
            if (author.length() >= 6 && author.length() < 50) {
                return author;
            } else {
                System.err.println("Tên tác giả có từ 6-50 ký tự, vui lòng nhập lại!");
            }
        } while (true);
    }

    public Date inputCreated(Scanner scanner) {
        System.out.println("Nhập vào ngày tháng: ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        do {
            try {
                Date created = sdf.parse(scanner.nextLine());
                return created;
            } catch (Exception e) {
                System.err.println("Ngày tháng nhập vào không đúng định dạng, vui lòng nhập lại!");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println("Nhập tiêu đề của sách: ");
        do {
            String description = scanner.nextLine();
            if (description.length() < 500) {
                return description;
            } else {
                System.err.println("Tiêu đề của sách có tối đa 500 ký tự, vui lòng nhập lại!");
            }
        } while (true);
    }
}
