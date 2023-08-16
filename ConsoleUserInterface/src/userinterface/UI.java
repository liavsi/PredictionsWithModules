package userinterface;

import engine.SimulationOutcome;
import engine.impl.EngineImpl;

import java.util.Scanner;

public class UI {
    private static final String JAXB_XML_PACKAGE_NAME = "schema.generated";
    private static final String XML_FILE_PATH = "resources/master-ex1.xml";
    public static void main(String[] args) {
        EngineImpl currEngine = new EngineImpl();
        System.out.println("please enter your choice:");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                currEngine.readWorldFromXml(XML_FILE_PATH, JAXB_XML_PACKAGE_NAME);
                break;
        }
//            case 2:
//                showDataToUser(currEngine.getWorldDTO());
//        Data Transfer Object
//                break;
//            case 3:
//                showSimulationIdNumber(currEngine.runNewSimulation());
//                break;
//            case 4:
//                int wantedSimulationNumber = getSimulationNumberFromUser();
//                showSimulationData(currEngine.getPastSimulationDTO(wantedSimulationNumber));
//                break;
//            case 5:
//                System.exit(1);
//                break;
//            default:
//                throw new IllegalArgumentException(choice + "is not a valid choice");
//        }
       // currEngine.readWorldFromXml(XML_FILE_PATH, JAXB_XML_PACKAGE_NAME);
        System.out.println("ended");
      //  System.out.println(currEngine.toString());
    }

    private static void showSimulationData(SimulationOutcome newSimulation) {

    }

}