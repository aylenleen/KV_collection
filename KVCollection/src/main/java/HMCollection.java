import java.util.*;

public class HMCollection<K, T> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private int size = 0;
    private Object[] array = new Object[INITIAL_CAPACITY];


    public boolean put(K key, T obj) {
        if(size >= array.length * LOAD_FACTOR) increaseArray();
        ListOfValues<T> objects = new ListOfValues<>();
        objects.add(obj);
        boolean wasAdded = put(key, objects, array);
        if (wasAdded) size++;
        return wasAdded;
    }


    public boolean put(K key, T... obj) {
        if(size >= array.length * LOAD_FACTOR) increaseArray();
        ListOfValues<T> objects = new ListOfValues<>();
        objects.add(obj);
        boolean wasAdded = put(key, objects, array);
        if(wasAdded) size++;
        return wasAdded;
    }

    private boolean put(K key, ListOfValues<T> values, Object[] dst) {
        int position = getElementPosition(key, dst.length);
        Entry existedElement = (Entry) dst[position];
        if(existedElement == null) {
            dst[position] = new Entry(key, values, null);
            return true;
        } else {
            while(true) {
                if(existedElement.key.equals(key)) {
                    for(T obj : values) {
                        existedElement.values.add(obj);
                    }
                    return false;
                }
                else if(existedElement.next == null) {
                    existedElement.next = new Entry(key,values, null);
                    return true;
                } else {
                    existedElement = existedElement.next;
                }
            }
        }
    }


    public List<T> getValuesOf(K key) {
        int position = getElementPosition(key, array.length);
        if(array[position] == null) {
            return null;
        } else {
            Entry existedElement = (Entry) array[position];
            while (existedElement != null) {
                if (existedElement.key.equals(key)) {
                    List<T> valuesOf = new LinkedList<>();
                    for (T dragon : existedElement.values) {
                        valuesOf.add(dragon);
                    }
                    return valuesOf;
                } else {
                    existedElement = existedElement.next;
                }
            }
        }
        return null;
    }


    public Set<K> keySet() {
        Set<K> setOfKeys = new HashSet<>();
        for(Object entry : array) {
            Entry existedElement = (Entry) entry;
            while(existedElement != null) {
                setOfKeys.add(existedElement.key);
                existedElement = existedElement.next;
            }
        }
        return setOfKeys;
    }


    public List<T> values() {
        List<T> allValues = new ArrayList<>();
        for(Object entry : array) {
            Entry existedElement = (Entry) entry;
            while (existedElement != null) {
                for (T dragon : existedElement.values){
                    allValues.add(dragon);
                }
                existedElement = existedElement.next;
            }
        }
        return allValues;
    }


    public boolean remove(K key) {
        int position = getElementPosition(key, array.length);
        if(array[position] == null) {
            return false;
        } else {
            Entry existedElement = (Entry) array[position];
            Entry nextElement = existedElement.next;
            if (existedElement.key.equals(key)) {
                array[position] = existedElement.next;
                size--;
                return true;
            }
            while (nextElement != null) {
                if (nextElement.key.equals(key)) {
                    existedElement.next = nextElement.next;
                    size--;
                    return true;
                } else {
                    nextElement = nextElement.next;
                }
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        array = new Object[INITIAL_CAPACITY];
    }
    private int getElementPosition(K key, int arrayLength) {
        return Math.abs(key.hashCode() % arrayLength);
    }
    private void increaseArray() {
        Object[] newArray = new Object[array.length * 2];
        for(Object object : array) {
            Entry existedElement = (Entry) object;
            while(existedElement != null) {
                put(existedElement.key, existedElement.values, newArray);
                existedElement = existedElement.next;
            }
        }
        array = newArray;
    }
    private class Entry {
        private K key;
        private ListOfValues<T> values;
        private Entry next;

        public Entry(K key, ListOfValues<T> values, Entry next) {
            this.key = key;
            this.values = values;
            this.next = next;
        }
    }

}

