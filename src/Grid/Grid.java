package Grid;

import Grid.Matrix.Jacobian2D;
import Grid.Matrix.MatrixC;
import Grid.Matrix.MatrixH;
import Grid.Matrix.MatrixHwithBC;

public class Grid {

    public double H,L;
    public int nH,nL,nh,nE;
    public Node node[];
    public Element element[];
    public double globalMatrixH[][];
    public double globalVectorP[][];
    public double globalMatrixC[][];
    public int conductivity,speecificHeat,density,alfa,ambientTemp,initialTemp,simulationTime,simulationStepTime;
    public double tableTemperature[];

    public Grid(int nH,int nL,double H,double L,int conductivity,int speecificHeat, int density, int alfa,int ambientTemp,int initialTemp,int simulationTime,int simulationStepTime){
        this.H = H;
        this.L = L;
        this.nH = nH;
        this.nL = nL;
        this.conductivity = conductivity;
        this.speecificHeat = speecificHeat;
        this.density = density;
        this.nh = nH*nL;
        this.nE = (nH-1)*(nL-1);
        this.node = new Node[nh];
        this.element = new Element[nE];
        this.globalMatrixH = new double[nH*nL][nH*nL];
        this.globalMatrixC = new double[nH*nL][nH*nL];
        this.globalVectorP = new double[nH*nL][nH*nL];
        this.tableTemperature = new double[nH*nL];
        this.alfa = alfa;
        this.ambientTemp = ambientTemp;
        this.initialTemp = initialTemp;
        this.simulationTime = simulationTime;
        this.simulationStepTime = simulationStepTime;
        functions();
    }

    public void functions(){
        GeneratorElement();
        GeneratorNode();
        generetorElementLocal();
        ElementIsAside();
        Jacobian2D();
        agregateMatrixHandC();
        calucalteMatrixIteration();

        //printElement();
        //printNode();
        //printCoordElement();
        //printGlobalMatrix();

    }

    public void GeneratorElement(){

        int element_value = 1;

            for(int i=0; i<element.length;i++){
                if(0 == element_value%nH)
                    element_value++;
                element[i]= new Element(element_value,nH);
                element_value++;
        }
    }

    public void GeneratorNode(){
        for(int i=0;i<node.length;i++){
            node[i]=new Node();
        }
        double delX = L/(nL-1);
        double delY = H/(nH-1);
        double x = 0, y = 0;
        int counter = 0;
        for(int i=1;i<=nL;i++){
            for(int j=0;j<nH;j++){
                node[counter].x=x;
                node[counter].y=y;
                y+=delY;
                counter++;
            }
            y=0;
            x+=delX;
        }
    }

    public void generetorElementLocal(){

        double[] tempX;
        double[] tempY;
        for(int b=0; b<nE; b++ ) {

            tempX = new double[4];
            tempY = new double[4];
            for(int a = 0; a < 4; a++) {
                tempX[a] = node[element[b].idArray[a]-1].x;
                tempY[a] = node[element[b].idArray[a]-1].y;
                }
            element[b].NodesElement(tempX,tempY);
        }
    }

    public void Jacobian2D(){
        Jacobian2D jacobian2D;
        MatrixH matrixH;
        MatrixC matrixC;
        MatrixHwithBC matrixHwithBC;

        for(int a=0;a<element.length;a++) {
            jacobian2D = new Jacobian2D(element[a]);
            jacobian2D.CalculateJacobian();

            matrixH = new MatrixH(jacobian2D.tabJacobian, jacobian2D.tabJacobian2, jacobian2D.tabDetJ, jacobian2D.tabKsi, jacobian2D.tabEta,conductivity);
            matrixH.calculateMatrix();
            element[a].tableMatrixH = matrixH.getTableMatrixH();
            //element[a].printMatrixH();

            matrixC = new MatrixC(jacobian2D.tabJacobian,jacobian2D.tabJacobian2,jacobian2D.tabDetJ,jacobian2D.tabKsi,jacobian2D.tabEta,jacobian2D.tableN,speecificHeat,density);
            matrixC.calculateMatrixC();
            element[a].tableMatrixC = matrixC.getMatrixC();
            //element[a].printMatrixC();

            matrixHwithBC = new MatrixHwithBC(element[a],a,alfa,ambientTemp);
            element[a].tableMatrixHwithBC = matrixHwithBC.getMatrixHwithBCTable();
           // element[a].printMatrixHwithBC();
        }

        for(int a=0;a<element.length;a++){
            for(int x=0;x<4;x++){
                for(int y=0;y<4;y++){
                    element[a].tableMatrixH[x][y] = element[a].tableMatrixH[x][y] + element[a].tableMatrixHwithBC[x][y];

                }
            }
        }

    }

    public void ElementIsAside(){

        boolean tableBoundaryConditions [];
        for(int a=0;a<element.length; a++){
            tableBoundaryConditions = new boolean[4];
            if(element[a].tableGlobalY[0] == 0){ tableBoundaryConditions[0] = true; } else tableBoundaryConditions[0] = false;
            if(element[a].tableGlobalX[1] == element[element.length-1].tableGlobalX[1]){ tableBoundaryConditions[1] = true; } else tableBoundaryConditions[1] = false;
            if(element[a].tableGlobalY[2] == element[element.length-1].tableGlobalY[2]){ tableBoundaryConditions[2] = true; } else tableBoundaryConditions[2] = false;
            if(element[a].tableGlobalX[3] == 0){ tableBoundaryConditions[3] = true; } else tableBoundaryConditions[3] = false;
            element[a].tableBoundaryConditions = tableBoundaryConditions;
        }
    }

