package lk.ijse.dep9.service.custom;

import com.github.javafaker.Faker;
import lk.ijse.dep9.dao.DAOFactory;
import lk.ijse.dep9.dao.DAOTypes;
import lk.ijse.dep9.dao.SuperDAO;
import lk.ijse.dep9.dao.custom.MemberDAO;
import lk.ijse.dep9.dto.MemberDTO;
import lk.ijse.dep9.entity.Member;
import lk.ijse.dep9.service.ServiceFactory;
import lk.ijse.dep9.service.ServiceTypes;
import lk.ijse.dep9.service.exceptions.DuplicateException;
import lk.ijse.dep9.service.exceptions.InUseException;
import lk.ijse.dep9.service.exceptions.NotFoundException;
import lk.ijse.dep9.service.util.Converter;
import lk.ijse.dep9.util.ConnectionUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {



    private MemberService memberService;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");
//        @Cleanup
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(BookServicceTest.class.getResource("/db-script.sql").toURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String dbScriptContent = lines.stream().reduce((previous, current) -> previous + '\n' + current).get();
        Statement stm = connection.createStatement();
        stm.execute(dbScriptContent);
        ConnectionUtil.setConnection(connection);
        memberService= ServiceFactory.getInstance().getService(ServiceTypes.MEMBER);

    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();

    }

   @Test
    void signupMember() {
       Faker faker = new Faker();
       MemberDTO member1 = new MemberDTO(UUID.randomUUID().toString(), faker.name().fullName(), faker.address().fullAddress(), "071-1234567");
       MemberDTO member2 = new MemberDTO(UUID.randomUUID().toString(), faker.name().fullName(), faker.address().fullAddress(), faker.regexify("0\\d{2}-\\d{7}"));
       assertThrows(DuplicateException.class,()->memberService.signupMember(member1));
       memberService.signupMember(member2);
   }

    @Test
    void updateMember() {
        Faker faker = new Faker();
        MemberDAO memberDAO= DAOFactory.getInstance().getDAO(ConnectionUtil.getConnection(), DAOTypes.MEMBER);
        MemberDTO member1 = new Converter().toMemberDTO(memberDAO.findById("7453a9bc-7091-44ae-90f5-e55a63c959c3").get());
        member1.setName(faker.address().firstName());
        member1.setAddress(faker.address().fullAddress());
        member1.setContact(faker.regexify("0\\d{2}-\\d{7}"));

        MemberDTO member2 = new MemberDTO(UUID.randomUUID().toString(),
                faker.name().fullName(), faker.address().fullAddress(), faker.regexify("0\\d{2}-\\d{7}"));

        memberService.updateMember(member1);

        MemberDTO updatedMember = new Converter().toMemberDTO(memberDAO.findById(member1.getId()).get());
        // same type
        assertEquals(member1,updatedMember);


        assertThrows(NotFoundException.class,()->memberService.updateMember(member2));






    }

    @Test
    void removeMemberAccount() {
        String member1Id = UUID.randomUUID().toString();
        String member2Id = "7453a9bc-7091-44ae-90f5-e55a63c959c3";
        String member3Id = "7453a9bc-7091-44ae-90f5-e55a63c95956";

        assertThrows(NotFoundException.class,()->memberService.removeMemberAccount(member1Id));
        assertThrows(InUseException.class,()->memberService.removeMemberAccount(member2Id));
        assertDoesNotThrow(()->memberService.removeMemberAccount(member3Id));

        MemberDAO memberDAO = DAOFactory.getInstance().getDAO(ConnectionUtil.getConnection(), DAOTypes.MEMBER);
        assertFalse(memberDAO.existsById(member3Id));

    }

    @Test
    void getMemberDetails() {
        String member1Id = UUID.randomUUID().toString();
        String member2Id = "7453a9bc-7091-44ae-90f5-e55a63c959c3";
        assertThrows(NotFoundException.class,()->memberService.getMemberDetails(member1Id));
        MemberDTO memberDetails = memberService.getMemberDetails(member2Id);
        assertNotNull(memberDetails.getName());
        assertNotNull(memberDetails.getAddress());
        assertNotNull(memberDetails.getContact());

        System.out.println(memberDetails);


    }

    @Test
    void findMembers() {
        List<MemberDTO> membersList1 = memberService.findMembers("", 2, 1);
        List<MemberDTO> membersList2 = memberService.findMembers("", 2, 2);
        List<MemberDTO> membersList3 = memberService.findMembers("Galle", 10, 1);

        assertEquals(2,membersList1.size());
        assertEquals(1,membersList2.size());
        assertEquals(2,membersList3.size());

        membersList3.forEach(System.out::println);

    }
}