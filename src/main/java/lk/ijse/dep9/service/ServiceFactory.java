package lk.ijse.dep9.service;

import lk.ijse.dep9.service.custom.impl.BookServiceImpl;
import lk.ijse.dep9.service.custom.impl.IssueServiceImpl;
import lk.ijse.dep9.service.custom.impl.MemberServiceImpl;
import lk.ijse.dep9.service.custom.impl.ReturnServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
//    private static ServiceFactory serviceFactory=new ServiceFactory(); Eager initializer

    private ServiceFactory() {
        //make db 20
    }

    public static ServiceFactory getInstance(){
        return (serviceFactory==null) ? (serviceFactory=new ServiceFactory()):serviceFactory;//lazy initializer
//        return serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceType){
        final SuperService service;
        switch (serviceType){
            case BOOK:
//                return (T) new BookServiceImpl();
                service=new BookServiceImpl();
                break;
            case ISSUE:
                service=new IssueServiceImpl();
                break;
//                return (T) new IssueServiceImpl();
            case MEMBER:
                service=new MemberServiceImpl();
                break;
//                return (T) new MemberServiceImpl();
            case RETURN:
                service=new ReturnServiceImpl();
                break;
//                return (T) new ReturnServiceImpl();
            default:
                return null;
        }

        return (T) service;
    }
}
