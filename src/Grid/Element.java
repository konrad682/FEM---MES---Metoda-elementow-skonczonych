package Grid;

public class Element {

    int[] idArray = new int[4];
    public double[] tableGlobalX;
    public double[] tableGlobalY;
    public double tableMatrixH [][];
    public double tableMatrixHwithBC [][];
    public double tableMatrixC [][];
    public double tableVectorP [][];
    public boolean tableBoundaryConditions [];

    Element(int element_value, int nH){
        idArray[0] = element_value;
        idArray[1] = element_value + nH;
        idArray[2] = element_value + nH + 1;
        idArray[3] = element_value + 1;

    }
    void NodesElement(double tabx[],double taby[]){
        tableGlobalX = tabx;
        tableGlobalY = taby;
    }

    public void printElement(){
        System.out.println("Id wezlow: " + " "  + this.idArray[0] + " " + this.idArray[1] + " " + this.idArray[2] + " " + this.idArray[3]);
    }
    public void printCoord() {
        System.out.println("Wspolrzedne wezlow: ");
        for (int a = 0; a < 4; a++) {
            System.out.println("x: " + this.tableGlobalX[a] + ", y: " + this.tableGlobalY[a]);
        }
        System.out.print("\n");
    }
    public void printMatrixH() {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {

                System.out.print(tableMatrixH[a][b] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }
    public void printMatrixHwithBC() {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {

                System.out.print(tableMatrixHwithBC[a][b] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }
    public void printMatrixC() {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {

                System.out.print(tableMatrixC[a][b] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }
    public void printisAside(){
        System.out.print("Boki: " + " "  + this.tableBoundaryConditions[0] + " " + this.tableBoundaryConditions[1] + " " + this.tableBoundaryConditions[2] + " " + this.tableBoundaryConditions[3]+ "\n");
    }
}
