
<?php

use PHPUnit\Framework\TestCase;

require_once __DIR__ . '/../Students.php';

class StudentsTest extends TestCase
{
    private PDO $conn;

    protected function setUp(): void
    {
        $this->conn = new PDO(
            "pgsql:host=localhost;port=5432;dbname=tp",
            "postgres",
            "postgres"
        );

        $this->conn->exec(
            "TRUNCATE TABLE students RESTART IDENTITY"
        );
    }

    public function testAddAndGetStudent(): void
    {
        Students::addStudent($this->conn, "Sara", 16);

        $student = Students::getStudent($this->conn, 1);

        $this->assertNotNull($student);
        $this->assertSame("Sara", $student["name"]);
        $this->assertEquals(16, (float) $student["grade"]);
    }

    public function testStudentNotFound(): void
    {
        $student = Students::getStudent($this->conn, 999);

        $this->assertNull($student);
    }

    public function testAverageGrade(): void
    {
        Students::addStudent($this->conn, "Amel", 10);
        Students::addStudent($this->conn, "Karim", 14);

        $this->assertEquals(
            12.0,
            Students::averageGrade($this->conn)
        );
    }
}
