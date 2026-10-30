import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:sqlite:ajp_practical.db";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Establish database connection
            con = DriverManager.getConnection(URL);

            System.out.println("Database connected successfully!");

            // Create employee table if it does not exist
            String createTable = "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "department TEXT, " +
                    "salary REAL)";

            Statement stmt = con.createStatement();
            stmt.execute(createTable);
            stmt.close();

            System.out.println("Employee table is ready!");

            int choice;

            do {
                System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
                System.out.println("1. Insert Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Display Employees");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        insertEmployee();
                        break;

                    case 2:
                        updateEmployee();
                        break;

                    case 3:
                        deleteEmployee();
                        break;

                    case 4:
                        displayEmployees();
                        break;

                    case 5:
                        System.out.println("Program terminated.");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 5);

            con.close();
            sc.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // INSERT operation
    static void insertEmployee() throws SQLException {

        String query =
                "INSERT INTO employee (id, name, department, salary) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, department);
        ps.setDouble(4, salary);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Employee inserted successfully!");
        }

        ps.close();
    }

    // UPDATE operation
    static void updateEmployee() throws SQLException {

        String query =
                "UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        sc.nextLine();

        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();

        ps.setString(1, name);
        ps.setString(2, department);
        ps.setDouble(3, salary);
        ps.setInt(4, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Employee ID not found.");
        }

        ps.close();
    }

    // DELETE operation
    static void deleteEmployee() throws SQLException {

        String query = "DELETE FROM employee WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();

        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Employee ID not found.");
        }

        ps.close();
    }

    // DISPLAY operation
    static void displayEmployees() throws SQLException {

        String query = "SELECT * FROM employee";

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n===== EMPLOYEE RECORDS =====");

        System.out.printf("%-10s %-20s %-20s %-12s%n",
                "ID", "Name", "Department", "Salary");

        while (rs.next()) {
            System.out.printf("%-10d %-20s %-20s %-12.2f%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary"));
        }

        rs.close();
        ps.close();
    }
}