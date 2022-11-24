package lk.ijse.dep9.service.util;

import jdk.dynalink.linker.LinkerServices;
import lk.ijse.dep9.dto.BookDTO;
import lk.ijse.dep9.dto.IssueNoteDTO;
import lk.ijse.dep9.dto.MemberDTO;
import lk.ijse.dep9.entity.Book;
import lk.ijse.dep9.entity.IssueItem;
import lk.ijse.dep9.entity.IssueNote;
import lk.ijse.dep9.entity.Member;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;

import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    private ModelMapper mapper;

    public Converter() {
        this.mapper = new ModelMapper();
        mapper.typeMap(LocalDate.class, Date.class).setConverter(mc->Date.valueOf(mc.getSource()));
//        mapper.typeMap(Date.class,LocalDate.class).setConverter(mc-> mc.getSource().toLocalDate());
    }

    public BookDTO toBookDTO(Book bookEntity){
//        return new BookDTO(bookEntity.getIsbn(), bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getCopies());
        return mapper.map(bookEntity,BookDTO.class);
    }

    public Book toBook(BookDTO bookDTO){
//        new Book(bookDTO.getIsbn(), bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getCopies());
        return mapper.map(bookDTO,Book.class);
    }

    public MemberDTO toMemberDTO(Member memberEntity){
        return mapper.map(memberEntity,MemberDTO.class);
    }

    public Member toMember(MemberDTO memberDTO){
        return mapper.map(memberDTO,Member.class);
    }



    public IssueNote toIssueNote(IssueNoteDTO issueNoteDTO){
        return mapper.map(issueNoteDTO,IssueNote.class);
    }

    public List<IssueItem> toIssueItemList(IssueNoteDTO issueNoteDTO) {
        Type typeToken=new TypeToken<List<IssueItem>>() {}.getType();
        mapper.typeMap(IssueNoteDTO.class,List.class).setConverter(mc->{
            IssueNoteDTO source = mc.getSource();
            return source.getBooks().stream().map(isbn->
                    new IssueItem(source.getId(),isbn)).collect(Collectors.toList());
        });

        return mapper.map(issueNoteDTO,typeToken);

    }
}
