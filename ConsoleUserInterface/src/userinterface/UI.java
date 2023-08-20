package userinterface;

import DTOManager.impl.*;
import engine.api.Engine;
import engine.impl.EngineImpl;
import userinterface.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UI {
    private static final String JAXB_XML_PACKAGE_NAME = "schema.generated";
    private static final String XML_FILE_PATH = "resources/ex1-cigarets.xml";
    private static final String FAILED_WHILE_RUNNING = "Something went wrong during this Action..\n";
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
                    //String XML_File_Path = getXmlPathFromUser(scanner);
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
                        runningSimulation(scanner, currEngine);
                        stage = Stage.AFTER_SIMULATION;
                        System.out.println(SUCCEED_DOING_SOMETHING);
                    }
                    catch (Exception e) {
                        System.out.println(FAILED_WHILE_RUNNING + e.getMessage());
                    }
                    break;
                case 4:
                    showSimulationDataWithChoose((currEngine.getPastSimulationMapDTO()),currEngine.getWorldDTO(), scanner);
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
            SimulationOutcomeDTO simulationOutcomeDTO = currEngine.runNewSimulation(propertyNameToValueAsString);
            int i = 1;
            System.out.println("Environment Vars:\n");
            for (PropertyDefinitionDTO propertyDefinitionDTO: currEngine.getWorldDTO().getEnvPropertiesDefinitionDTO()){
                System.out.println(i + ") ");
                showPropertyDefinitionDataToUser(propertyDefinitionDTO);
            }
            showEndSimulationDataToUser(simulationOutcomeDTO);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    private static boolean showEnvironmentVarsToUser(List<PropertyDefinitionDTO> envars,Map<String, Object> propertyNameToValueAsString, Scanner scanner) {
        int counter = 1;
        if(envars.isEmpty())
        {return true;}
        System.out.println("Select the number of environment variable you want to set: (and -1 if not)");
        for (PropertyDefinitionDTO env: envars) {
            System.out.println(counter + ") " + env.getName());
            counter++;
        }
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice != -1) {
            try {
                PropertyDefinitionDTO chosenProperty = envars.get(choice-1);
                envars.remove(chosenProperty);
                System.out.println("Please enter value that is suitable for: ");
                showPropertyDefinitionDataToUser(chosenProperty);
                Object valueAsString = scanner.nextLine();
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
    private static void showSimulation(SimulationOutcomeDTO simulationOutcomeDTO, WorldDTO worldDTO,Scanner scanner) {

        while (true) {
            System.out.println("1. Display entity quantities");
            System.out.println("2. Display entity properties histogram");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                displayEntityQuantities(simulationOutcomeDTO.getEntityInstancDTOS());
            } else if (choice == 2) {
                displayEntityPropertiesHistogram(simulationOutcomeDTO.getEntityInstancDTOS(),worldDTO, scanner);
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
    private static void displayEntityPropertiesHistogram(Map<Integer, EntityInstanceManagerDTO> entityInstanceDTOS, WorldDTO worldDTO, Scanner scanner) {
        System.out.println("Select an Entity to display its Properties:");

        List<EntityDefinitionDTO> definitionDTOS = new ArrayList<>(worldDTO.getNameToEntityDefinitionDTO().values());

        IntStream.range(0, definitionDTOS.size())
                .mapToObj(i -> (i + 1) + ". " + definitionDTOS.get(i).getName())
                .forEach(System.out::println);

        int entityChoice = Integer.parseInt(scanner.nextLine());
        EntityDefinitionDTO entityDefinitionDTO = definitionDTOS.get(entityChoice - 1);

        System.out.println("Select a Property to display its histogram:");

        List<PropertyDefinitionDTO> propertyDefinitionDTOs = entityDefinitionDTO.getPropertiesDTO();

        IntStream.range(0, propertyDefinitionDTOs.size())
                .mapToObj(i -> (i + 1) + ". " + propertyDefinitionDTOs.get(i).getName())
                .forEach(System.out::println);

        int propertyChoice = Integer.parseInt(scanner.nextLine());
        PropertyDefinitionDTO selectedProperty = propertyDefinitionDTOs.get(propertyChoice - 1);

        int counter = countEntitiesWithProperty(entityInstanceDTOS, selectedProperty);

        System.out.println(selectedProperty.getName() + " appears in " + counter + " entities after the simulation");
    }

    private static int countEntitiesWithProperty(Map<Integer, EntityInstanceManagerDTO> entityInstanceDTOS, PropertyDefinitionDTO selectedProperty) {
        int counter = 0;
        for (EntityInstanceDTO entityInstanceDTO : entityInstanceDTOS.get(1).getInstancesDTO().values()) {
            if (entityInstanceDTO.getProperties().values().stream().anyMatch(propertyInstanceDTO -> propertyInstanceDTO.getPropertyDefinitionDTO().getName().equals(selectedProperty.getName()))) {
                counter++;
            }
        }
        return counter;
    }
    private static void showSimulationDataWithChoose(Map<Integer, SimulationOutcomeDTO> simulationOutcomeDTOMap, WorldDTO worldDTO, Scanner scanner) {
        System.out.println("Please choose simulation to show data about:");
        simulationOutcomeDTOMap.forEach((id, simulationOutComeDTO) -> {
            System.out.printf("Simulation Id: %d Run Date: %s%n", id, simulationOutComeDTO.getRunDate());
        });
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (simulationOutcomeDTOMap.containsKey(choice)) {
                showSimulation(simulationOutcomeDTOMap.get(choice), worldDTO, scanner);
            } else {
                throw new IllegalArgumentException(choice + "is not a valid simulation key");
            }
        } catch (Exception e)
        { throw new IllegalArgumentException("illegal number format entered");}


    }
    public static void displayEntityQuantities(Map<Integer, EntityInstanceManagerDTO> entities) {
        EntityInstanceManagerDTO before = entities.get(0);
        EntityInstanceManagerDTO after = entities.get(1);

        // Collect entity counts before the simulation
        Map<String, List<EntityInstanceDTO>> beforeCounts = before.getInstancesDTO().values()
                .stream()
                .collect(Collectors.groupingBy(EntityInstanceDTO::getName));

        // Collect entity counts after the simulation
        Map<String, List<EntityInstanceDTO>> afterCounts = after.getInstancesDTO().values()
                .stream()
                .collect(Collectors.groupingBy(EntityInstanceDTO::getName));

        // Combine entity counts from before and after simulation
        Set<String> allEntityNames = new HashSet<>();
        allEntityNames.addAll(beforeCounts.keySet());
        allEntityNames.addAll(afterCounts.keySet());

        for (String entityName : allEntityNames) {
            List<EntityInstanceDTO> beforeInstances = beforeCounts.getOrDefault(entityName, new ArrayList<>());
            List<EntityInstanceDTO> afterInstances = afterCounts.getOrDefault(entityName, new ArrayList<>());

            System.out.println("Entity Name: " + entityName);
            System.out.println("Before running the simulation: Count: " + beforeInstances.size());
            System.out.println("After running the simulation: Count: " + afterInstances.size());
        }
    }
    private static void showWorldDataToUser(WorldDTO worldDTO) {
        System.out.println("Loaded World Details:");
        Map<String, EntityDefinitionDTO> entityDefinitionDTOMap = worldDTO.getNameToEntityDefinitionDTO();
        System.out.println("Entities:");

        int entityNumber = 1;
        for (EntityDefinitionDTO entityDefinitionDTO : entityDefinitionDTOMap.values()) {
            System.out.println("Entity number " + entityNumber + ":");
            System.out.println("--------------");
            showEntityDefinitionDataToUser(entityDefinitionDTO);
            entityNumber++;
        }

        System.out.println("Rules:");
        int ruleNumber = 1;
        for (RuleDTO ruleDTO : worldDTO.getRulesDTO()) {
            System.out.println("Rule number " + ruleNumber + ":");
            System.out.println("--------------");
            showRuleDataToUser(ruleDTO);
            ruleNumber++;
        }

        showTerminationDataToUser(worldDTO.getTerminationDTO());
    }
    private static void showTerminationDataToUser(TerminationDTO terminationDTO) {
        System.out.println("End conditions of the simulation:");
        System.out.println("---------------------------------");
        Integer ticks = terminationDTO.getTicks();
        Integer seconds = terminationDTO.getSecondsToPast();

        if (ticks != null) {
            System.out.println("- The simulation will end after " + ticks + " ticks");
        }

        if (seconds != null) {
            System.out.println("- The simulation will end after " + seconds + " seconds");
        }
    }
    private static void showRuleDataToUser(RuleDTO ruleDTO){
        System.out.println("- Name: " + ruleDTO.getName());
        System.out.println("- This rule is activated every " + ruleDTO.getTicks() + " ticks");
        System.out.println("  with a probability of " + ruleDTO.getProbability());

        int numOfActions = ruleDTO.getActionsNames().size();
        System.out.println("- This rule performs " + numOfActions + " actions");
        System.out.println("- The actions are:");

        int actionNumber = 1;
        for (String actionName : ruleDTO.getActionsNames()) {
            if (actionNumber == numOfActions) {
                System.out.println(actionName);
            } else {
                System.out.print(actionName + ", ");
            }
            actionNumber++;
        }
    }
    private static void showEntityDefinitionDataToUser(EntityDefinitionDTO entityDefinitionDTO){
        System.out.println("Name: " + entityDefinitionDTO.getName());
        System.out.println("Amount in population: " + entityDefinitionDTO.getPopulation());
        System.out.println(entityDefinitionDTO.getName() + "'s Properties:");

        int propertyNumber = 1;
        for (PropertyDefinitionDTO propertyDefinitionDTO : entityDefinitionDTO.getPropertiesDTO()) {
            System.out.println(propertyNumber + ")");
            showPropertyDefinitionDataToUser(propertyDefinitionDTO);
            propertyNumber++;
        }
    }
    private static void showPropertyDefinitionDataToUser(PropertyDefinitionDTO propertyDefinitionDTO) {
        System.out.println("Name: " + propertyDefinitionDTO.getName());
        System.out.println("Type: " + propertyDefinitionDTO.getPropertyType());

        if (propertyDefinitionDTO.getFrom() != null && propertyDefinitionDTO.getTo() != null) {
            System.out.println("Property range value: " + propertyDefinitionDTO.getFrom() + " - " + propertyDefinitionDTO.getTo());
        }

        System.out.println("The property value is " + (propertyDefinitionDTO.getRandomInitializer() ? "initialized randomly" : "not initialized randomly"));
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


}