    void agregateMatrixHandC(){
        for (int i = 0; i <nE; i++)
        {
                for (int k = 0; k < 4; k++)
                {
                    for (int l = 0; l <4 ; l++)
                    {
                        int indexOuter = element[i].idArray[k]-1;
                        int indexInner = element[i].idArray[l]-1;


                        globalMatrixH[indexOuter][indexInner] += element[i].tableMatrixH[l][k];
                        globalMatrixC[indexOuter][indexInner] += element[i].tableMatrixC[l][k];
                        //globalVectorP[indexOuter][indexInner] += element[i].tableVectorP[l][k];
                    }
                }
        }
    }
    void agregateVectorP() {
        for (int i = 0; i <nE; i++)
        {
            for (int k = 0; k < 4; k++)
            {
                for (int l = 0; l <4 ; l++)
                {
                    int indexOuter = element[i].idArray[k]-1;
                    int indexInner = element[i].idArray[l]-1;
                    globalVectorP[indexOuter][indexInner] += element[i].tableVectorP[l][k];
                }
            }
        }
    }


    public void calucalteMatrixIteration(){
        agregateVectorP();

        double vectorP[] = new double[globalVectorP.length];
        for (int x = 0; x < globalMatrixH.length; x++) {
            tableTemperature[x] = initialTemp;
        }

        double tmpIteration = simulationStepTime;
        double tempMatrixH[][] = new double[nH*nL][nH*nL];
        double tempGlobalVectorP[][] = new double[nH*nL][nH*nL];

        for(int a=0;a<simulationTime/simulationStepTime;a++) {

            for (int x = 0; x < globalMatrixH.length; x++) {
                for (int y = 0; y < globalMatrixH.length; y++) {
                    tempMatrixH[x][y] = (globalMatrixH[x][y] + (globalMatrixC[x][y] / simulationStepTime));
                }
            }

           // System.out.println("Vector P: ");

                for (int x = 0; x < globalVectorP.length; x++) {
                    vectorP[x] = 0;
                    //System.out.print(tableTemperature[x] + " ");
                    for (int y = 0; y < globalVectorP.length; y++) {

                        tempGlobalVectorP[x][y] = globalVectorP[x][y] + (globalMatrixC[x][y] / simulationStepTime) * tableTemperature[y];
                        vectorP[x] += tempGlobalVectorP[x][y];
                    }
                    //System.out.print(vectorP[x] + " ");
                }

            double m,s;
            double tempGlobalH[][] = new double[nH*nL][nH*nL+1];
            int n = nH*nL;

            //Eliminacja Gaussa

            for (int x = 0; x < nH*nL; x++) {
                for (int y = 0; y < nH*nL+1; y++) {
                    if(y == nH*nL ) {
                        tempGlobalH[x][y] = vectorP[x];
                    }
                    else tempGlobalH[x][y] = tempMatrixH[x][y];
                }
            }
            for(int i = 0; i < n - 1; i++)
            {
                for(int j = i + 1; j < n; j++)
                {
                    m = -tempGlobalH[j][i] / tempGlobalH[i][i];
                    for(int k = i + 1; k <= n; k++)
                        tempGlobalH[j][k] += m * tempGlobalH[i][k];
                }
            }
            for(int i = n - 1; i >= 0; i--)
            {
                s = tempGlobalH[i][n];
                for(int j = n - 1; j >= i + 1; j--)
                    s -= tempGlobalH[i][j] * vectorP[j];
                vectorP[i] = s / tempGlobalH[i][i];
                tableTemperature[i] = vectorP[i];
            }
//            System.out.println();
//            for (int x = 0; x < tableTemperature.length; x++) {
//                System.out.print(tableTemperature[x]+" ");
//            }
         // System.out.println();
            double minTemp=0,maxTemp=0;
            for(int x=0;x<tableTemperature.length;x++){
                if(x==0) {minTemp = tableTemperature[x];maxTemp = tableTemperature[x];}
                if(tableTemperature[x]>maxTemp) maxTemp = tableTemperature[x];
                if(tableTemperature[x]<minTemp) minTemp = tableTemperature[x];
            }

            System.out.println("Time :"+tmpIteration + " Min : " + minTemp + " Max : " + maxTemp);
            tmpIteration +=simulationStepTime;

            //System.out.println("\n");

        }
    }

    public void printGlobalMatrix() {
          System.out.println("Matrix Globalna H");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(globalMatrixH[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
        System.out.println("Matrix Globalna C");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(globalMatrixC[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public void printCoordElement(){
        for(int a=0; a<nE; a++ ) {
            System.out.print("Element: " + (a+1) + "  ");
            element[a].printElement();
            element[a].printCoord();
        }
    }
    public void printElement(){
        for(int i=0;i<element.length;i++){
            System.out.print("Element: " + (i+1) + "  ");
            element[i].printElement();
        }
    }
    public void printNode(){
        for(int i=0;i<node.length;++i){
            System.out.printf("Wspolrzedne wezla: "+ (i+1) + " ");
            node[i].printNode();
        }
    }

}









