package lk.ijse.dep9.dao;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.dep9.dto.MemberDTO;
import lk.ijse.dep9.exception.ResponseStatusException;
import lk.ijse.dep9.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class DataAccess {
    public static boolean deleteMember(String memberId){
        try  {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE FROM member WHERE id=?");
            stm.setString(1, memberId);
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                return false;
            } else {
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateMember(MemberDTO dto){
        Connection connection = ConnectionUtil.getConnection();
        return true;
    }

    public static boolean createMember(MemberDTO member){

        try  {
            Connection connection = ConnectionUtil.getConnection();
            member.setId(UUID.randomUUID().toString());
                    PreparedStatement stm = connection.
                            prepareStatement("INSERT INTO member (id, name, address, contact) VALUES (?, ?, ?, ?)");
                    stm.setString(1, member.getId());
                    stm.setString(2, member.getName());
                    stm.setString(3, member.getAddress());
                    stm.setString(4, member.getContact());

                    int affectedRows = stm.executeUpdate();
                    if (affectedRows == 1) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }

    }
}
