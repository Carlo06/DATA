import java.util.NoSuchElementException;

public class StudentLinkedList {
    private Node head;

    public void addStudent(Student student) {
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    public Student findStudentById(int id) {
        Node current = head;
        while (current != null) {
            if (current.getStudent().getId() == id) {
                return current.getStudent();
            }
            current = current.getNext();
        }
        throw new NoSuchElementException("Student not found.");
    }

    public boolean updateStudent(int id, String name, double gwa) {
        Node current = head;
        while (current != null) {
            if (current.getStudent().getId() == id) {
                current.getStudent().setName(name);
                current.getStudent().setGwa(gwa);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean deleteStudentById(int id) {
        if (head == null) {
            return false; // List is empty
        }

        if (head.getStudent().getId() == id) {
            head = head.getNext();
            return true; // Deleted from the head
        }

        Node current = head;
        while (current.getNext() != null) {
            if (current.getNext().getStudent().getId() == id) {
                current.setNext(current.getNext().getNext());
                return true; // Deleted from the middle
            }
            current = current.getNext();
        }

        return false; // Student not found
    }

    public void displayStudents() {
        Node current = head;
        while (current != null) {
            System.out.println(current.getStudent());
            current = current.getNext();
        }
    }
}
