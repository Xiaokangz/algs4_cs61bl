public class ModNCounter extends Counter {
    private int N;

    ModNCounter (int N) {
        this.N = N;
    }

    ModNCounter () {
        this.N = 1;
    }

    public void increment () {
        if (this.value() == N - 1) {
            this.reset();
        } else {
            super.increment();
        }
    }
}
