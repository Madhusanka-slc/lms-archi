package lk.ijse.dep9.dao.custom.impl;

import lk.ijse.dep9.dao.custom.QueryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class QueryDAOImpl implements QueryDAO {
    private Connection connection;

    public QueryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Integer> getAvailableBookCopies(String isbn) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT b.copies-COUNT(ii.isbn)+COUNT(r.isbn) as `available_copies` FROM issue_item ii\n" +
                    "LEFT OUTER JOIN  `return` r ON ii.issue_id = r.issue_id and ii.isbn = r.isbn\n" +
                    "RIGHT OUTER JOIN book b on ii.isbn = b.isbn WHERE b.isbn=?" +
                    "GROUP BY b.isbn;");
            stm.setString(1,isbn);
            ResultSet rst = stm.executeQuery();
            if (!rst.next()) {
                return Optional.empty();
            }
            return Optional.of(rst.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isAlreadyIssued(String isbn, String memberId) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT  m.id,m.name,ii.isbn FROM member m\n" +
                    "                                     INNER JOIN issue_note isn ON m.id = isn.member_id\n" +
                    "                                     INNER JOIN issue_item ii ON isn.id = ii.issue_id\n" +
                    "                                     LEFT OUTER JOIN `return` r ON ii.issue_id = r.issue_id and ii.isbn = r.isbn\n" +
                    "WHERE r.date IS NULL AND m.id =?  AND ii.isbn=?  ;");
            stm.setString(1,memberId);
            stm.setString(2,isbn);
            return stm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Integer> availableBookLimit(String memberId) {
        try {
            PreparedStatement stm = connection.prepareStatement(
                    "SELECT member.id, name, 3 - COUNT(`in`.id) as `available` FROM member " +
                            "LEFT OUTER JOIN issue_note `in` ON member.id = `in`.member_id " +
                            "LEFT OUTER JOIN issue_item ii ON `in`.id = ii.issue_id " +
                            "LEFT OUTER JOIN `return` r ON ii.issue_id = r.issue_id and ii.isbn = r.isbn " +
                            "WHERE r.date IS NULL AND member.id = ? GROUP BY member.id"
            );

            stm.setString(1,memberId);
            ResultSet rst = stm.executeQuery();
            if (!rst.next()) {
                return Optional.empty();

            }
            return Optional.of(rst.getInt("available"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValidIssueItem(String memberId, int issueId, String isbn) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM issue_item " +
                    "INNER JOIN issue_note `in` on issue_item.issue_id = `in`.id " +
                    "WHERE member_id = ? AND issue_id = ? and isbn =?");

            stm.setString(1,memberId);
            stm.setInt(2,issueId);
            stm.setString(3,isbn);
            return stm.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
