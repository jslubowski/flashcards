package flashcards;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        CardCollection collection = new CardCollection();
        boolean exit = false;
        String choice, card, definition, file;
        Integer guesses;
        boolean flag = false;
        List<String> log = new ArrayList<>();
        List<String> temp;
        if(args.length > 0){
        if (args[0].equals("-import")) {
            collection.importFile(args[1]);
            System.out.println("loaded");
            flag = true;
        }
        }

        while (!exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            choice = scan.nextLine();
            log.add(choice);
            switch (choice) {
                case "add":
                    System.out.println("The card:");
                    log.add("The card:");
                    card = scan.nextLine();
                    log.add(card);
                    System.out.println("The definition:");
                    log.add("The definition:");
                    definition = scan.nextLine();
                    log.add(definition);
                    temp = collection.add(card, definition);
                    for (String s : temp) {
                        log.add(s);
                    }
                    break;
                case "remove":
                    System.out.println("The card:");
                    log.add("The card:");
                    card = scan.nextLine();
                    log.add(card);
                    temp = collection.remove(card);
                    for (String s : temp) {
                        log.add(s);
                    }
                    break;
                case "import":
                    System.out.println("File name:");
                    log.add("File name:");
                    file = scan.nextLine();
                    log.add(file);
                    temp = collection.importFile(file);
                    for (String s : temp) {
                        log.add(s);
                    }
                    break;
                case "export":
                    System.out.println("File name:");
                    log.add("File name:");
                    file = scan.nextLine();
                    log.add(file);
                    temp = collection.exportFile(file);
                    for (String s : temp) {
                        log.add(s);
                    }
                    break;
                case "ask":
                    System.out.println("How many times to ask?");
                    log.add("How many times to ask?");
                    guesses = Integer.parseInt(scan.nextLine());
                    log.add(guesses.toString());
                    temp = collection.ask(guesses);
                    for (String s : temp) {
                        log.add(s);
                    }
                    break;
                case "log":
                    System.out.println("File name:");
                    log.add("File name:");
                    file = scan.nextLine();
                    log.add(file);
                    collection.logFile(file, log);
                    System.out.println("The file has been saved.");
                    log.add("The file has been saved.");
                    break;
                case "hardest card":
                    temp = collection.showHardest();
                    for (String s : temp) {
                        log.add(s);
                    }
                    break;
                case "reset stats":
                    collection.resetStats();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    exit = true;
            }

        }
        if(args.length > 0) {
            if (flag = false) {
                if (args[0].equals("-export")) {
                    collection.exportFile(args[1]);
                }
            } else {
                if(args.length > 2) {
                    if (args[2].equals("-export")) {
                        collection.exportFile(args[3]);
                    }
                }

            }
        }
    }
}
