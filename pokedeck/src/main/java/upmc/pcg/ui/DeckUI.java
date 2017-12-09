package upmc.pcg.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import upmc.pcg.game.Deck;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DeckUI {


    private final Scanner console = new Scanner(System.in);
    private static Deck selected_deck;
    private GameUI gUI = new GameUI();


    public void deckToJson(Deck deck){
        FileWriter fw=null;
        try {
            fw = new FileWriter("pokedeck\\data\\deck\\"+deck.getName()+".json");
        }catch(IOException e){
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(deck, fw);

        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create_deck(){
        System.out.println("Choose your deck name:");
        Deck deck =new Deck(console.nextLine());
        File f = new File("pokedeck\\data\\deck\\"+deck.getName()+".json");
        if(!f.exists() && !f.isDirectory()) {
            System.out.println("\u001B[32mDeck "+deck+" successfully created! \u001B[0m");
            this.deckToJson(deck);
            GameUI.p1.add_deck(deck);
        }
        else{
            System.out.println("\u001B[31mDeck "+deck+" already exists! Please name your deck differently! \u001B[0m");
        }

        this.gUI.print_choices();

    }

    public void edit_deck(){
        if (GameUI.p1.getDecks().isEmpty()) {
            System.out.println("There is no Deck yet, create one to start!");
        }
        else{
            System.out.println("Choose the deck you want to edit by inserting his number:");
            int index=0;

            for (Deck deck: GameUI.p1.getDecks()) {
                System.out.println((index++)+" => "+deck);
            }

            try{
                int choice = Integer.parseInt(console.nextLine());

                if(choice < GameUI.p1.getDecks().size()){
                    Deck chosen_deck = GameUI.p1.getDecks().get(choice);
                    selected_deck = chosen_deck;
                    print_deck_actions();
                }
                else{
                    edit_deck();
                }
            }catch(NumberFormatException e){
                System.out.println("This deck doesn't exist OR you did not type a number!");
                edit_deck();
            }

        }

    }

    public void print_deck_actions() {
        System.out.println("1) Add Card \n2) Remove Card \n3) Update Card\n4) Card Gallery\n5) Search Card \n6) Back");
        deck_actions_choices();
    }

    public void deck_actions_choices(){
        CardUI cardUI = new CardUI();
        String choice = console.nextLine();

        switch(choice){
            case "1":
                cardUI.create_card(selected_deck);
                break;
            case "2":
                cardUI.delete_card(selected_deck);
                break;
            case "3":
                cardUI.update_card(selected_deck);
                break;
            case "4":
                cardUI.show_all_cards(selected_deck);
                break;
            case "5":
                cardUI.search_cards(selected_deck);
                break;
            case "6":
                this.gUI.print_choices();
                break;
            default:
                System.out.println("Please choose a valid option!");
                print_deck_actions();
                break;



        }
    }
}
