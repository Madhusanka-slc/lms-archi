package lk.ijse.dep9.service.custom.impl;

import lk.ijse.dep9.dao.DAOFactory;
import lk.ijse.dep9.dao.DAOTypes;
import lk.ijse.dep9.dao.custom.BookDAO;
import lk.ijse.dep9.dto.BookDTO;
import lk.ijse.dep9.entity.Book;
import lk.ijse.dep9.service.custom.BookServicce;
import lk.ijse.dep9.service.exceptions.DuplicateException;
import lk.ijse.dep9.service.exceptions.NotFoundException;
import lk.ijse.dep9.service.util.Converter;
import lk.ijse.dep9.util.ConnectionUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookServicce {

    private BookDAO bookDAO;//dependancy declare

    private final Converter converter;

    public BookServiceImpl() {
        // dependency injection
        this.bookDAO = DAOFactory.getInstance().getDAO(ConnectionUtil.getConnection(), DAOTypes.BOOK);
        converter=new Converter();
    }


    @Override
    public void addNewBook(BookDTO dto) throws DuplicateException {
        if (bookDAO.existsById(dto.getIsbn())){
            throw new DuplicateException("Book with this isbn is already exists");
        }

//        Book bookEntity = new Book(dto.getIsbn(), dto.getTitle(), dto.getAuthor(), dto.getCopies());

//        bookDAO.save(bookEntity);
        bookDAO.save(converter.toBook(dto));

    }

    @Override
    public void updateBookDetails(BookDTO dto) throws NotFoundException {
        if (!bookDAO.existsById(dto.getIsbn())) {
            throw new NotFoundException("Book doesn't exist");
        }
/*        Book bookEntity = new Book(dto.getIsbn(), dto.getTitle(), dto.getAuthor(),dto.getCopies());
        bookDAO.update(bookEntity);*/

        bookDAO.update(converter.toBook(dto));

    }

    @Override
    public BookDTO getBookDetails(String isbn) throws NotFoundException {
//        if (!bookDAO.existsById(isbn)) throw new NotFoundException("Book doesn't exist");
    /*    return bookDAO.findById(isbn).
                map(e -> new BookDTO(e.getIsbn(),e.getTitle(),e.getAuthor(),e.getCopies()))
                .orElseThrow(()-> new NotFoundException("Book doesn't exists"));*/

            return bookDAO.findById(isbn).
                map(converter::toBookDTO)
                .orElseThrow(()-> new NotFoundException("Book doesn't exists"));


    }

    @Override
    public List<BookDTO> findBooks(String query, int size, int page) {
        List<Book> bookEntityList=bookDAO.findBooksByQuery(query, size, page);
    /*    return bookEntityList.stream().
                map(e->new BookDTO(e.getIsbn(),e.getTitle(),e.getAuthor(),e.getCopies())).collect(Collectors.toList());*/

        return bookEntityList.stream().map(converter::toBookDTO).collect(Collectors.toList());


    }
}
