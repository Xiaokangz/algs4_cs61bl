import java.util.*;
import java.util.stream.Collectors;

public class UserExercises extends DBTable<User> {
    UserExercises() {
    }

    UserExercises(Collection<User> lst) {
        super(lst);
    }

    /**
     * Get an ordered List of Users, sorted first on age,
     * then on their id if the age is the same.
     */
    public List<User> getOrderedByAgeThenId() {

        return entries.stream()
                .sorted((u1, u2) -> {
                    if (u1.getAge() != u2.getAge()) {
                        return u1.getAge() - u2.getAge();
                    } else {
                        return u1.getId() - u2.getId();
                    }
                })
                .collect(Collectors.toList()); // FIX ME
    }

    /**
     * Get the average age of all the users.
     * If there are no users, the average is 0.
     */
    public double getAverageAge() {
        return entries.stream()
                .mapToInt(u -> u.getAge())
                .average()
                .orElse(0); // FIX ME
        // HINT: You may find an IntStream helpful 
    }

    /**
     * Group usernames by user age, for all users that have an age greater than min_age.
     * Usernames with ages less than or equal to min_age are excluded.
     * Returns a Map from each age present to a list of the usernames that have that age.
     */
    public Map<Integer, List<String>> groupUsernamesByAgeOlderThan(int min_age) {
        return entries.stream()
                .filter(u -> u.getAge() > min_age)
                .collect(Collectors.groupingBy(User::getAge, Collectors.mapping(User::getUsername, Collectors.toList()))); // FIX ME
        // HINT: See the Additional Examples for a helpful Collector
        // HINT2: You will need to use Collectors.mapping. See the Javadocs for examples
    }

    public static void main(String[] args) {
        User u1 = new User(2, "daniel", "dando@gmail.com");
        User u2 = new User(3, "matt", "italy@gmail.com");
        User u3 = new User(1, "sarahjkim", "potato@potato.com");
        User u4 = new User(1, "alanyao", "potato@cs61bl.org");
        UserExercises t = new UserExercises(Arrays.asList(
                u1,
                u2,
                u3,
                u4
        ));
        System.out.println(t.groupUsernamesByAgeOlderThan(20));
    }
}
