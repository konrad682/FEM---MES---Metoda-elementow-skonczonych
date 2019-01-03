import java.io.FileNotFoundException;
import Grid.Grid;
import Scanner.ReadFile;

public class main {
    static public void main(String [] args) throws FileNotFoundException {

     ReadFile readFile = new Scanner.ReadFile();
     Grid grid = new Grid(readFile.getnH(),readFile.getnL(),readFile.getH(),readFile.getL(),readFile.getConductivity(),readFile.getSpeecificHeat(),readFile.getDensity(),readFile.getAlfa(),readFile.getAmbientTemp(),readFile.getInitialTemp(),readFile.getSimulationTime(),readFile.getSimulationStepTime());


    }

}
