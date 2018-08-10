package cube;

import java.util.Observable;

import static java.lang.System.arraycopy;

/** Models an instance of the Cube puzzle: a cube with color on some sides
 *  sitting on a cell of a square grid, some of whose cells are colored.
 *  Any object may register to observe this model, using the (inherited)
 *  addObserver method.  The model notifies observers whenever it is modified.
 *  @author P. N. Hilfinger
 */
public class CubeModel extends Observable {

    private int _side;
    private int _row;
    private int _col;
    private int _moves;
    private boolean[][] _painted;
    private boolean[] _facePainted;

    /** A blank cube puzzle of size 4. */
    public CubeModel() {
        // FIXME
        _side = 4;
        _row = 0;
        _col = 0;
        _moves = 0;
        _painted = new boolean[_side][_side];
        _facePainted = new boolean[6];
    }

    /** A copy of CUBE. */
    public CubeModel(CubeModel cube) {
        initialize(cube);
    }

    /** Initialize puzzle of size SIDExSIDE with the cube initially at
     *  ROW0 and COL0, with square r, c painted iff PAINTED[r][c], and
     *  with face k painted iff FACEPAINTED[k] (see isPaintedFace).
     *  Assumes that
     *    * SIDE > 2.
     *    * PAINTED is SIDExSIDE.
     *    * 0 <= ROW0, COL0 < SIDE.
     *    * FACEPAINTED has length 6.
     */
    public void initialize(int side, int row0, int col0, boolean[][] painted,
                    boolean[] facePainted) {
        // FIXME
        _side = side;
        _row = row0;
        _col = col0;
        _painted = new boolean[side][side];
        _facePainted = new boolean[6];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                _painted[i][j] = painted[i][j];
            }
        }
        for (int i = 0; i < 6; i++) {
            _facePainted[i] = facePainted[i];
        }
        setChanged();
        notifyObservers();
    }

    /** Initialize puzzle of size SIDExSIDE with the cube initially at
     *  ROW0 and COL0, with square r, c painted iff PAINTED[r][c].
     *  The cube is initially blank.
     *  Assumes that
     *    * SIDE > 2.
     *    * PAINTED is SIDExSIDE.
     *    * 0 <= ROW0, COL0 < SIDE.
     */
    public void initialize(int side, int row0, int col0, boolean[][] painted) {
        initialize(side, row0, col0, painted, new boolean[6]);
    }

    /** Initialize puzzle to be a copy of CUBE. */
    public void initialize(CubeModel cube) {
        // FIXME
        _row = cube.cubeRow();
        _col = cube.cubeCol();
        _side = cube.side();
        _moves = cube.moves();
        _painted = new boolean[_side][_side];
        _facePainted = new boolean[6];
        for (int i = 0; i < _side; i++) {
            for (int j = 0; j < _side; j++) {
                _painted[i][j] = cube.isPaintedSquare(i, j);
            }
        }
        for (int i = 0; i < 6; i++) {
            _facePainted[i] = cube.isPaintedFace(i);
        }
        setChanged();
        notifyObservers();
    }

    /** Move the cube to (ROW, COL), if that position is on the board and
     *  vertically or horizontally adjacent to the current cube position.
     *  Transfers colors as specified by the rules.
     *  Throws IllegalArgumentException if preconditions are not met.
     */
    public void change(int fid, int row, int col) {
        if (_facePainted[fid] != _painted[row][col]) {
            _facePainted[fid] = !_facePainted[fid];
            _painted[row][col] = !_painted[row][col];
        }
    }

    public static void roll(boolean[] f, int[] order) {
        boolean[] temp = new boolean[6];
        for (int i = 0; i < 6; i++) {
            temp[i] = f[order[i]];
        }
        for (int i = 0; i < 6; i++) {
            f[i] = temp[i];
        }
    }

    public void move(int row, int col) {
        // FIXME
        if (row < 0 || row >= _side || col < 0 || col >= _side) {
            throw new IllegalArgumentException("preconditions are not met");
        }
        if (row == _row && col == _col + 1) {
            change(3, row, col);
            int[] order = {0, 1, 4, 5, 3, 2};
            roll(_facePainted, order);
        } else if (row == _row && col == _col - 1) {
            change(2, row, col);
            int[] order = {0, 1, 5, 4, 2, 3};
            roll(_facePainted, order);
        } else if (row == _row + 1 && col == _col) {
            change(1, row, col);
            int[] order = {4, 5, 2, 3, 1, 0};
            roll(_facePainted, order);
        } else if (row == _row - 1 && col == _col) {
            change(0, row, col);
            int[] order = {5, 4, 2, 3, 0, 1};
            roll(_facePainted, order);
        } else {
            throw new IllegalArgumentException("preconditions are not met");
        }
        _moves++;
        _col = col;
        _row = row;
        setChanged();
        notifyObservers();
    }

    /** Return the number of squares on a side. */
    public int side() {
        return _side; // FIXME
    }

    /** Return true iff square ROW, COL is painted.
     *  Requires 0 <= ROW, COL < board size. */
    public boolean isPaintedSquare(int row, int col) {
        return _painted[row][col]; // FIXME
    }

    /** Return current row of cube. */
    public int cubeRow() {
        return _row; // FIXME
    }

    /** Return current column of cube. */
    public int cubeCol() {
        return _col; // FIXME
    }

    /** Return the number of moves made on current puzzle. */
    public int moves() {
        return _moves; // FIXME
    }

    /** Return true iff face #FACE, 0 <= FACE < 6, of the cube is painted.
     *  Faces are numbered as follows:
     *    0: Vertical in the direction of row 0 (nearest row to player).
     *    1: Vertical in the direction of last row.
     *    2: Vertical in the direction of column 0 (left column).
     *    3: Vertical in the direction of last column.
     *    4: Bottom face.
     *    5: Top face.
     */
    public boolean isPaintedFace(int face) {
        return _facePainted[face]; // FIXME
    }

    /** Return true iff all faces are painted. */
    public boolean allFacesPainted() {
        for (int i = 0; i < 6; i++) {
            if (!_facePainted[i]) {
                return false;
            }
        }
        return true;
    }

    // ADDITIONAL FIELDS AND PRIVATE METHODS HERE, AS NEEDED.

}
