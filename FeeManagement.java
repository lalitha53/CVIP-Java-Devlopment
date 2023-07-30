import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private double feesPaid;
    private double totalFees;

    public Student(String name, int rollNumber, double totalFees) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.totalFees = totalFees;
        this.feesPaid = 0;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public double getFeesPaid() {
        return feesPaid;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public void payFees(double amount) {
        feesPaid += amount;
    }

    public double getRemainingFees() {
        return totalFees - feesPaid;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Roll Number: " + rollNumber +
                ", Total Fees: " + totalFees +
                ", Fees Paid: " + feesPaid +
                ", Pending Dues: " + getRemainingFees();
    }
}

public class FeeManagement {
    private List<Student> students;
    private final String dataFileName = "FeeManagementData.txt";

    public FeeManagement() {
        students = new ArrayList<>();
        loadDataFromFile();
    }

    public void addStudent(String name, int rollNumber, double totalFees) {
        Student student = new Student(name, rollNumber, totalFees);
        students.add(student);
        saveDataToFile();
    }

    public void recordFeesPayment(int rollNumber, double amount) {
        Student student = findStudentByRollNumber(rollNumber);
        if (student != null) {
            student.payFees(amount);
            saveDataToFile();
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    public void displayStudentDetails(int rollNumber) {
        Student student = findStudentByRollNumber(rollNumber);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    private Student findStudentByRollNumber(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    private void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFileName))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFileName))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        FeeManagement system = new FeeManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Student\n2. Record Fees Payment\n3. Display Student Details\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Roll Number: ");
                    int rollNumber = scanner.nextInt();
                    System.out.print("Enter Total Fees: ");
                    double totalFees = scanner.nextDouble();
                    system.addStudent(name, rollNumber, totalFees);
                    System.out.println("Student Added Successfully!");
                    break;
                case 2:
                    System.out.print("Enter Roll Number: ");
                    rollNumber = scanner.nextInt();
                    System.out.print("Enter Amount to Pay: ");
                    double amount = scanner.nextDouble();
                    system.recordFeesPayment(rollNumber, amount);
                    System.out.println("Fees Payment Recorded!");
                    break;
                case 3:
                    System.out.print("Enter Roll Number: ");
                    rollNumber = scanner.nextInt();
                    system.displayStudentDetails(rollNumber);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    system.saveDataToFile();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice. Try Again.");
            }
        }
    }
}