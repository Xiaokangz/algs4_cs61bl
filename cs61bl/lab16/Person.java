public class Person {

	private String myName;

	public Person(String name) {
		this.myName = name;
	}

	// return a String representation of the Person object
	public String toString() {
		return myName;
	}

	// Change the name of the person
	public void changeName(String newName) {
		this.myName = newName;
	}
	
	// TODO add additional methods
	public boolean equals (Object other) {
		if (!(other instanceof Person)) {
			return false;
		}
		Person otherp = (Person)other;
		if (myName.equals(otherp.toString())) {
			return true;
		} else {
			return false;
		}
	}
}
