import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBTable<T> {
    protected List<T> entries;

    public DBTable() {
        this.entries = new ArrayList<>();
    }

    public DBTable(Collection<T> lst) {
        entries = new ArrayList<>(lst);
    }

    public void add(T t) {
        entries.add(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DBTable<?> dbTable = (DBTable<?>) o;
        return entries != null ? entries.equals(dbTable.entries) : dbTable.entries == null;

    }

    /**
     * Add all items from a collection to the table
     */
    public void add(Collection<T> col) {
        col.forEach(this::add);
    }

    /**
     * Returns a copy of the entries in this table
     */
    List<T> getEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * getOrderedBy should create a new list ordered on the results of the getter,
     * without modifying the entries.
     * @param getter Gets a field from or processes an object of type T, and returns
     *               a Comparable.
     * @param <R> The type returned by the getter method, and the type ordered on.
     * @return A List of the contents of this table, ordered by result of the getter.
     */
    public <R extends Comparable<R>> List<T> getOrderedBy(Function<T, R> getter) {
        return entries.stream()
                .sorted((T t1, T t2) -> (getter.apply(t1)).compareTo(getter.apply(t2)))
                .collect(Collectors.toList());
        /*List<T> l = new ArrayList<>(entries);
        l.sort((T t1, T t2) -> (getter.apply(t1)).compareTo(getter.apply(t2))); // FIX ME
        return l;*/
    }

    /**
     * groupByWhitelist() takes in a getter and a whitelist, and groups entries by the key given by
     * the getter as long as the key is present in the whitelist.
     * @param getter Gets a field from or process an object of type T.
     * @param whitelist A Collection of keys.
     * @param <R> The key type and return type of the getter.
     * @return A map from each key allowed to a list of the matching entries.
     * All keys present in this DB as obtained by the getter and in the whitelist are allowed.
     */
    public <R> Map<R, List<T>> groupByWhitelist(Function<T, R> getter, Collection<R> whitelist) {
        return entries.stream()
                .filter(t -> whitelist.contains(getter.apply(t)))
                .collect(Collectors.groupingBy(getter)); // FIX ME
    }

    /**
     * Creates a DBTable that contains the elements as obtained by the getter.
     * For example, getting a DBTable of usernames would look like this:
     * DBTable<String> names = table.getSubtableOf(User::getUsername);
     */
    public <R> DBTable<R> getSubtableOf(Function<T, R> getter) {
        return new DBTable<>(entries.stream()
                .map(t -> getter.apply(t))
                .collect(Collectors.toList())); // FIX ME
    }

    public static void main(String[] args) {
        /* Basic test DB*/
        DBTable<User> t = new DBTable<>(Arrays.asList(
                new User(2, "daniel", "dando@gmail.com"),
                new User(3, "matt", "italy@gmail.com"),
                new User(1, "sarahjkim", "potato@potato.com"),
                new User(1, "alanyao", "potato@cs61bl.org")
        ));
        System.out.println("t = " + t.groupByWhitelist(User::getId, Arrays.asList(1, 2)));
        //System.out.println((t.getSubtableOf(User::getUsername)).getEntries().toString());
    }

    /*public static void main(String[] args) {
        DBTable<User> t = new DBTable<>(Arrays.asList(
                new User(2, "christine", ""),
                new User(4, "antares", ""),
                new User(1, "dan", ""),
                new User(1, "daniel", ""),
                new User(5, "ching", "")
        ));
        List<User> l = t.getOrderedBy(User::getUsername);
        System.out.println(l);
        System.out.println(t.getEntries());
    }*/

}
