/** Written by tran1199*/

public class ArrayList<T extends Comparable<T>> implements List<T> {

    private boolean isSorted;
    private int size;
    private T[] a;

    public ArrayList() {
        a = (T[]) new Comparable[2];
        this.isSorted = true;
        this.size = 0;
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(10);
        list.add(15);
        System.out.println(list.isSorted);
        list.add(30);
        list.add(20);
        System.out.println(list.isSorted);

    }

    private void addLength() { //method to add length to array if size limit is reached
        if (size == a.length) {
            T[] newList = (T[]) new Comparable[a.length * 2];

            for (int i = 0; i < size; i++) {
                newList[i] = a[i];
            }
            a = newList;
        }
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        } else if (size == a.length) { //add length if needed
            addLength();
        }
        if (size != 0) {
            if (element.compareTo(get(size - 1)) < 0) { //new element is smaller, isSorted is false
                isSorted = false;
            }
        }
        a[size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null || index < 0 || index >= size) {
            return false;
        } else {
            if (size == a.length) { //adds length if needed
                addLength();
            }
            for (int i = size - 1; i >= index; i--) { //iterates through the list from the end
                a[i + 1] = a[i]; //moves all the elements to the right one
            }
            a[index] = element; //appends element
            if (element.compareTo(a[index + 1]) > 0) { //if added element is bigger than the one after or smaller than the one before it, isSorted is false
                isSorted = false;
            }
            if (index != 0) {
                if (element.compareTo(a[index - 1]) < 0) {
                    isSorted = false;
                }
            }
            size++;
            return true;
        }
    }

    @Override
    public void clear() {
        this.a = (T[]) new Comparable[2];
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < size && index >= 0) { //checks if index is out of bounds
            return a[index];
        }
        return null;
    }

    @Override
    public int indexOf(T element) {
        if (element != null) {
            for (int i = 0; i < size; i++) { //iterates through array
                if (a[i].equals(element)) { //if element is found, return the index it was found at
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void sort() {
        if (!isSorted) {
            for (int i = 0; i < size - 1; i++) { //iterates through list to the end
                T min = get(i);
                int minInd = i;
                for (int j = i + 1; j < size; j++) { //iterates again
                    if (min.compareTo(get(j)) > 0) { //if 'min' is larger than j
                        min = get(j); //replaces 'min' if j is smaller than previous min
                        minInd = j;
                    }
                }
                if (minInd != i) { //correctly places the smallest found element to the front of array
                    a[minInd] = get(i);
                    a[i] = min;
                }
            }
            isSorted = true;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            T toReturn = a[index]; //prepares to return the element
            for (int i = index + 1; i < size; i++) { //starts at the element after the index and moves all the elements left one
                a[i - 1] = a[i];
            }
            if (index != size - 1) {
                if (a[index].compareTo(a[index + 1]) > 0) { //if the element that replaced the old onne is larger than the element in front, isSorted is false
                    isSorted = false;
                }
            }
            if (index != 0) {
                if (a[index].compareTo(a[index - 1]) < 0) {// if new element is smaller than the one behind it, isSorted is false.
                    isSorted = false;
                }
            }
            size--;
            return toReturn;
        }
    }

    @Override
    public void equalTo(T element) {
        if (element != null) {
            for (int i = 0; i < size; i++) { //iterates through the array
                if (!get(i).equals(element)) { //if current index element does not equal the param element
                    remove(i); //remove the non-correct element
                    i--; //go back an index
                }
            }
        }
        isSorted = true; //should be sorted since it's all the same number
    }

    @Override
    public void reverse() {
        if (this.size() > 1) {
            T val = this.remove(0);
            reverse();
            this.add(val);
        }
    } //recursively reverses the array by removing first element and adding it to the end

    @Override
    public void merge(List<T> otherList) {
        if (otherList != null) {
            ArrayList<T> other = (ArrayList<T>) otherList;
            sort();
            other.sort();
            T[] temp = (T[]) new Comparable[a.length + other.a.length]; //new ArrayList that has the right size

            int i = 0, j = 0, k = 0;
            while (i < size && j < other.size) { //iterates through both lists
                if (a[i].compareTo(other.get(j)) < 0) { //if a element is smaller than other, temp appends it
                    temp[k++] = a[i++];
                } else { //appends the smaller element
                    temp[k++] = other.get(j++);
                }
            }
            while (i < a.length) { //if there are elements left in this list, appends the rest
                temp[k++] = a[i++];
            }
            while (j < other.size) { //appends the rest of other list
                temp[k++] = other.get(j++);
            }
            this.size = temp.length; //size now equals new list length
            this.a = temp;
        }
    }

    @Override
    public void pairSwap() {
        for (int i = 0; i < a.length - 1; i += 2) { //iterates through array but increments of 2
            T temp = a[i]; //temporarily holds element 1
            a[i] = a[i + 1]; //sets spot 1 to element 2
            a[i + 1] = temp; //sets spot 2 to element 1
        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += get(i) + "\n";
        }
        return str;
    }

    @Override
    public boolean isSorted() {
        return this.isSorted;
    }
}
