import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentLinkedList studentList = new StudentLinkedList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Display Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter student GWA: ");
                    double gwa = scanner.nextDouble();
                    studentList.addStudent(new Student(name, id, gwa));
                    break;

                case 2:
                    System.out.print("Enter student ID to search: ");
                    int searchId = scanner.nextInt();
                    Student foundStudent = studentList.findStudentById(searchId);
                    if (foundStudent != null) {
                        System.out.println("Found student: " + foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID to update: ");
                    int updateId = scanner.nextInt();
                    Student studentToUpdate = studentList.findStudentById(updateId);
                    if (studentToUpdate != null) {
                        System.out.print("Enter updated name: ");
                        scanner.nextLine(); // Consume newline
                        String updatedName = scanner.nextLine();
                        System.out.print("Enter updated GWA: ");
                        double updatedGwa = scanner.nextDouble();
                        studentToUpdate.setName(updatedName);
                        studentToUpdate.setGwa(updatedGwa);
                        System.out.println("Student updated: " + studentToUpdate);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    boolean deleted = studentList.deleteStudentById(deleteId);
                    if (deleted) {
                        System.out.println("Student with ID " + deleteId + " deleted.");
                    } else {
                        System.out.println("Student with ID " + deleteId + " not found.");
                    }
                    break;

                case 5:
                    System.out.println("\nList of Students:");
                    studentList.displayStudents();
                    break;

                case 6:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
