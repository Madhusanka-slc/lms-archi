package lk.ijse.dep9.dao.custom.impl;


import lk.ijse.dep9.dao.custom.ReturnDAO;
import lk.ijse.dep9.entity.Return;
import lk.ijse.dep9.entity.ReturnPK;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReturnDAOImpl implements ReturnDAO {

    private Connection connection;

    public ReturnDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long count(){
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(isbn) FROM `return`");
            ResultSet rst = stm.executeQuery();
            rst.next();
            return rst.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(ReturnPK returnPK){
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM `return` WHERE isbn=? AND issue_id=?");
            stm.setString(1, returnPK.getIsbn());
            stm.setInt(2,returnPK.getIssueId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public boolean existsById(ReturnPK returnPK){
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `return` WHERE isbn=? AND issue_id=?");
            stm.setString(1, returnPK.getIsbn());
            stm.setInt(2,returnPK.getIssueId());
            return stm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Return> findAll(){
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

    @Override
    public Optional<Return> findById(ReturnPK returnPK){
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

    @Override
    public Return save(Return returns){
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

    @Override
    public Return update(Return returnItem) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE `return` SET date = ? WHERE issue_id=? AND isbn=?");
            stm.setDate(1, returnItem.getDate());
            stm.setInt(2, returnItem.getReturnPK().getIssueId());
            stm.setString(3, returnItem.getReturnPK().getIsbn());
            if (stm.executeUpdate() == 1) {
                return returnItem;
            } else {
                throw new SQLException("Failed to update the issue note");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
