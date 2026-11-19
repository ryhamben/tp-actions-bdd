
const { Pool } = require("pg");

const pool = new Pool({
    host: "localhost",
    port: 5432,
    database: "tp",
    user: "postgres",
    password: "postgres"
});

async function addStudent(name, grade) {
    await pool.query(
        "INSERT INTO students (name, grade) VALUES ($1, $2)",
        [name, grade]
    );
}

async function getStudent(id) {
    const result = await pool.query(
        "SELECT id, name, grade FROM students WHERE id = $1",
        [id]
    );

    return result.rows[0] || null;
}

async function averageGrade() {
    const result = await pool.query(
        "SELECT AVG(grade) AS average FROM students"
    );

    return Number(result.rows[0].average);
}

module.exports = {
    addStudent,
    getStudent,
    averageGrade,
    pool
};
