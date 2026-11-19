
const {
    addStudent,
    getStudent,
    averageGrade,
    pool
} = require("./students");

beforeEach(async () => {
    await pool.query("TRUNCATE TABLE students RESTART IDENTITY");
});

afterAll(async () => {
    await pool.end();
});

test("Ajouter et rechercher un etudiant", async () => {
    await addStudent("Sara", 16);

    const student = await getStudent(1);

    expect(student.name).toBe("Sara");
    expect(Number(student.grade)).toBe(16);
});

test("Rechercher un etudiant inexistant", async () => {
    const student = await getStudent(999);

    expect(student).toBeNull();
});

test("Calculer la moyenne", async () => {
    await addStudent("Amel", 10);
    await addStudent("Karim", 14);

    expect(await averageGrade()).toBe(12);
});
