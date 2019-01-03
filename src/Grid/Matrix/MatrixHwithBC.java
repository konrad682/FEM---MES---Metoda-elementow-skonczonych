package Grid.Matrix;

import Grid.Element;
import static java.lang.StrictMath.sqrt;

public class MatrixHwithBC {

    double sideLengthTable[] = new double[4];
    double MatrixHwithBCTable[][] = new double[4][4];
    Element element;
    int alfa,ambientTemp;

    int counter;
    double vectorP[][] = new double[4][4];

    public MatrixHwithBC(Element element,int counter,int alfa,int ambientTemp){
        this.element = element;
        this.counter = counter;
        this.alfa = alfa;
        this.ambientTemp = ambientTemp;
        function();
    }

    private void function(){
        computeSideLength();
        boundaryConditions();
    }

    public void computeSideLength(){
        sideLengthTable[0] = sqrt(Math.pow(element.tableGlobalX[1]-element.tableGlobalX[0],2) + Math.pow(element.tableGlobalY[1]-element.tableGlobalY[0],2));
        sideLengthTable[1] = sqrt(Math.pow(element.tableGlobalX[1]-element.tableGlobalX[2],2) + Math.pow(element.tableGlobalY[1]-element.tableGlobalY[2],2));
        sideLengthTable[2] = sqrt(Math.pow(element.tableGlobalX[2]-element.tableGlobalX[3],2) + Math.pow(element.tableGlobalY[2]-element.tableGlobalY[3],2));
        sideLengthTable[3] = sqrt(Math.pow(element.tableGlobalX[3]-element.tableGlobalX[0],2) + Math.pow(element.tableGlobalY[3]-element.tableGlobalY[0],2));

    }

    public void boundaryConditions(){
        double tableSumPC[][]= new double[4][4];
        double tableSumPC1[][]= new double[4][4];
        double tableSumPC2[][]= new double[4][4];
        double tableSumPC3[][]= new double[4][4];
        if(element.tableBoundaryConditions[0] == true) tableSumPC = boundaryConditionsArea1();
        if(element.tableBoundaryConditions[1] == true) tableSumPC1 = boundaryConditionsArea2();
        if(element.tableBoundaryConditions[2] == true) tableSumPC2 = boundaryConditionsArea3();
        if(element.tableBoundaryConditions[3] == true) tableSumPC3 = boundaryConditionsArea4();

        for(int a=0;a<4;a++){
            for(int b=0;b<4;b++){
                 MatrixHwithBCTable[a][b] = tableSumPC[a][b] + tableSumPC1[a][b] + tableSumPC2[a][b] + tableSumPC3[a][b];
            }
        }
            element.tableVectorP = vectorP;
    }


    public double[][] boundaryConditionsArea1(){
        double tableSumPC[][];
        double detJ = sideLengthTable[0]/2;
        double tabKsi[] = new double[] {-1/sqrt(3),1/sqrt(3)};
        double tabEta[] = new double[] {-1,-1};
        tableSumPC = generatortablePCandTableN(tabKsi,tabEta,detJ);
    return tableSumPC;
    }
    public double[][] boundaryConditionsArea2(){
        double tableSumPC[][];
        double detJ = sideLengthTable[1]/2;
        double tabKsi[] = new double[] {1,1};
        double tabEta[] = new double[] {-1/sqrt(3),1/sqrt(3)};
        tableSumPC = generatortablePCandTableN(tabKsi,tabEta,detJ);
        return tableSumPC;
    }
    public double[][] boundaryConditionsArea3(){
        double tableSumPC[][];
        double detJ = sideLengthTable[2]/2;
        double tabKsi[] = new double[] {1/sqrt(3),-1/sqrt(3)};
        double tabEta[] = new double[] {1,1};
        tableSumPC = generatortablePCandTableN(tabKsi,tabEta,detJ);
        return tableSumPC;
    }
    public double[][] boundaryConditionsArea4(){
        double tableSumPC[][];
        double detJ = sideLengthTable[3]/2;
        double tabKsi[] = new double[] {-1,-1};
        double tabEta[] = new double[] {1/sqrt(3),-1/sqrt(3)};
        tableSumPC = generatortablePCandTableN(tabKsi,tabEta,detJ);
        return tableSumPC;
    }

    public double[][] generatortablePCandTableN(double tabKsi[],double tabEta[],double detJ){
        double tableN[][] = new double[2][4];
        double tablePC[][][] = new double[2][4][4];
        for(int a=0;a<2;a++){
            for(int b=0;b<4;b++){
                if(b==0) tableN[a][b] = 0.25*(1-tabKsi[a])*(1-tabEta[a]);
                if(b==1) tableN[a][b] = 0.25*(1+tabKsi[a])*(1-tabEta[a]);
                if(b==2) tableN[a][b] = 0.25*(1+tabKsi[a])*(1+tabEta[a]);
                if(b==3) tableN[a][b] = 0.25*(1-tabKsi[a])*(1+tabEta[a]);
            }
        }

        for(int a=0;a<2;a++){
            for(int b=0;b<4;b++){
                for(int c=0;c<4;c++) {
                    tablePC[a][b][c] = tableN[a][c]*tableN[a][b]*alfa;
                }
            }
        }

        double tableSumPC[][] = new double[4][4];

        for(int b=0;b<4;b++){
            for(int c=0;c<4;c++) {
                tableSumPC[b][c] = (tablePC[0][b][c]+tablePC[1][b][c])*detJ;
            }
        }

        //Vector P

        double tablePCVectorP[][][] = new double[2][4][4];
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 4; b++) {
                for (int c = 0; c < 4; c++) {
                    tablePCVectorP[a][b][c] = tableN[a][c] * tableN[a][b] * alfa * ambientTemp * detJ;
                }
            }
        }

        double tmpVectorP[][] = new double[4][4];
        for(int a=0;a<2;a++) {
            for (int b = 0; b < 4; b++) {
                for(int c=0; c<4; c++) {
                    tmpVectorP[b][c] += tablePCVectorP[a][b][c];
                }
            }
        }

        for (int b = 0; b < 4; b++) {
            for(int c=0; c<4; c++) {
                vectorP[b][c] += tmpVectorP[b][c];
            }
        }

        return tableSumPC;
    }

    public double[][] getMatrixHwithBCTable() {
        return MatrixHwithBCTable;
    }
}
