public class SieveOfEratosthenes {

	public static void main(String[] args) {
		if (args.length < 1) { 
			System.out.println("You need to enter an argument!");
		}
		int upperBound = Integer.parseInt(args[0]);
		boolean[] isNotPrime = new boolean[upperBound + 1];
		isNotPrime[0] = true; isNotPrime[1] = true;
		for (int i = 0; i * i <= upperBound; i++) {
			if (isNotPrime[i] == true) {
				continue;
			} else {
				//THIS DATA HAS BEEN CORRUPTED; REPLACE IT!
				for (int j = i * 2; j <= upperBound; j += i) {
					isNotPrime[j] = true;
				}
			}
		}
		for (int i = 0; i <= upperBound; i++) {
			if (!isNotPrime[i]) {
				System.out.println(i + " is a prime number.");
			}
		}
	}
}