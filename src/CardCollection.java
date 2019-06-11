package flashcards;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CardCollection {

    // == fields ==

    private Map<String, String> cardToDefinition = new LinkedHashMap<>();
    private Map<String, String> definitionToCard= new LinkedHashMap<>();
    private Map<String, Integer>  mistakeCount = new LinkedHashMap<>();
    private Scanner scan = new Scanner(System.in);
    private File file;
    private List<String> retList;
    private int loadCount = 0;
    private int saveCount = 0;
    String temp;

    // == getters and setters ==

    public Map<String, String> getCardToDefinition() {
        return cardToDefinition;
    }

    public void setCardToDefinition(Map<String, String> cardToDefinition) {
        this.cardToDefinition = cardToDefinition;
    }

    public Map<String, String> getDefinitionToCard() {
        return definitionToCard;
    }

    public void setDefinitionToCard(Map<String, String> definitionToCard) {
        this.definitionToCard = definitionToCard;
    }

    // == methods ==
    public List<String> add(String card, String definition){
        String temp;
        retList = new ArrayList<>();

        if(!cardToDefinition.containsKey(card)){
            if(cardToDefinition.containsValue(definition)) {
                temp = cardToDefinition.get(card);
                cardToDefinition.remove(card);
                definitionToCard.remove(temp);
                mistakeCount.remove(card);
            }
                cardToDefinition.put(card, definition);
                definitionToCard.put(definition, card);
                mistakeCount.put(card, 0);
                System.out.println("The pair (" + card + "\":\"" + definition + "\") is added.");
                retList.add("The pair (" + card + "\":\"" + definition + "\") is added.");
        }else{
            System.out.println("You can't add:" + card + ": it's already in the database.");
            retList.add("You can't add:" + card + ": it's already in the database.");
        }
        return retList;
    }

    public List<String> remove(String card){
        retList = new ArrayList<>();
        if(cardToDefinition.containsKey(card)){
            temp = cardToDefinition.get(card);
            cardToDefinition.remove(card);
            definitionToCard.remove(temp);
            mistakeCount.remove(card);
            System.out.println("The card (" + card + "\") is removed.");
            retList.add("The card (" + card + "\") is removed.");
        }else{
            System.out.println("Can't remove \"" + card + "\": there is no such card.");
            retList.add("Can't remove \"" + card + "\": there is no such card.");
        }
        return retList;
    }

    public List<String> ask(int guesses){
        retList = new ArrayList<>();
        if((guesses > 0) && (cardToDefinition.size() > 0)) {
        String[] keys = cardToDefinition.keySet().toArray(new String[cardToDefinition.size()]);
        Random rand = new Random();
        int pointer, b;

            for (int i = 0; i < guesses; i++) {

                if (cardToDefinition.size() == 1) {
                    pointer = 0;
                }else {
                    pointer = rand.nextInt(cardToDefinition.size());
                }
                System.out.println("Print the definition of \"" + keys[pointer] + "\":");
                retList.add("Print the definition of \"" + keys[pointer] + "\":");
                temp = scan.nextLine();
                retList.add(temp);
                if (cardToDefinition.get(keys[pointer]).equals(temp)) {
                    System.out.print("Correct answer.");
                    retList.add("Correct answer.");
                } else if (definitionToCard.containsKey(temp)) {
                    b = mistakeCount.get(keys[pointer]);
                    mistakeCount.remove(keys[pointer]);
                    b++;
                    mistakeCount.put(keys[pointer], b);
                    System.out.print("Wrong answer (the correct one is \"" + cardToDefinition.get(keys[pointer])+
                            "\" ,you've just written a definition of \"" +
                            definitionToCard.get(temp) + "\" card.) ");
                    retList.add("Wrong answer (the correct one is \"" + cardToDefinition.get(keys[pointer])+
                            "\" ,you've just written a definition of \"" +
                            definitionToCard.get(temp) + "\" card.) ");
                } else {
                    b = mistakeCount.get(keys[pointer]);
                    mistakeCount.remove(keys[pointer]);
                    b++;
                    mistakeCount.put(keys[pointer], b);
                    System.out.print("Wrong answer(the correct one is \"" + cardToDefinition.get(keys[pointer]) +"\"). ");
                    retList.add("Wrong answer(the correct one is \"" + cardToDefinition.get(keys[pointer]) +"\"). ");
                }
            }
        }
        System.out.println();
        return retList;
    }

    public List<String> importFile(String name){
        retList = new ArrayList<>();
        file = new File(name);
        int i = 0;
        String card, definition;
        try(Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine()){
                card = scanner.nextLine();
                if(!cardToDefinition.containsKey(card)) {
                    definition = scanner.nextLine();
                    if(!definitionToCard.containsKey(definition)) {
                        cardToDefinition.remove(definitionToCard.get(definition));
                        definitionToCard.remove(definition);
                        mistakeCount.remove(card);
                        cardToDefinition.put(card, definition);
                        definitionToCard.put(definition, card);
                        mistakeCount.put(card, Integer.parseInt(scanner.nextLine()));
                    }
                }else{
                    scanner.nextLine();
                    scanner.nextLine();
                }
                i++;
            }
        }catch(IOException e){
            System.out.println("Error, cannot open file!");
            retList.add("Error, cannot open file!");
        }
        System.out.println((i) + " cards have been loaded.");
        retList.add((i)  + " cards have been loaded.");
        return retList;
    }

    public List<String> exportFile(String name){
        file = new File(name);
        retList = new ArrayList<>();
        //int i = 0;
        try(PrintWriter writer = new PrintWriter(file)){
            for(Map.Entry entry: cardToDefinition.entrySet()){
                writer.println(entry.getKey());
                writer.println(entry.getValue());
                writer.println(mistakeCount.get(entry.getKey()));
                saveCount++;
            }
        }catch(IOException e){
            System.out.println("Error, cannot open file!");
            retList.add("Error, cannot open file!");
        }
        System.out.println(saveCount + " cards have been saved.");
        retList.add(saveCount + " cards have been saved.");
        return retList;
    }

    public void logFile(String name, List<String> logList){
        file = new File(name);
        try(PrintWriter writer = new PrintWriter(file)){
            for(String s: logList){
                writer.println(s);
            }

        }catch(IOException e){
            System.out.println("Error, cannot open file!");
        }
    }

    public List<String> showHardest(){
        retList = new ArrayList<>();
        int most = 0;
        String tempKey = "";
        for(Map.Entry<String, Integer> entry: mistakeCount.entrySet()){
            if(entry.getValue() > most){
                most = entry.getValue();
                tempKey = entry.getKey();
            }
        }
        System.out.println("The hardest card is " + tempKey +". You have " + most + " errors answering it.");
        retList.add("The hardest card is " + tempKey +". You have " + most + " errors answering it.");
        return retList;
    }

    public void resetStats(){
        for(Map.Entry<String, Integer> entry: mistakeCount.entrySet()){
           entry.setValue(0);
        }
    }
}
