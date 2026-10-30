import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:sqlite:student.db";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL);

            Statement st = con.createStatement();

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Student (" +
                "roll_no INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "marks REAL)"
            );

            int choice;

            do {
                System.out.println("\n--- STUDENT DATABASE ---");
                System.out.println("1. Insert");
                System.out.println("2. Update");
                System.out.println("3. Delete");
                System.out.println("4. Display");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Enter Roll Number: ");
                        int roll = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Marks: ");
                        double marks = sc.nextDouble();

                        PreparedStatement ps1 = con.prepareStatement(
                            "INSERT INTO Student VALUES (?, ?, ?)"
                        );

                        ps1.setInt(1, roll);
                        ps1.setString(2, name);
                        ps1.setDouble(3, marks);

                        ps1.executeUpdate();

                        System.out.println("Record inserted successfully!");
                        ps1.close();
                        break;

                    case 2:
                        System.out.print("Enter Roll Number to update: ");
                        roll = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Name: ");
                        name = sc.nextLine();

                        System.out.print("Enter New Marks: ");
                        marks = sc.nextDouble();

                        PreparedStatement ps2 = con.prepareStatement(
                            "UPDATE Student SET name = ?, marks = ? WHERE roll_no = ?"
                        );

                        ps2.setString(1, name);
                        ps2.setDouble(2, marks);
                        ps2.setInt(3, roll);

                        int updated = ps2.executeUpdate();

                        if (updated > 0)
                            System.out.println("Record updated successfully!");
                        else
                            System.out.println("Record not found!");

                        ps2.close();
                        break;

                    case 3:
                        System.out.print("Enter Roll Number to delete: ");
                        roll = sc.nextInt();

                        PreparedStatement ps3 = con.prepareStatement(
                            "DELETE FROM Student WHERE roll_no = ?"
                        );

                        ps3.setInt(1, roll);

                        int deleted = ps3.executeUpdate();

                        if (deleted > 0)
                            System.out.println("Record deleted successfully!");
                        else
                            System.out.println("Record not found!");

                        ps3.close();
                        break;

                    case 4:
                        ResultSet rs = st.executeQuery(
                            "SELECT * FROM Student"
                        );

                        System.out.println("\nRoll No\tName\tMarks");
                        System.out.println("----------------------------");

                        while (rs.next()) {
                            System.out.println(
                                rs.getInt("roll_no") + "\t" +
                                rs.getString("name") + "\t" +
                                rs.getDouble("marks")
                            );
                        }

                        rs.close();
                        break;

                    case 5:
                        System.out.println("Program terminated.");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 5);

            st.close();
            con.close();
            sc.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}