package Grid.Matrix;

import static java.lang.StrictMath.sqrt;

public class MatrixH {
    double tabJacobian[][];
    double tabDetJ [];
    double tabJacobian2[][];
    double tabKsi[];
    double tabEta[];


    final int N = 4;
    final int M=4;

    double tabNdivKsi [][] = new double[M][N];
    double tabNdivEta [][] = new double[M][N];
    double tableMatrixX[][][] = new double[M][N][N];
    double tableMatrixY[][][] = new double[M][N][N];
    double tableMatrixDetJ[][][] = new double[M][N][N];
    double tabNdivX [][] = new double[M][N];
    double tabNdivY [][] = new double[M][N];
    final int K;
    double tableMatrixH [][] = new double[N][N];


    public MatrixH(double tabJacobian[][],double tabJacobian2[][],double tabDetJ[],double tabKsi[], double tabEta[],int conductivity){
        this.tabJacobian = tabJacobian;
        this.tabJacobian2 = tabJacobian2;
        this.tabDetJ = tabDetJ;
        this.tabKsi = tabKsi;
        this.tabEta =  tabEta;
        this.K = conductivity;

    }

    public void calculateMatrix(){
        int tmp = 0;
        for(int a=0;a<M;a++){
            for(int b=0;b<N;b++) {
                if(b==0){
                    tabNdivEta[a][b] = -0.25*(1-tabKsi[tmp]);
                    tabNdivKsi[a][b] = -0.25*(1-tabEta[tmp]);
                }
                if(b==1){
                    tabNdivEta[a][b] = -0.25*(1+tabKsi[tmp]);
                    tabNdivKsi[a][b] = 0.25*(1-tabEta[tmp]);
                }
                if(b==2){
                    tabNdivEta[a][b] = 0.25*(1+tabKsi[tmp]);
                    tabNdivKsi[a][b] = 0.25*(1+tabEta[tmp]);
                }
                if(b==3){
                    tabNdivEta[a][b] = 0.25*(1-tabKsi[tmp]);
                    tabNdivKsi[a][b] = -0.25*(1+tabEta[tmp]);
                }
            }
            tmp++;
        }
            tmp=0;
        for(int a=0;a<M;a++){
            for(int b=0;b<N;b++) {

                tabNdivX[a][b] = tabJacobian2[tmp][a]*tabNdivKsi[a][b] + tabJacobian2[tmp+1][a]*tabNdivEta[a][b];
            }
        }
        tmp=2;
        for(int a=0;a<M;a++){
            for(int b=0;b<N;b++) {

                tabNdivY[a][b] = tabJacobian2[tmp][a]*tabNdivKsi[a][b] + tabJacobian2[tmp+1][a]*tabNdivEta[a][b];
            }
        }



        for(int a=0;a<M;a++) {
            for (int b = 0; b < N; b++) {
                for (int c = 0; c < N; c++) {

                    tableMatrixX[a][b][c] = tabNdivX[a][b] * tabNdivX[a][c];
                    tableMatrixY[a][b][c] = tabNdivY[a][b] * tabNdivY[a][c];

                }
            }
        }

        for(int a=0;a<M;a++) {
            for (int b = 0; b < N; b++) {
                for (int c = 0; c < N; c++) {

                    tableMatrixX[a][b][c] *= tabDetJ[a];
                    tableMatrixY[a][b][c] *= tabDetJ[a];
                }
            }
        }

        for(int a=0;a<M;a++) {
            for (int b = 0; b < N; b++) {
                for (int c = 0; c < N; c++) {
                    tableMatrixDetJ[a][b][c] = K*(tableMatrixX[a][b][c] + tableMatrixY[a][b][c]);
                }
            }
        }

        for(int a=0;a<M;a++) {
            for (int b = 0; b < N; b++) {
                for(int c=0; c<N; c++) {
                    tableMatrixH[b][c] += tableMatrixDetJ[a][b][c];
                }
            }
        }
    }

    public double[][] getTableMatrixH() {
        return tableMatrixH;
    }

    public void printMatrix(){
        for(int a=0;a<N;a++){
            for(int b=0;b<N;b++) {

                System.out.print( tableMatrixH[a][b] +" ");
            }
            System.out.print( "\n");
        }

//        for(int a=0;a<N;a++) {
//            for (int b = 0; b < N; b++) {
//                for(int c=0;c<N;c++){
//                    System.out.print(tableMatrixDetJ[a][b][c]+"  ");
//                }
//                System.out.print("\n");
//            }
//            System.out.println("\n");
//        }
//        System.out.println("\n");


    }


}
