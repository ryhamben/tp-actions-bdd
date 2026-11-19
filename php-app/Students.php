
<?php

class Students
{
    public static function addStudent(
        PDO $conn,
        string $name,
        float $grade
    ): void {
        $sql = "INSERT INTO students (name, grade) VALUES (?, ?)";

        $stmt = $conn->prepare($sql);
        $stmt->execute([$name, $grade]);
    }

    public static function getStudent(
        PDO $conn,
        int $id
    ): ?array {
        $sql = "SELECT id, name, grade FROM students WHERE id = ?";

        $stmt = $conn->prepare($sql);
        $stmt->execute([$id]);

        $student = $stmt->fetch(PDO::FETCH_ASSOC);

        return $student ?: null;
    }

    public static function averageGrade(PDO $conn): float
    {
        $stmt = $conn->query("SELECT AVG(grade) FROM students");

        return (float) $stmt->fetchColumn();
    }
}
