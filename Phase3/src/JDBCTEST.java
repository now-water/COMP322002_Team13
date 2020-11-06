import java.sql.*;

public class JDBCTEST {
    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;


        String url = "jdbc:postgresql://localhost/phase2";
        String user = "postgres";
        String password = "kwon0879";

        try {
            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM \"knuMovie\".\"ACCOUNT\"");

            while (rs.next())
                System.out.println(
                        rs.getString(1) + ' ' +
                                rs.getString(2) + ' ' +
                                rs.getString(3) + ' ' +
                                rs.getString(4) + ' ' +
                                rs.getDate(5) + ' ' +
                                rs.getInt(6) + ' ' +
                                rs.getString(7) + ' ' +
                                rs.getString(8) + ' ' +
                                rs.getString(9) + ' ' +
                                rs.getString(10) + ' ' +
                                rs.getBoolean(11));
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        } finally {
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException sqlEX) {
                System.out.println(sqlEX);
            }
        }
    }
}