package Tank01;

public class ViolableBarrier extends Barrier {
    private final static int size = 10;


    public int getSize() {
        return size;
    }



    public ViolableBarrier(int x, int y) {
        super(x, y);
    }

    public ViolableBarrier(int x, int y, boolean isLive) {
        super(x, y, isLive);
    }

}
