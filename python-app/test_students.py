
import pytest
import psycopg2

from students import add_student, get_student, average_grade


@pytest.fixture
def conn():
    connection = psycopg2.connect(
        host="localhost",
        database="tp",
        user="postgres",
        password="postgres",
        port=5432
    )

    with connection.cursor() as cur:
        cur.execute("TRUNCATE TABLE students RESTART IDENTITY")

    connection.commit()

    yield connection

    connection.close()


def test_add_and_get_student(conn):
    add_student(conn, "Sara", 16)

    student = get_student(conn, 1)

    assert student is not None
    assert student[1] == "Sara"
    assert float(student[2]) == 16.0


def test_student_not_found(conn):
    student = get_student(conn, 999)

    assert student is None


def test_moyenne(conn):
    add_student(conn, "Amel", 10)
    add_student(conn, "Karim", 14)

    assert average_grade(conn) == 12.0
