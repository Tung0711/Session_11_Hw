package Ra.run;

import Ra.imp.Book;

import java.io.*;
import java.util.*;

public class BookRun {
    static List<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            bookList = BookRun.readDataFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("************ MENU ************");
            System.out.println("1. Nhập thông tin sách");
            System.out.println("2. Hiển thị thông tin sách");
            System.out.println("3. Cập nhật thông tin sách theo mã sách");
            System.out.println("4. Xóa sách theo mã sách");
            System.out.println("5. Sắp xếp sách theo giá bán tăng dần");
            System.out.println("6. Thống kê sách theo khoảng giá");
            System.out.println("7. Tìm kiếm sách theo tên tác giả");
            System.out.println("8. Thoát");
            System.out.println("Lựa chọn của bạn: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Vui lòng nhập số nguyên từ 1-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (choice) {
                case 1:
                    inputBookData(scanner);
                    break;
                case 2:
                    displayBook();
                    break;
                case 3:
                    updateBookById(scanner);
                    break;
                case 4:
                    deleteBook(scanner);
                    break;
                case 5:
                    sortBookByExportPrice();
                    break;
                case 6:
                    statisticalBookByPrice(scanner);
                    break;
                case 7:
                    findBookByAuthor(scanner);
                    break;
                case 8:
                    BookRun.writeDataToFile();
                    System.out.println("Chương trình kết thúc, xin tạm biệt!");
                    System.exit(0);
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (true);
    }

    public static void inputBookData(Scanner scanner) {
        System.out.println("Nhập vào số lượng sách cần nhập thông tin:");
        try {
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Book bk = new Book();
                bk.inputData(scanner, bookList);
                bookList.add(bk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayBook() {
        System.out.println("THÔNG TIN CÁC SÁCH:");
        try {
            for (int i = 0; i < bookList.size(); i++) {
                bookList.get(i).displayData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBookById(Scanner scanner) {
        System.out.println("Nhập mã sách cần cập nhật:");
        try {
            int bookIdUpdate = Integer.parseInt(scanner.nextLine());
            boolean find = false;
            for (int i = 1; i < bookList.size(); i++) {
                Book bk = bookList.get(i);
                if (bk.getBookId() == bookIdUpdate) {
                    bk.inputData(scanner, bookList);
                    System.out.println("Thông tin sách đã được cập nhật thành công!");
                    find = true;
                    break;
                }
            }
            if (!find) {
                System.err.println("Mã sách không tồn tại, vui lòng nhập lại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook(Scanner scanner) {
        System.out.println("Nhập vào mã sách cần xóa:");
        try {
            int bookIdDelete = Integer.parseInt(scanner.nextLine());
            boolean isDelete = false;
            for (Book bk : bookList) {
                if (bk.getBookId() == bookIdDelete) {
                    bookList.remove(bk);
                    isDelete = true;
                    System.out.println("Xóa sách thành công!");
                    break;
                }
            }
            if (!isDelete) {
                System.err.println("Mã sách không tồn tại, vui lòng nhập lại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sortBookByExportPrice() {
        bookList.sort(Comparator.comparing(Book::getExportPrice));
        System.out.println("Đã sắp xếp sách thành công!");
    }

    public static void statisticalBookByPrice(Scanner scanner) {
        try {
            System.out.println("Nhập vào khoảng giá đầu: ");
            Float firstNum = Float.parseFloat(scanner.nextLine());
            System.out.println("Nhập vào khoảng giá cuối: ");
            Float lastNum = Float.parseFloat(scanner.nextLine());
            if (firstNum > lastNum) {
                System.err.println("Khoảng giá cuối không được nhỏ hơn khoảng giá đầu, vui lòng nhập lại!");
                return;
            }
            int count = 0;
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                float priceBook = book.getExportPrice();
                if (priceBook > firstNum && priceBook < lastNum) {
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("Không có sách nào trong khoảng giá từ thống kê");
            } else {
                System.out.println("Tổng số sách trong khoảng giá từ " + firstNum + " - " + lastNum + " là: " + count);
            }
        } catch (Exception e) {
            System.err.println("Giá không hợp lệ, vui lòng nhập lại");
            e.printStackTrace();
        }
    }

    public static void findBookByAuthor(Scanner scanner) {
        System.out.println("Nhập vào tên tác giả cần tìm: ");
        try {
            String findAuthor = scanner.nextLine();
            boolean isFindBook = bookList.stream().anyMatch(book -> book.getAuthor().equalsIgnoreCase(findAuthor));
            if (!isFindBook) {
                System.err.println("Sách không tồn tại!");
            } else {
                bookList.stream().filter(book -> book.getAuthor().equalsIgnoreCase(findAuthor)).forEach(System.out::println);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Book> readDataFromFile() {
        List<Book> listBookRead = null;
        File file = new File("book.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listBookRead = (List<Book>) ois.readObject();
        } catch (Exception ex) {
            listBookRead = new ArrayList<>();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listBookRead;
    }

    public static void writeDataToFile() {
        File file = new File("book.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bookList);
            oos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
