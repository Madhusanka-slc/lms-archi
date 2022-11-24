package lk.ijse.dep9.dao;

import com.github.javafaker.Faker;
import lk.ijse.dep9.dao.exception.ConstraintViolationException;
import lk.ijse.dep9.dao.custom.impl.MemberDAOImpl;
import lk.ijse.dep9.entity.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;//static members import

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberDAOTest {
    private MemberDAOImpl memberDAO;
    private Connection connection;

    @AfterAll
    static void afterAll() {
        System.out.println("After all test case");

    }

    @BeforeAll
    static void beforeAll() throws ClassNotFoundException, SQLException, URISyntaxException, IOException {

    }

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException, URISyntaxException, IOException {
        connection = DriverManager.getConnection("jdbc:h2:mem:");

        Class.forName("com.mysql.cj.jdbc.Driver");

        List<String> lines = Files.readAllLines(Paths.get(MemberDAOTest.class.getResource("/db-script.sql").toURI()));
        String dbScriptContent = lines.stream().reduce((previous, current) -> previous + '\n' + current).get();
        Statement stm = connection.createStatement();
        stm.execute(dbScriptContent);
        this.memberDAO=new MemberDAOImpl(connection);


    }

    @AfterEach
    void tearDown() throws SQLException {
//        System.out.println("After each test case");
        connection.close();

    }

//    @Order(1)
    @Test
    void countMembers() {
//        System.out.println(this);
        long actualMemberCount = memberDAO.count();
        assertEquals(5,actualMemberCount);
    }

//    @Order(1)
    @Test
    void findAllMembers() {
//        System.out.println(this);
        List<Member> members = memberDAO.findAll();
        assertEquals(5,members.size());
        members.forEach(member -> {
            assertNotNull(member);
            assertNotNull(member.getId());
            assertNotNull(member.getName());
            assertNotNull(member.getAddress());
            assertNotNull(member.getContact());
            System.out.println(member);
        });
    }

//    @Order(2)
    @RepeatedTest(5)
    @Test
    void saveMember() {
        Faker faker = new Faker();
        String contact = faker.regexify("0\\d{2}-\\d{7}");
        Member expectedMember = new Member(UUID.randomUUID().toString(),
                faker.name().fullName(),
                faker.address().fullAddress(),
                contact);
        System.out.println(expectedMember);
        long expectedCount=memberDAO.count()+ 1;
        Member actualMember = memberDAO.save(expectedMember);
        assertEquals(expectedMember,actualMember);
        assertEquals(expectedCount,memberDAO.count());
    }



    @ParameterizedTest
    @CsvFileSource(resources = "/member-test-data.csv")
    void deleteMemberByMemberId(String memberId,boolean expectedResult) {
        try {
            memberDAO.deleteById(memberId);
        } catch (ConstraintViolationException e) {
            // Nothing to do
            System.out.println("Failed to delete: "+memberId);
        }
    }
//    @RepeatedTest()
    @ParameterizedTest
  /*  @ValueSource(strings={"05bcea20-fae2-4621-a66e-23bfa942d454",
    "7453a9bc-7091-44ae-90f5-e55a63c95956",
    "7453a9bc-7091-44ae-90f5-e55a63c959c3",
    "7453a9bc-7091-44ae-90f5-e55a63c959cc",
    "c2af11a9-4186-4479-b059-b9f0073b33fa"  })*/

    @CsvFileSource(resources = "/member-test-data.csv")

    void existsMemberByMemberId(String memberId,boolean expectedResult) {
//        String memberId="7453a9bc-7091-44ae-90f5-e55a63c959c3";

        boolean actualValue = memberDAO.existsById(memberId);
//        assertEquals(true,actualValue);
        assertTrue(actualValue==expectedResult);

    }

    @Test
    void testFindAllMembers() {
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/member-test-data.csv")
    void findMemberByMemberId(String memberId,boolean expectedResult) {
        Optional<Member> optMember = memberDAO.findById(memberId);
        assertEquals(optMember.isPresent(),expectedResult);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/member-test-data.csv")
    void updateMember(String memberId,boolean exist) {
        Optional<Member> optMember = memberDAO.findById(memberId);
        Faker faker = new Faker();
        optMember.ifPresent(member -> {
            member.setName(faker.name().fullName());
            member.setAddress(faker.address().fullAddress());
            member.setContact(faker.regexify("0\\d{2}-\\d{7}"));
            Member updateMember = memberDAO.update(member);
            assertEquals(member,updateMember);
        });
    }

    @Test
    void findMembersByQuery() {
    }

    @Test
    void testFindMembersByQuery() {
    }
}