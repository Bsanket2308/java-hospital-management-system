import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getConnection();
            if (con != null && !con.isClosed()) {
                System.out.println("✅ Database connected successfully!");
                con.close();
            } else {
                System.out.println("❌ Connection is null or closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
