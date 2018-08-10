public class ResizableList extends FixedSizeList {

    private int capacity;

    public ResizableList () {
        super(1);
        capacity = 1;
    }

    @Override
    public void add(int i, int k) {
        if (count == capacity) {
            capacity *= 2;
            int[] temp = new int[capacity];
            for (int j = 0; j < count; j++) {
                temp[j] = values[j];
            }
            values = temp;
        }
        super.add(i, k);
    }

}
