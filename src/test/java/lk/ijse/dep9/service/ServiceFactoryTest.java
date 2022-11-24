package lk.ijse.dep9.service;

import lk.ijse.dep9.service.custom.BookServicce;
import lk.ijse.dep9.service.custom.IssueService;
import lk.ijse.dep9.service.custom.MemberService;
import lk.ijse.dep9.service.custom.ReturnService;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {

    @RepeatedTest(2)
    void getInstance() {
        ServiceFactory i1 = ServiceFactory.getInstance();
        ServiceFactory i2 = ServiceFactory.getInstance();
        ServiceFactory i3 = ServiceFactory.getInstance();
        assertEquals(i1,i2);
        assertEquals(i1,i3);
        assertEquals(i2,i3);

    }

    @Test
    void getService() {
        SuperService bookService = ServiceFactory.getInstance().getService(ServiceTypes.BOOK);
        SuperService memberService = ServiceFactory.getInstance().getService(ServiceTypes.MEMBER);
        SuperService returnService = ServiceFactory.getInstance().getService(ServiceTypes.RETURN);
        SuperService issueService = ServiceFactory.getInstance().getService(ServiceTypes.ISSUE);

        assertInstanceOf(BookServicce.class,bookService);
        assertInstanceOf(MemberService.class,memberService);
        assertInstanceOf(ReturnService.class,returnService);
        assertInstanceOf(IssueService.class,issueService);
    }
}