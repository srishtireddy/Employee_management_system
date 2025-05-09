import java.sql.*;
import java.util.Scanner;

public class EmployeeManagement {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/emp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tiger";

    private static Connection con;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            menu();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static boolean checkEmployee(String id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private static void addEmployee() {
        try {
            System.out.print("Enter Employee Id: ");
            String id = scanner.nextLine();
            if (checkEmployee(id)) {
                System.out.println("Employee already exists. Please try again.");
                return;
            }

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Employee Post: ");
            String post = scanner.nextLine();

            System.out.print("Enter Employee Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            String query = "INSERT INTO employees (id, name, position, salary) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, post);
            ps.setDouble(4, salary);
            ps.executeUpdate();

            System.out.println("Employee Added Successfully");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeEmployee() {
        try {
            System.out.print("Enter Employee Id: ");
            String id = scanner.nextLine();

            if (!checkEmployee(id)) {
                System.out.println("Employee does not exist. Please try again.");
                return;
            }

            String query = "DELETE FROM employees WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();

            System.out.println("Employee Removed Successfully");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void promoteEmployee() {
        try {
            System.out.print("Enter Employee Id: ");
            String id = scanner.nextLine();

            if (!checkEmployee(id)) {
                System.out.println("Employee does not exist. Please try again.");
                return;
            }

            System.out.print("Enter increase in Salary: ");
            double amount = Double.parseDouble(scanner.nextLine());

            String selectQuery = "SELECT salary FROM employees WHERE id = ?";
            PreparedStatement selectPs = con.prepareStatement(selectQuery);
            selectPs.setString(1, id);
            ResultSet rs = selectPs.executeQuery();
            rs.next();
            double currentSalary = rs.getDouble("salary");
            double newSalary = currentSalary + amount;

            String updateQuery = "UPDATE employees SET salary = ? WHERE id = ?";
            PreparedStatement updatePs = con.prepareStatement(updateQuery);
            updatePs.setDouble(1, newSalary);
            updatePs.setString(2, id);
            updatePs.executeUpdate();

            System.out.println("Employee Promoted Successfully");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayEmployees() {
        try {
            String query = "SELECT * FROM employees";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("Employee Id : " + rs.getString("id"));
                System.out.println("Employee Name : " + rs.getString("name"));
                System.out.println("Employee Post : " + rs.getString("position"));
                System.out.println("Employee Salary : " + rs.getDouble("salary"));
                System.out.println("------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void menu() {
        while (true) {
            System.out.println("\nWelcome to Employee Management Record");
            System.out.println("Press:");
            System.out.println("1 to Add Employee");
            System.out.println("2 to Remove Employee");
            System.out.println("3 to Promote Employee");
            System.out.println("4 to Display Employees");
            System.out.println("5 to Exit");

            System.out.print("Enter your Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addEmployee();
                case "2" -> removeEmployee();
                case "3" -> promoteEmployee();
                case "4" -> displayEmployees();
                case "5" -> {
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid Choice! Please try again.");
            }
        }
    }
}
