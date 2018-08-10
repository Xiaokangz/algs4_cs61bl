import java.util.HashMap;

public class Fibonacci {
	int callsToFib;
	int result;
	private HashMap<Integer, Integer> fibmap;

	public Fibonacci(int n){
		this.callsToFib = 0;
		fibmap = new HashMap<>();
		fibmap.put(0, 0);
		fibmap.put(1, 1);
		this.result = fib(n);
	}
	
	private int fib(int n) {
		callsToFib++;
		if (fibmap.containsKey(n)) {
			return fibmap.get(n);
		}
		int temp = fib(n - 1) + fib(n - 2);
		fibmap.put(n, temp);
		return temp;
	}

}
