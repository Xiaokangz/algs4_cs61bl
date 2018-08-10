public class UnstableSelectionSort {

	public static void selectionSort(Comparable[] arr) {
		for (int j = arr.length - 1; j > 0; j--) {
			for (int k = 1; k <= j; k++) {
				if (arr[k].compareTo(arr[k - 1]) <= -1) {
					Comparable temp = arr[k];
					arr[k] = arr[k - 1];
					arr[k - 1] = temp;
				}
			}
		}
	}
}
