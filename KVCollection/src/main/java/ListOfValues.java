import java.util.LinkedList;

public class ListOfValues<T> extends LinkedList<T> {

    @Override
    public boolean add(T object) {
            add(size(), object);
            return true;
    }

    public void add(T... objects) {
        for (T object : objects) {
            add(object);
        }
    }

    @Override
    public void add(int index, T object) {
        if (!contains(object)) {
            add(index, object);
        }
    }
}
