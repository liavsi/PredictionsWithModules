package userinterface;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.impl.EngineImpl;
import userinterface.stage.Stage;

import java.util.*;

public class UI {
    private static final String JAXB_XML_PACKAGE_NAME = "schema.generated";
    private static final String XML_FILE_PATH = "resources/master-ex1.xml";

    public static void main(String[] args) {
        Engine currEngine = new EngineImpl();
        Scanner scanner = new Scanner(System.in);
        List<Integer> simulationIds = new ArrayList<>();
        boolean isRunning = true;
        Stage stage = Stage.FILE_NOT_LOADED;

        // TODO: 18/08/2023  make sure the string from the user is ending with .xml
        while (isRunning) {
           int choice = 0;//stage.runMenu(scanner);
            switch (choice) {
                case 1:
//                String XML_File_Path = getXmlPathFromUser(scanner);
                    currEngine.readWorldFromXml(XML_FILE_PATH, JAXB_XML_PACKAGE_NAME);
                    stage = Stage.FILE_LOADED;
                    break;
                case 2:
                    showWorldDataToUser(currEngine.getWorldDTO());
                    //Data Transfer Object
                    break;
                case 3:

                    stage = Stage.AFTER_SIMULATION;
                    break;
                case 4:
                     int wantedSimulationNumber = getSimulationNumberFromUser(simulationIds, scanner).get();
                    //showSimulationData(currEngine.getPastSimulation(wantedSimulationNumber));
                    break;
                case 5:
                    isRunning = false;
                    break;
                default:
                    throw new IllegalArgumentException(choice + "is not a valid choice");
            }
        }
        System.out.println("ended main loop");
    }

    private static Optional<Integer> getSimulationNumberFromUser(List<Integer> simulationIds, Scanner scanner) {
        System.out.println("Please choose simulation to show:\n");
        simulationIds.forEach((id) -> System.out.println(id + "\n"));
        Integer choice = scanner.nextInt();
        if( choice > simulationIds.stream().max(Integer::compare).get() || choice < simulationIds.stream().min(Integer::compare).get()) {
            choice = null;
        }
        return Optional.ofNullable(choice);
    }

    private static void runningSimulation(Scanner scanner, Engine currEngine) {
        boolean isDoneSettingEnvVars = false;
        Map<String, Object> propertyNameToValueAsString = new HashMap<>();
        List<PropertyDefinitionDTO> envars = (List<PropertyDefinitionDTO>) currEngine.getWorldDTO();
        while(isDoneSettingEnvVars) {
            isDoneSettingEnvVars = showEnvironmentVarsToUser(envars,propertyNameToValueAsString, scanner);

        }
        try{
            currEngine.runNewSimulation(propertyNameToValueAsString);
            int simulationId =currEngine.runNewSimulation(propertyNameToValueAsString);
            System.out.println(simulationId);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "Something went wrong during the simulation running..");
        }
    }

    private static boolean showEnvironmentVarsToUser(List<PropertyDefinitionDTO> envars,Map<String, Object> propertyNameToValueAsString, Scanner scanner) {
        int counter = 1;
        for (PropertyDefinitionDTO env: envars) {
            System.out.println(counter + env.getName());
            counter++;
        }
        int choice = scanner.nextInt();
        if (choice != -1) {
            try {
                PropertyDefinitionDTO chosenProperty = envars.get(choice);
                System.out.println("Please enter value that is suitable for: " + chosenProperty.toString());
                Object valueAsString = scanner.nextLine();
                propertyNameToValueAsString.put(chosenProperty.getName(), valueAsString);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage() + "\nTry again");
            }
        }
        else {
            return true;
        }
        return true;


    }

    private static void showSimulationIdNumber(int simulationId) {

    }


    private static void showWorldDataToUser(WorldDTO worldDTO) {
        Map<String, EntityDefinitionDTO> entityDefinitionDTOMap = worldDTO.getNameToEntityDefinitionDTO();
//        for (EntityDefinitionDTO entityDefinitionDTO:entityDefinitionDTOMap.values()){
//
//        }
    }
    private static void showEntityDataToUser(EntityDefinitionDTO entityDefinitionDTO){

    }


    private static void showSimulationData(SimulationOutcome newSimulation) {

    }



}