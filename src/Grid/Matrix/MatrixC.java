package Grid.Matrix;

public class MatrixC {

    final int SPECIFYHEAT;
    final int DENSITY;
    final int N = 4;
    final int M=4;
    double tabJacobian[][];
    double tabDetJ [];
    double tabJacobian2[][];
    double tabKsi[];
    double tabEta[];
    double tableN[][];
    double tablePC[][][] = new double[M][N][N];
    double MatrixC[][] = new double[N][N];


    public MatrixC(double tabJacobian[][],double tabJacobian2[][],double tabDetJ[],double tabKsi[], double tabEta[],double tableN[][],int SPECIFYHEAT,int DENSITY){
        this.tabJacobian = tabJacobian;
        this.tabJacobian2 = tabJacobian2;
        this.tabDetJ = tabDetJ;
        this.tabKsi = tabKsi;
        this.tabEta =  tabEta;
        this.tableN = tableN;
        this.SPECIFYHEAT = SPECIFYHEAT;
        this.DENSITY = DENSITY;
    }
    public void calculateMatrixC() {

        for (int a = 0; a < M; a++) {
            for (int b = 0; b < N; b++) {
                for (int c = 0; c < N; c++) {
                    tablePC[a][b][c] = tableN[a][c] * tableN[a][b] * SPECIFYHEAT * DENSITY * tabDetJ[a];
                }
            }
        }

        for(int a=0;a<M;a++) {
            for (int b = 0; b < N; b++) {
                for(int c=0; c<N; c++) {
                    MatrixC[b][c] += tablePC[a][b][c];
                }
            }
        }
    }

    public double[][] getMatrixC() {
        return MatrixC;
    }
}
