
import java.sql.*;
import java.util.Optional;

public class Students {

    public record Student(int id, String name, double grade) {}

    public static void addStudent(Connection conn, String name,
                                  double grade) throws SQLException {

        String sql = "INSERT INTO students (name, grade) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, grade);
            stmt.executeUpdate();
        }
    }

    public static Optional<Student> getStudent(Connection conn,
                                                int id) throws SQLException {

        String sql = "SELECT id, name, grade FROM students WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("grade")
                    ));
                }
            }
        }

        return Optional.empty();
    }

    public static double averageGrade(Connection conn)
            throws SQLException {

        String sql = "SELECT AVG(grade) FROM students";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();
            return rs.getDouble(1);
        }
    }
}
