
import psycopg2


def add_student(conn, name, grade):
    with conn.cursor() as cur:
        cur.execute(
            "INSERT INTO students (name, grade) VALUES (%s, %s)",
            (name, grade)
        )


def get_student(conn, student_id):
    with conn.cursor() as cur:
        cur.execute(
            "SELECT id, name, grade FROM students WHERE id = %s",
            (student_id,)
        )
        return cur.fetchone()


def average_grade(conn):
    raise NotImplementedError("Fonction non implementee")
