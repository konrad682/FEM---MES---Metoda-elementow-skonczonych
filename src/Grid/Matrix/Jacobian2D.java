package Grid.Matrix;


import Grid.Element;

import static java.lang.StrictMath.sqrt;

public class Jacobian2D {
    final int N = 4;
    final int M = 4;
    public double tabKsi[] = new double[] {-1/sqrt(3),1/sqrt(3),1/sqrt(3),-1/sqrt(3)};
    public double tabEta[] = new double[] {-1/sqrt(3),-1/sqrt(3),1/sqrt(3),1/sqrt(3)};
    public double tableN[][] = new double[M][N];
    double tabXP[] = new double[M];
    double tabYP[] = new double[M];
    public double tabJacobian[][] = new double[N][M];
    public double tabDetJ [] = new double[M];
    public double tabJacobian2[][] = new double[N][M];
    Element element;

    public Jacobian2D(Element element) {
    this.element = element;
    }

    public void CalculateJacobian(){

        for(int a=0;a<M;a++){
            tabXP[a]=0;
            tabYP[a]=0;
        }

        for(int a=0;a<M;a++){
            for(int b=0;b<N;b++){

                if(b==0){
                    tableN[a][b] = 0.25 * (1 - tabKsi[a])*(1-tabEta[a]);
                }
                if(b==1){
                    tableN[a][b] = 0.25 * (1 + tabKsi[a])*(1-tabEta[a]);
                }
                if(b==2){
                    tableN[a][b] = 0.25 * (1 + tabKsi[a])*(1+tabEta[a]);
                }
                if(b==3){
                    tableN[a][b] = 0.25 * (1 - tabKsi[a])*(1+tabEta[a]);
                }
            }
        }

        for(int a=0;a<M;a++){
            for(int b=0;b<N;b++){
                tabXP[a] += element.tableGlobalX[b]*tableN[a][b];
                tabYP[a] += element.tableGlobalY[b]*tableN[a][b];
            }
        }

        //Pochodne względem ksi oraz eta
        double funkcjaKsztaltuEta[][] = new double[N][M];
        double funkcjaKsztaltuKsi[][] = new double[N][M];

        for(int a=0;a<N;a++){
            for(int b=0;b<M;b++) {

                if(a==0){
                    funkcjaKsztaltuEta[a][b] = -0.25*(1-tabEta[b]);
                    funkcjaKsztaltuKsi[a][b] = -0.25*(1-tabKsi[b]);
                }
                if(a==1){
                    funkcjaKsztaltuEta[a][b] = 0.25*(1-tabEta[b]);
                    funkcjaKsztaltuKsi[a][b] = -0.25*(1+tabKsi[b]);
                }
                if(a==2){
                    funkcjaKsztaltuEta[a][b] = 0.25*(1+tabEta[b]);
                    funkcjaKsztaltuKsi[a][b] = 0.25*(1+tabKsi[b]);
                }
                if(a==3){
                    funkcjaKsztaltuEta[a][b] = -0.25*(1+tabEta[b]);
                    funkcjaKsztaltuKsi[a][b] = 0.25*(1-tabKsi[b]);
                }

            }
        }

        //Jacobian przeksztaalcenia

        for(int a=0;a<N;a++){
            for(int b=0; b<M;b++){
                tabJacobian[a][b] = 0;
            }
        }

        for(int a=0;a<N;a++){
            for(int b=0; b<M;b++){
                for(int c=0;c<N;c++){
                    if(a==0) {
                        tabJacobian[a][b] += funkcjaKsztaltuEta[c][b] * element.tableGlobalX[c];
                    }
                    if(a==1) {
                        tabJacobian[a][b] += funkcjaKsztaltuEta[c][b] * element.tableGlobalY[c];
                    }
                    if(a==2){
                        tabJacobian[a][b] += funkcjaKsztaltuKsi[c][b] * element.tableGlobalX[c];
                    }
                    if(a==3){
                        tabJacobian[a][b] += funkcjaKsztaltuKsi[c][b] * element.tableGlobalY[c];
                    }
                }
            }
        }

        for(int a=0;a<M;a++){
                tabDetJ[a] = tabJacobian[3][a]*tabJacobian[0][a]-tabJacobian[1][a]*tabJacobian[2][a];
        }

        for(int a=0;a<N;a++){
            for(int b=0; b<M;b++){
                if(a==0) {
                    tabJacobian2[a][b] = tabJacobian[3][b] / tabDetJ[b];
                }
                if(a==1){
                    tabJacobian2[a][b] = -tabJacobian[1][b]/tabDetJ[b];
                }
                if(a==2){
                    tabJacobian2[a][b] = tabJacobian[2][b]/tabDetJ[b];
                }
                if(a==3){
                    tabJacobian2[a][b] = tabJacobian[0][b]/tabDetJ[b];
                }
            }
        }

    }

    public void printJacobian(){

        /*
        for(int a=0;a<tableN.length;a++){
            for(int b=0;b<tableN.length;b++) {

                System.out.print( tableN[a][b] +" ");
            }
            System.out.print( "\n");
        }*/

        /*for(int a=0;a<tableN.length;a++){
                for(int b=0;b<tableN.length;b++) {

                    System.out.print( funkcjaKsztaltuEta[a][b] +" ");
                }
                System.out.print( "\n");
            }
            System.out.print( "\n");  System.out.print( "\n");

            for(int a=0;a<tableN.length;a++){
                for(int b=0;b<tableN.length;b++) {

                    System.out.print( funkcjaKsztaltuKsi[a][b] +" ");
                }
                System.out.print( "\n");
            }
            */

        for(int a=0;a<N;a++){
            for(int b=0; b<N;b++){

                System.out.print( tabJacobian[a][b] +" ");
            }
            System.out.print( "\n");
        }

        System.out.print( "\n");

        for(int a=0;a<tableN.length;a++) {
            System.out.print( tabDetJ[a] +" ");
        }
        System.out.print( "\n\n");

           for(int a=0;a<tableN.length;a++){
            for(int b=0;b<tableN.length;b++) {

                System.out.print( tabJacobian2[a][b] +" ");
            }
            System.out.print( "\n");
        }
    }
}
