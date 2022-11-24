package lk.ijse.dep9.service.custom;

import com.github.javafaker.Faker;
import lk.ijse.dep9.dao.custom.impl.MemberDAOImpl;
import lk.ijse.dep9.dto.BookDTO;
import lk.ijse.dep9.service.ServiceFactory;
import lk.ijse.dep9.service.ServiceTypes;
import lk.ijse.dep9.service.SuperService;
import lk.ijse.dep9.service.exceptions.DuplicateException;
import lk.ijse.dep9.service.exceptions.NotFoundException;
import lk.ijse.dep9.util.ConnectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServicceTest {
    private Connection connection;
    private  BookServicce bookService;
    @BeforeEach
    void setUp() throws SQLException, URISyntaxException, IOException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");
        List<String> lines = Files.readAllLines(Paths.get(BookServicceTest.class.getResource("/db-script.sql").toURI()));
        String dbScriptContent = lines.stream().reduce((previous, current) -> previous + '\n' + current).get();
        Statement stm = connection.createStatement();
        stm.execute(dbScriptContent);
        ConnectionUtil.setConnection(connection);
        bookService = ServiceFactory.getInstance().getService(ServiceTypes.BOOK);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();

    }

    @Test
    void addNewBook() {
        Faker faker = new Faker();
        BookDTO book1 = new BookDTO("1234-1234", faker.book().title(), faker.book().author(), 2);
        BookDTO book2 = new BookDTO(faker.code().isbn10(), faker.book().title(), faker.book().author(), 2);
        assertThrows(DuplicateException.class,()->bookService.addNewBook(book1));
        assertDoesNotThrow(()->bookService.addNewBook(book2));
    }

    @Test
    void updateBookDetails() {
        Faker faker = new Faker();
        BookDTO book1 = new BookDTO(faker.code().isbn10(), faker.book().title(), faker.book().author(), 2);
        BookDTO book2 = bookService.getBookDetails("1234-1234");
        book2.setAuthor(faker.book().author());
        book2.setTitle(faker.book().title());
        book2.setCopies(faker.number().numberBetween(1,3));

        assertThrows(NotFoundException.class,()->bookService.updateBookDetails(book1));
        bookService.updateBookDetails(book2);
        BookDTO book3 = bookService.getBookDetails("1234-1234");
        assertEquals(book2,book3);


    }

    @Test
    void getBookDetails() {
        Faker faker = new Faker();
        String invalidISBN = faker.code().isbn10();
        String isbn="1234-1234";
        String title="Patterns of Enterprise Application Architecture";
        String author="Marin Fowler";
        int copies=2;

        assertThrows(NotFoundException.class,()->bookService.getBookDetails(invalidISBN));
        BookDTO bookDetails=bookService.getBookDetails(isbn);
        System.out.println(bookDetails);
        assertEquals(isbn,bookDetails.getIsbn());
        assertEquals(title,bookDetails.getTitle());
        assertEquals(author,bookDetails.getAuthor());
        assertEquals(copies,bookDetails.getCopies());
    }

    @Test
    void findBooks() {
        List<BookDTO> bookList1 = bookService.findBooks("", 3, 1);
        List<BookDTO> bookList2 = bookService.findBooks("", 3, 2);
        List<BookDTO> bookList3 = bookService.findBooks("", 3, 3);
        List<BookDTO> bookList4 = bookService.findBooks("Spec", 5, 1);
        assertEquals(3,bookList1.size());
        assertEquals(3,bookList2.size());
        assertEquals(3,bookList3.size());
        assertEquals(3,bookList4.size());
        bookList4.forEach(System.out::println);

    }
}