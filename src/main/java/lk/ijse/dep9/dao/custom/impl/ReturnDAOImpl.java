package lk.ijse.dep9.dao.impl;


import lk.ijse.dep9.entity.Return;
import lk.ijse.dep9.entity.ReturnPK;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnDAOImpl {

    private Connection connection;

    public ReturnDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public long countReturns(){
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(isbn) FROM `return`");
            ResultSet rst = stm.executeQuery();
            rst.next();
            return rst.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReturnByPK(ReturnPK returnPK){
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM `return` WHERE isbn=? AND issue_id=?");
            stm.setString(1, returnPK.getIsbn());
            stm.setInt(2,returnPK.getIssueId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }
    public boolean existsReturnByPK(ReturnPK returnPK){
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `return` WHERE isbn=? AND issue_id=?");
            stm.setString(1, returnPK.getIsbn());
            stm.setInt(2,returnPK.getIssueId());
            return stm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Return> findAllReturns(){
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `return`");
            ResultSet rst = stm.executeQuery();
            List<Return> returnList=new ArrayList<>();
            while (rst.next()) {
                String isbn = rst.getString("isbn");
                int issueId = rst.getInt("issue_id");
                returnList.add(new Return(Date.valueOf(LocalDate.now()),issueId,isbn));
            }

            return returnList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Return> findReturnByPK(ReturnPK returnPK){
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `return` WHERE isbn=? AND issue_id=?");
            stm.setString(1, returnPK.getIsbn());
            stm.setInt(2,returnPK.getIssueId());
            ResultSet rst = stm.executeQuery();


            if (rst.next()) {
                String isbn = rst.getString("isbn");
                int issueId = rst.getInt("issue_id");
                return Optional.of(new Return(Date.valueOf(LocalDate.now()),issueId,isbn));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Return saveReturn(Return returns){
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO `return` (issue_id, isbn) VALUES (?,?)");
            stm.setInt(1,returns.getReturnPK().getIssueId());
            stm.setString(2,returns.getReturnPK().getIsbn());
            if (stm.executeUpdate()==1) {
                return returns;
            }else {
                throw new SQLException("Failed to save the return");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

/*    public IssueItem updateIssueItem(IssueItem issueItem){
        return null;
        primary key cannot update

    }*/

}
