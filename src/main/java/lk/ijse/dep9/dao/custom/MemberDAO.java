package lk.ijse.dep9.dao.custom;

import lk.ijse.dep9.dao.CrudDAO;
import lk.ijse.dep9.entity.Member;

import java.util.List;

public
interface MemberDAO extends CrudDAO<Member,String> {
    default boolean existsByContact(String contact){
        throw new RuntimeException("Not Implement Yet");
    }


    public List<Member> findAllMembers(int size,int page);

    public List<Member> findMembersByQuery(String query);

    public List<Member> findMembersByQuery(String query,int size,int page);
}
