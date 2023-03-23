/**Written by tran1199*/

public class LinkedList<T extends Comparable<T>> implements List<T> {

    private Node head;
    private int size;
    private boolean isSorted;

    public LinkedList() {
        this.head = null;
        this.size = 0;
        this.isSorted = true;
    }

    public boolean add(T element) {
        if (element == null) {
            return false;
        } else {
            Node node = new Node(element, null); //new node to be added

            if (head == null) { //if list is empty, head is now the added node
                head = node;
            } else {
                Node n = head;
                while (n.getNext() != null) { //traverses list until it reaches the end
                    n = n.getNext();
                }
                if (node.getData().compareTo(n.getData()) < 0) { //if new node is smaller than the last node, isSorted is false
                    isSorted = false;
                }
                n.setNext(node);
            }
            size++;
            return true;
        }
    }

    public boolean add(int index, T element) {
        if (element == null || index < 0 || index >= this.size) {
            return false;
        } else {
            Node node = new Node(element, null);

            if (index == 0) {
                node.setNext(head);
                head = node;
                size++;
                return true;
            } else {
                Node n = head;
                for (int i = 0; i < index - 1; i++) { //traverses list until it reaches the node behind where the index is
                    n = n.getNext();
                }
                if (node.getData().compareTo(n.getData()) < 0) { //if new node is smaller than the last node, isSorted is false
                    isSorted = false;
                }
                node.setNext(n.getNext());
                n.setNext(node);
                size++;
                return true;
            }
        }
    }

    public void clear() { //resets the linked list
        this.head = null;
        this.size = 0;
        this.isSorted = true;
    }

    public T get(int index) {
        if (index < 0 || index >= this.size()) {
            return null;
        } else {
            Node n = head;
            for (int i = 0; i < index; i++) { //traverses until reaches index
                n = n.getNext();
            }
            return (T) n.getData(); //return the node
        }
    }

    public int indexOf(T element) {
        if (element == null) {
            return -1;
        } else {
            Node node = head;
            int ind = -1;

            for (int i = 0; i < this.size; i++) { //traverse the linked list
                if (node.getData() == element) { //check if the node == element
                    ind = i;
                    break;
                } // takes index if equals and exits the loop
                node = node.getNext(); //continue traversing otherwise
            }

            return ind;
        }
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public int size() {
        return this.size;
    }

    public void sort() {
        if (!isSorted) {
            Node curr = head;

            while (curr != null) {
                Node temp = curr;
                Node next = curr.getNext();

                while (next != null) {
                    if (next.getData().compareTo(temp.getData()) < 0) { //compares next node to the current one to find smallest node
                        temp = next;
                    }
                    next = next.getNext();
                }
                if (temp.getData().compareTo(curr.getData()) != 0) {  //if smallest found node in list isn't the same to the current, swaps places
                    T newt = (T) curr.getData();
                    curr.setData(temp.getData());
                    temp.setData(newt);
                }
                curr = curr.getNext();
            } //repeats until sorted
            isSorted = true;
        }
    }

    public T remove(int index) {
        T toReturn = null;
        if (index < 0 || index >= this.size()) {
            return null;
        } else if (index == 0) { //if removing head, sets head to next node
            toReturn = (T) head.getData();
            head = head.getNext();
            this.size--;
        } else {
            Node n = head;
            for (int i = 0; i < index - 1; i++) { //traverses until before the index
                n = n.getNext();
            }
            toReturn = (T) n.getNext().getData();
            n.setNext(n.getNext().getNext()); //sets next node (the to-be deleted) to the node after
        }
        return toReturn;
    }

    public void equalTo(T element) {
        if (element != null) {
            while (head != null && head.getData() != element) { //checks if the head equals the element
                head = head.getNext();
                size--;
            }
            Node node = head; //head should equal the element
            while (node.getNext() != null) { //goes until the end of the list
                if (node.getNext().getData() != element) { //if next node equals element, if not, set next to the node after it
                    node.setNext(node.getNext().getNext());
                    size--;
                } else { //if it does equal element, moves node to next
                    node = node.getNext();
                }
            }
            isSorted = true; //should be sorted since its all the same element
        }
    }

    public void reverse() {
        Node prev = null;
        Node curr = head;
        Node next;

        while (curr != null) { //traversing list
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        } //iteratively moves the list backwards
        head = prev;
    }

    public void merge(List<T> otherList) {
        if (otherList != null) {
            LinkedList<T> other = (LinkedList<T>) otherList;
            this.sort();
            other.sort();

            Node node = this.head, node2 = other.head;
            size = this.size + other.size;
            if (node.getData().compareTo(node2.getData()) < 0) { //if node is smaller than node2, the head will be the smallest element
                head = node;
            } else { //does the opposite and swaps the nodes
                head = node2;
                node2 = node;
                node = head;
            }
            while (node.getNext() != null) { //traversing
                if (node.getNext().getData().compareTo(node2.getData()) > 0) { //if next node is larger than node2
                    Node temp = node.getNext(); //temp grabs the larger node
                    node.setNext(node2); //sets next to the smaller node
                    node2 = temp; //set node2 to the larger node
                }
                node = node.getNext();
            }
            node.setNext(node2); //set final node to the largest
            isSorted = true;

        }
    } // O(n + m) since traversing two linked lists

    public void pairSwap() {

        if (head == null || head.getNext() == null) {

        } else {
            Node curr = head.getNext().getNext(); //gets the third node
            Node prev = head;
            head = head.getNext(); //head becomes the 2nd node
            head.setNext(prev); //sets the next one to old head

            while (curr != null && curr.getNext() != null) { //iteratively swaps nodes
                prev.setNext(curr.getNext());
                prev = curr;
                Node temp = curr.getNext().getNext();
                curr.getNext().setNext(curr);
                curr = temp;
            }
            prev.setNext(curr);
        }
    }

    public String toString() {
        Node node = head;
        String str = "";
        if (node == null) {
            return "List is empty";
        }
        while (node.getNext() != null) { // grabs each element and newlines after
            str += node.getData() + "\n";
            node = node.getNext();
        }
        str += node.getData();
        return str;
    }

    public boolean isSorted() {
        return this.isSorted;
    }
}
