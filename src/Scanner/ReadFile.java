package Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
      private  double H,L;
      private  int nH,nL,conductivity,speecificHeat,density,alfa,ambientTemp,initialTemp,simulationTime,simulationStepTime;

    public ReadFile() throws FileNotFoundException {
        File file=new File("date2.txt");
        Scanner scanner=new Scanner(file);
        this.H=scanner.nextDouble();
        this.L=scanner.nextDouble();
        this.nH=scanner.nextInt();
        this.nL=scanner.nextInt();
        this.conductivity=scanner.nextInt();
        this.speecificHeat=scanner.nextInt();
        this.density=scanner.nextInt();
        this.alfa=scanner.nextInt();
        this.ambientTemp=scanner.nextInt();
        this.initialTemp=scanner.nextInt();
        this.simulationTime =scanner.nextInt();
        this.simulationStepTime =scanner.nextInt();
        System.out.println("H = " + H + " L = "+L + " nH = "+ nH + " nL = " + nL+ " conductivity = "+ conductivity+ " speecificHeat = " + speecificHeat+" density = "+ density+ " convection = " + alfa+ " ambientTemp = " + ambientTemp+ " initialTemp = "+ initialTemp +
        " simulationTime = " + simulationTime + " simulationStepTime = " + simulationStepTime);
        }

    public double getH() {
        return H;
    }

    public double getL() {
        return L;
    }

    public int getnH() {
        return nH;
    }

    public int getnL() {
        return nL;
    }

    public int getConductivity() {
        return conductivity;
    }

    public int getDensity() {
        return density;
    }

    public int getSpeecificHeat() {
        return speecificHeat;
    }

    public int getAlfa() {
        return alfa;
    }

    public int getAmbientTemp() {
        return ambientTemp;
    }

    public int getInitialTemp() {
        return initialTemp;
    }

    public int getSimulationStepTime() {
        return simulationStepTime;
    }

    public int getSimulationTime() {
        return simulationTime;
    }
}
