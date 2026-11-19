
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StudentsTest {

    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/tp",
            "postgres",
            "postgres"
        );

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                "TRUNCATE TABLE students RESTART IDENTITY"
            );
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.close();
    }

    @Test
    void testAddAndGetStudent() throws SQLException {
        Students.addStudent(conn, "Sara", 16);

        Optional<Students.Student> student =
            Students.getStudent(conn, 1);

        assertTrue(student.isPresent());
        assertEquals("Sara", student.get().name());
        assertEquals(16.0, student.get().grade(), 0.001);
    }

    @Test
    void testStudentNotFound() throws SQLException {
        Optional<Students.Student> student =
            Students.getStudent(conn, 999);

        assertTrue(student.isEmpty());
    }

    @Test
    void testAverageGrade() throws SQLException {
        Students.addStudent(conn, "Amel", 10);
        Students.addStudent(conn, "Karim", 14);

        assertEquals(12.0, Students.averageGrade(conn), 0.001);
    }
}
