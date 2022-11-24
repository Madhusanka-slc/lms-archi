//package lk.ijse.dep9.dao;
//
//import lk.ijse.dep9.dto.BookDTO;
//import lk.ijse.dep9.dto.MemberDTO;
//import lk.ijse.dep9.util.ConnectionUtil;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.UUID;
//
//public class DataAccessWrongWay {
//    public static boolean deleteMember(String memberId){
//        try  {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stm = connection.prepareStatement("DELETE FROM member WHERE id=?");
//            stm.setString(1, memberId);
//            int affectedRows = stm.executeUpdate();
//            if (affectedRows == 0) {
//                return false;
//            } else {
//               return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static boolean updateMember(MemberDTO member){
//        try  {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stm = connection.
//                    prepareStatement("UPDATE member SET name=?, address=?, contact=? WHERE id=?");
//            stm.setString(1, member.getName());
//            stm.setString(2, member.getAddress());
//            stm.setString(3, member.getContact());
//            stm.setString(4, member.getId());
//
//            return stm.executeUpdate() == 1;
//
//        } catch (SQLException e) {
//           e.printStackTrace();
//           return false;
//        }
//
//    }
//
//    public static boolean createMember(MemberDTO member){
//
//        try  {
//            Connection connection = ConnectionUtil.getConnection();
//            member.setId(UUID.randomUUID().toString());
//                    PreparedStatement stm = connection.
//                            prepareStatement("INSERT INTO member (id, name, address, contact) VALUES (?, ?, ?, ?)");
//                    stm.setString(1, member.getId());
//                    stm.setString(2, member.getName());
//                    stm.setString(3, member.getAddress());
//                    stm.setString(4, member.getContact());
//
//                    int affectedRows = stm.executeUpdate();
//                    if (affectedRows == 1) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//
//    }
//
//
//    public static boolean existBook(String isbn){
//        try {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stmExist = connection.
//                    prepareStatement("SELECT isbn FROM book WHERE isbn = ?");
//            stmExist.setString(1, isbn);
//            return stmExist.executeQuery().next();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
////            throw new SQLException("Invalid Book");
//        }
//
//
//    }
//    public static boolean updateBook(BookDTO book){
//        try  {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stm = connection.
//                    prepareStatement("UPDATE book SET title=?, author=?, copies=? WHERE isbn=?");
//            stm.setString(1, book.getTitle());
//            stm.setString(2, book.getAuthor());
//            stm.setInt(3, book.getCopies());
//            stm.setString(4, book.getIsbn());
//
//            if (stm.executeUpdate() == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//
//
//    }
//    public static boolean saveBook(BookDTO book){
//        try  {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stm = connection.
//                    prepareStatement("INSERT INTO book (isbn, title, author, copies) VALUES (?, ?, ?, ?)");
//            stm.setString(1, book.getIsbn());
//            stm.setString(2, book.getTitle());
//            stm.setString(3, book.getAuthor());
//            stm.setInt(4, book.getCopies());
//
//            int affectedRows = stm.executeUpdate();
//            if (affectedRows == 1) {
//                return true;
//            } else {
//               return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//           return false;
//        }
//
//    }
//
//    public static boolean existMember(String memberId){
//        try {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stmExist = connection.prepareStatement("SELECT id FROM member WHERE id=?");
//            stmExist.setString(1, memberId);
//            return stmExist.executeQuery().next();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static boolean isBookAvailable(String isbn){
//        try {
//            Connection connection = ConnectionUtil.getConnection();
//            PreparedStatement stm = connection.prepareStatement(
//                    "SELECT b.title, ((b.copies - COUNT(issue_item.isbn)) > 0) as `availability` FROM issue_item " +
//                            "    LEFT OUTER JOIN `return` r ON issue_item.issue_id = r.issue_id and issue_item.isbn = r.isbn " +
//                            "    RIGHT OUTER JOIN book b on issue_item.isbn = b.isbn " +
//                            "    WHERE r.date IS NULL and b.isbn = ? GROUP BY b.isbn");
//
//            stm.setString(1,isbn);
//            ResultSet rst = stm.executeQuery();
//            if (rst.next()) {
//                return rst.getBoolean("availability");
//            }else {
//                return false;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
