package userinterface.stage;

import java.util.Scanner;

public enum Stage {
    FILE_NOT_LOADED {
        @Override
        public int runMenu(Scanner scanner) {
            System.out.println("Please enter full path of XML file: ");

            return 1;
        }
    }, FILE_LOADED{
        @Override
        public int runMenu(Scanner scanner) {
            return 0;
        }
    },AFTER_SIMULATION {
        @Override
        public int runMenu(Scanner scanner) {
            return 0;
        }
    }, BEFORE_SIMULATION{
        @Override
        public int runMenu(Scanner scanner) {
            return 0;
        }
    };
    public abstract int runMenu(Scanner scanner);
}
