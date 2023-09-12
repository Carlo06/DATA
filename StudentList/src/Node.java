public class Node {
    private Student student;
    private Node next;

    public Node(Student student) {
        this.student = student;
        this.next = null;
    }

    public Student getStudent() {
        return student;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
