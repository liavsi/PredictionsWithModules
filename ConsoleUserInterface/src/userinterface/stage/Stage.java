package userinterface.stage;

import java.util.Scanner;

public enum Stage {
    FILE_NOT_LOADED {
        @Override
        public int runMenu(Scanner scanner) {
            System.out.println(separator);
            System.out.println(loadXmlMessage);
            System.out.println(exitMessage);
            int choice = tryGetNumberFromUser(scanner);
            return choice == 1 ? 1 : 5;
        }
    },
    FILE_LOADED{
        @Override
        public int runMenu(Scanner scanner) {
            System.out.println(separator);
            System.out.println(loadXmlMessage);
            System.out.println(showWorldMessage);
            System.out.println(runSimulationMessage);
            System.out.println(exitMessage);
            int choice = tryGetNumberFromUser(scanner);
            if(choice != 1 && choice != 2 && choice != 3) {
                choice = 5;
            }
            return choice;
        }
    },AFTER_SIMULATION {
        @Override
        public int runMenu(Scanner scanner) {
            System.out.println(separator);
            System.out.println(loadXmlMessage);
            System.out.println(showWorldMessage);
            System.out.println(runSimulationMessage);
            System.out.println(showSimulationMessage);
            System.out.println(exitMessage);
            int choice = tryGetNumberFromUser(scanner);
            if(choice != 1 && choice != 2 && choice != 3 && choice != 4) {
                choice = 5;
            }
            return choice;        }
    };

    private static final String separator = "\n--------------------------------------\n";
    private static final String errorGettingNumber = "Please Enter valid number!";
    private static final String loadXmlMessage = "Enter 1 to load xml file to the system";
    private static final String showWorldMessage = "Enter 2 to show the loaded world data";

    private static final String runSimulationMessage = "Enter 3 to run simulation with the current loaded world";

    private static final String showSimulationMessage = "Enter 4 to show past simulation outcome data";
    private static final String exitMessage = "Enter any other key to exit..";

    public Integer tryGetNumberFromUser(Scanner scanner) {
        int choice = -1;
        while (choice == -1) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errorGettingNumber);
            }
        }
        return choice;
    }

    public abstract int runMenu(Scanner scanner);
}
