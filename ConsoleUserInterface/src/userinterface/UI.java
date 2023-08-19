package userinterface;

import DTOManager.impl.*;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.impl.EngineImpl;
import userinterface.stage.Stage;

import java.util.*;

public class UI {
    private static final String JAXB_XML_PACKAGE_NAME = "schema.generated";
    private static final String XML_FILE_PATH = "resources/master-ex1.xml";

    private static final String FAILED_WHILE_RUNNING = "Something went wrong during this Action..";
    private static final String SUCCEED_DOING_SOMETHING = "Action has been performed successfully";
    public static void main(String[] args) {
        Engine currEngine = new EngineImpl();
        Scanner scanner = new Scanner(System.in);
        List<Integer> simulationIds = new ArrayList<>();
        boolean isRunning = true;
        Stage stage = Stage.FILE_NOT_LOADED;
        int choice;
        while (isRunning) {
            choice = stage.runMenu(scanner);
            switch (choice) {
                case 1:
                    String XML_File_Path = getXmlPathFromUser(scanner);
                    try {
                        currEngine.readWorldFromXml(XML_FILE_PATH, JAXB_XML_PACKAGE_NAME);
                        stage = Stage.FILE_LOADED;
                        System.out.println(SUCCEED_DOING_SOMETHING);
                    }
                    catch (Exception e) {
                        System.out.println(FAILED_WHILE_RUNNING + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        showWorldDataToUser(currEngine.getWorldDTO());
                        System.out.println(SUCCEED_DOING_SOMETHING);
                    }
                    catch (Exception e) {
                        System.out.println(FAILED_WHILE_RUNNING +e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        runningSimulation(scanner,currEngine);
                        stage = Stage.AFTER_SIMULATION;
                        System.out.println(SUCCEED_DOING_SOMETHING);
                    }
                    catch (Exception e) {
                        System.out.println(FAILED_WHILE_RUNNING + e.getMessage());
                    }
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

    private static String getXmlPathFromUser(Scanner scanner) {
        boolean validXml = false;
        String xmlPath = "";
        while (!validXml) {
            System.out.print("Please enter valid xml file path: ");
            xmlPath = scanner.nextLine();
            if (!xmlPath.endsWith(".xml")) {
                System.out.println("this is not a valid xml path.. make sure the file suffix is .xml");
            }
            else {
                validXml = true;
            }
        }
        return xmlPath;
    }

    private static Optional<Integer> getSimulationNumberFromUser(List<Integer> simulationIds, Scanner scanner) {
        System.out.println("Please choose simulation to show:\n");
        simulationIds.forEach((id) -> System.out.println(id + "\n"));
        Integer choice =Integer.parseInt(scanner.nextLine());
        if( choice > simulationIds.stream().max(Integer::compare).get() || choice < simulationIds.stream().min(Integer::compare).get()) {
            choice = null;
        }
        return Optional.ofNullable(choice);
    }
    private static void runningSimulation(Scanner scanner, Engine currEngine) {
        boolean isDoneSettingEnvVars = false;
        Map<String, Object> propertyNameToValueAsString = new HashMap<>();
        List<PropertyDefinitionDTO> envars = currEngine.getWorldDTO().getEnvPropertiesDefinitionDTO();
        while(!isDoneSettingEnvVars) {
            isDoneSettingEnvVars = showEnvironmentVarsToUser(envars,propertyNameToValueAsString, scanner);
        }
        try{
            int i = 1;
            System.out.println("Environment Vars:\n");
            for (PropertyDefinitionDTO propertyDefinitionDTO: currEngine.getWorldDTO().getEnvPropertiesDefinitionDTO()){
                System.out.println(i + ") ");
                showPropertyDataToUser(propertyDefinitionDTO);
            }
            SimulationOutcomeDTO simulationOutcomeDTO = currEngine.runNewSimulation(propertyNameToValueAsString);
            showEndSimulationDataToUser(simulationOutcomeDTO);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
    private static void showEndSimulationDataToUser(SimulationOutcomeDTO simulationOutcomeDTO){
        System.out.println("------The simulation is over------\n");
        System.out.println("ID number: " + simulationOutcomeDTO.getId() + "\n");
        if(simulationOutcomeDTO.getTerminationDTO().isTicksTerminate()){
            System.out.println("The number of ticks set for the simulation has passed\n");
        }
        if (simulationOutcomeDTO.getTerminationDTO().isSecondsTerminate()){
            System.out.println("The seconds set for the simulation have passed\n");
        }
    }
    private static boolean showEnvironmentVarsToUser(List<PropertyDefinitionDTO> envars,Map<String, Object> propertyNameToValueAsString, Scanner scanner) {
        int counter = 1;
        if(envars.size() < 1)
        {return true;}
        System.out.println("Select the number of environment variable you want to set: (and -1 if not)");
        for (PropertyDefinitionDTO env: envars) {
            System.out.println(counter + ") " + env.getName());
            counter++;
        }
        int choice = scanner.nextInt();
        if (choice != -1) {
            try {
                PropertyDefinitionDTO chosenProperty = envars.get(choice-1);
                envars.remove(chosenProperty);
                System.out.println("Please enter value that is suitable for: ");
                showPropertyDataToUser(chosenProperty);
                Object valueAsString = scanner.nextLine();
                valueAsString = scanner.nextLine();
                //check values
                propertyNameToValueAsString.put(chosenProperty.getName(), valueAsString);
                return false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage() + "\nTry again");
            }
        }
        else {
            return true;
        }
        return true;

        // TODO: 18/08/2023 not let him change vars twice and stop when he finish
    }
    private static void showSimulationIdNumber(int simulationId) {

    }
    private static void showWorldDataToUser(WorldDTO worldDTO) {
        int i=1;
        System.out.println("Simulation Details:\n");
        Map<String, EntityDefinitionDTO> entityDefinitionDTOMap = worldDTO.getNameToEntityDefinitionDTO();
        System.out.println("The Entities:\n");
        for (EntityDefinitionDTO entityDefinitionDTO:entityDefinitionDTOMap.values()){
            System.out.printf("Entity number " + i + "\n");
            System.out.println("--------------\n");
            showEntityDataToUser(entityDefinitionDTO);
            i++;
        }
        System.out.println("Rules:\n");
        i=1;
        for (RuleDTO ruleDTO: worldDTO.getRulesDTO()){
            System.out.println("Rule number " + i + ":\n");
            System.out.println("--------------\n");
            showRuleDataToUser(ruleDTO);
            i++;
        }
        showTerminationDataToUser(worldDTO.getTerminationDTO());
    }
    private static void showTerminationDataToUser(TerminationDTO terminationDTO){
        System.out.println("End conditions of the simulation: \n");
        System.out.println("---------------------------------\n");
        Integer ticks = terminationDTO.getTicks();
        Integer seconds = terminationDTO.getSecondsToPast();
        if (ticks != null){
            System.out.println("- The simulation will end after " + ticks + " ticks");
        }
        if (seconds != null){
            System.out.println("- The simulation will end after " + seconds + " seconds");
        }
    }
    private static void showRuleDataToUser(RuleDTO ruleDTO){
        int i = 1;
        System.out.println("- Name: " + ruleDTO.getName());
        System.out.println("- This rule in activated every " + ruleDTO.getTicks() + " ticks ");
        System.out.println("with a probability of " + ruleDTO.getProbability() + "\n");
        int numOfActions = ruleDTO.getActionsNames().size();
        System.out.println("- This rule performs " + numOfActions + " actions\n");
        System.out.println("- The actions are:\n");
        for (String actionName: ruleDTO.getActionsNames()){
            if (i == numOfActions){
                System.out.println(actionName + "\n");
            }
            else {
                System.out.println(actionName + ",");
            }
            i++;
        }
    }
    private static void showEntityDataToUser(EntityDefinitionDTO entityDefinitionDTO){
        int i = 1;
        System.out.println("Name: " + entityDefinitionDTO.getName() + "\n");
        System.out.println("Amount in population: " + entityDefinitionDTO.getPopulation() + "\n");
        System.out.println(entityDefinitionDTO.getName() + "'s Properties:\n");
        for (PropertyDefinitionDTO propertyDefinitionDTO: entityDefinitionDTO.getPropertiesDTO()){
            System.out.println(i+")\n");
            showPropertyDataToUser(propertyDefinitionDTO);
            i++;
        }
    }
    private static void showPropertyDataToUser(PropertyDefinitionDTO propertyDefinitionDTO){
        System.out.println("Name:" + propertyDefinitionDTO.getName());
        System.out.println("Type: " + propertyDefinitionDTO.getPropertyType());
        if(propertyDefinitionDTO.getFrom() != null && propertyDefinitionDTO.getTo() != null){
            System.out.println("Property range value: " + propertyDefinitionDTO.getFrom() + " - " + propertyDefinitionDTO.getTo());
        }
        System.out.println("The property value " + (propertyDefinitionDTO.getRandomInitializer()? "is":"isn't") + " initialized randomly");
     }
    private static void showSimulationData(SimulationOutcome newSimulation) {

    }



}