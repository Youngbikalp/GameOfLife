
public class LifeGame {
    private int stepNum = 0;

    private int nGrid;
    private boolean lifeChanged;

    private int[][] grid;
    private int[][] grid1;

    LifeGame(int n) {
        nGrid = n;
        grid = new int[nGrid][nGrid];
        grid1 = new int[nGrid][nGrid];
        stepNum = 0;


        int i = nGrid / 2;
        grid[i][i] = grid[i - 1][i] = grid[i + 1][i] = grid[i][i - 1] = grid[i - 1][i + 1] = 1;
    }

    public int getGridCell(int i, int j) {
        return grid[i][j];
    }

    public void setGridCell(int i, int j, int val) {
        grid[i][j] = val;
    }

    public int getStepNum() {
        return stepNum;
    }

    public boolean gridChanged() {
        return lifeChanged;
    }

    public void make1Step() {
        for (int i = 0; i < nGrid; i++) {
            for (int j = 0; j < nGrid; j++) {
                int nbrs = 0;
                if (i > 0 && j > 0 && grid[i - 1][j - 1] > 0) nbrs++;
                if (i > 0 && grid[i - 1][j] > 0) nbrs++;
                if (i > 0 && j < nGrid - 1 && grid[i - 1][j + 1] > 0) nbrs++;
                if (j > 0 && grid[i][j - 1] > 0) nbrs++;
                if (j < nGrid - 1 && grid[i][j + 1] > 0) nbrs++;
                if (i < nGrid - 1 && j > 0 && grid[i + 1][j - 1] > 0) nbrs++;
                if (i < nGrid - 1 && grid[i + 1][j] > 0) nbrs++;
                if (i < nGrid - 1 && j < nGrid - 1 && grid[i + 1][j + 1] > 0) nbrs++;

                if (grid[i][j] > 0) {
                    if (nbrs > 3 || nbrs < 2) grid1[i][j] = 0;
                    else grid1[i][j] = 1;
                } else {
                    if (nbrs == 3) grid1[i][j] = 1;
                    else grid1[i][j] = 0;
                }
            }
        }

        lifeChanged = false;
        for (int i = 0; i < nGrid; i++) {
            for (int j = 0; j < nGrid; j++) {
                if (grid[i][j] != grid1[i][j]) {
                    lifeChanged = true;
                    break;
                }
            }
        }

        for (int i = 0; i < nGrid; i++) {
            for (int j = 0; j < nGrid; j++) {
                grid[i][j] = grid1[i][j];
            }
        }
        stepNum++;
    }
}