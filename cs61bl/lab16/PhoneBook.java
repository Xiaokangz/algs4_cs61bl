import java.util.*;

public class PhoneBook {
    // TODO Add any instance variables necessary
    private HashMap<String, PhoneNumber> pbtable;

    public PhoneBook() {
        pbtable = new HashMap<>();
    }
    /*
     * Adds a person with this name to the phone book and associates
     * with the given PhoneNumber.
     */
    public void addEntry(Person personToAdd, PhoneNumber numberToAdd){
    	// TODO Add your own code
        pbtable.put(personToAdd.toString(), numberToAdd);
    }

    /*
     * Access an entry in the phone book. 
     */
    public PhoneNumber getNumber(Person personToLookup){
    	// TODO Add your own code
    	return pbtable.get(personToLookup.toString());
    }

}
