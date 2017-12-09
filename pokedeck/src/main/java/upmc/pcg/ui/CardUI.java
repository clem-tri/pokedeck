package upmc.pcg.ui;

import java.util.ArrayList;
import java.util.Scanner;
import upmc.pcg.game.*;

public class CardUI {

    private Scanner console = new Scanner(System.in);
    private DeckUI dui = new DeckUI();

    private ArrayList<Object> weakness = new ArrayList<>();
    private ArrayList<Object> resistance = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();

    private void choose_type(){
        int index = 0;
        for (String type:Card.getTypes()) {
            System.out.println((index++)+" => "+type);
        };
    }

    private void choose_card(Deck deck){
        int index = 0;
        for (Card card: deck.getCards()) {
            System.out.println((index++)+" => "+card.getName()+" ["+card.getType()+"]");
        };
    }

    public void update_card(Deck deck){
        this.choose_card(deck);

        try{

            int choice = Integer.parseInt(console.nextLine());

            if(choice < deck.getCards().size()){
                Card chosen_card = deck.getCards().get(choice);
                System.out.println("\u001B[33mWhat do you want to update for the "+chosen_card.getName()+" Card?\u001B[0m");
                if(chosen_card.getType().equals("MONSTER")){
                    this.edit_pokemon_card(chosen_card);
                }
                else{
                    this.edit_energy_card((EnergyCard)chosen_card);
                }
                dui.deckToJson(deck);
                dui.print_deck_actions();
            }
            else{
                update_card(deck);
            }
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("\u001B[31mThis option doesn't exist OR you did not type a number!\u001B[0m");
            update_card(deck);
        }

    }

    private void edit_attacks(Card card){
        System.out.println("Pokemon Attacks :");
        //1
        System.out.println("First attack name :");
        String attack_name = console.nextLine();
        System.out.println("First attack Power :");
        int attack_power = Integer.valueOf(console.nextLine());
        Attack attack1 = new Attack(attack_name,attack_power);
        // 2
        System.out.println("Second attack name :");
        attack_name = console.nextLine();
        System.out.println("Second attack Power :");
        attack_power = Integer.valueOf(console.nextLine());
        Attack attack2 = new Attack(attack_name,attack_power);

        this.attacks.add(attack1);
        this.attacks.add(attack2);

        card.setAttacks(attacks);
    }


    private void edit_resistance(Card card){
        System.out.println("Pokemon Energy Resistance :");
        this.choose_type();
        String energy_type = Card.getTypes()[Integer.valueOf(console.nextLine())];
        System.out.println("Resistance substraction :");
        int multiplier = Integer.valueOf(console.nextLine());
        this.resistance.add(energy_type);
        this.resistance.add(multiplier);
        card.setResistance(resistance);
    }

    private void edit_weakness(Card card){
        System.out.println("Pokemon Energy Weakness :");
        this.choose_type();
        String energy_type = Card.getTypes()[Integer.valueOf(console.nextLine())];
        System.out.println("Weakness multiplier :");
        int multiplier = Integer.valueOf(console.nextLine());
        this.weakness.add(energy_type);
        this.weakness.add(multiplier);
        card.setWeakness(weakness);
    }

    private void edit_pokemon_card(Card chosen_card){

        String[] property = new String[]{"Name","HP", "Energy", "Weakness", "Resistance", "Attacks"};

        for(int i=0; i < property.length;++i){
            System.out.println(i+1+") "+ property[i]+"\n");
        }

        String choice_property = console.nextLine();
        System.out.println(
                "New "+property[Integer.valueOf(choice_property)-1]+
                        " for "+chosen_card.getName()+": "
        );

        switch (choice_property){
            case "1":
                chosen_card.setName(console.nextLine());
                break;
            case "2":
                chosen_card.setHealth_points(Integer.valueOf(console.nextLine()));
                break;
            case "3":
                this.choose_type();
                chosen_card.setEnergy_type(Card.getTypes()[Integer.valueOf(console.nextLine())]);
                break;
            case "4":
                edit_weakness(chosen_card);
                break;
            case "5":
                edit_resistance(chosen_card);
                break;
            case "6":
                edit_attacks(chosen_card);
                break;
            default:
                System.out.println("Wrong value!!!");
                edit_pokemon_card(chosen_card);
                break;

        }
        System.out.println("\u001B[32m Card successfully updated => \n" +chosen_card+"\u001B[0m");

    }

    private void edit_energy_card(EnergyCard energy_card){
        System.out.println("New Energy card type :");
        this.choose_type();
        String choice_property = console.nextLine();
        String type_value = Card.getTypes()[Integer.valueOf(choice_property)];
        energy_card.setEnergy_type(type_value);
        energy_card.setName(type_value);

        System.out.println("\u001B[32m Card successfully updated => \n" +energy_card+"\u001B[0m");
        dui.print_deck_actions();
    }

    public void delete_card(Deck deck){
        if(deck.getCards().isEmpty()){
            System.out.println("\u001B[31mThere is no Card yet in your Deck, create one to start!\u001B[0m");
            dui.print_deck_actions();
        }
        else{
            this.choose_card(deck);

            try{
                int choice = Integer.valueOf(console.nextLine());

                if(choice < deck.getCards().size()){
                    Card card_to_delete = deck.getCards().get(choice);
                    deck.remove_card(card_to_delete);
                    dui.deckToJson(deck);
                    System.out.println("\u001B[32mCard removed successfully!\u001B[0m");

                    dui.print_deck_actions();
                }
            }

            catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                System.out.println("\u001B[31mThis card doesn't exist OR you did not type a number!\u001B[0m");
                delete_card(deck);
            }
        }



    }


    public void create_card(Deck deck) {

        System.out.println("Create a new Card:");
        System.out.println("Choose the card type : \n1) Pokemon \n2) Energy");
        switch (console.nextLine()) {
            case "1":
                create_pokemon_card(deck);

                break;
            case "2":
                create_energy_card(deck);

                break;

            default:
                System.out.println("Wrong value!!!");
                 create_card(deck);
        }


    }

    private void create_energy_card(Deck deck){
        try{
            EnergyCard energy_card = new EnergyCard();
            System.out.println("Energy card type :");
            this.choose_type();
            String type_value = Card.getTypes()[Integer.valueOf(console.nextLine())];
            energy_card.setEnergy_type(type_value);
            energy_card.setName(type_value);

            System.out.println("\u001B[32m Energy Card successfully created => " +energy_card+"\u001B[0m");

            deck.add_card(energy_card);
            dui.deckToJson(deck);

            dui.print_deck_actions();
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("\u001B[31mYou did not type a correct value, please try again!\u001B[0m");
            create_energy_card(deck);
        }


    }

    private void create_pokemon_card(Deck deck) {

        Card card = new Card();

        try{


            //set card name
            System.out.println("Pokemon name :");
            card.setName(console.nextLine());

            // set hp
            System.out.println("Pokemon HP :");
            card.setHealth_points(Integer.valueOf(console.nextLine()));

            //set energy type
            System.out.println("Pokemon Energy Type :");
            this.choose_type();
            String type_value = Card.getTypes()[Integer.valueOf(console.nextLine())];
            card.setEnergy_type(type_value);

            //set weakness
           this.edit_weakness(card);

            //set resistance
            this.edit_resistance(card);

            // set attacks
            this.edit_attacks(card);


            //finally add card to deck

            deck.add_card(card);
            dui.deckToJson(deck);
            System.out.println("\u001B[32mCard successfully created => \n" +card+"\u001B[0m");
            dui.print_deck_actions();



        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("\u001B[31mYou did not type a correct value, please try again!\u001B[0m");
            create_card(deck);
        }


    }




    public void show_all_cards(Deck deck){
        if(!deck.getCards().isEmpty()){
            for(Card card: deck.getCards()){
                System.out.println(card);
                System.out.println("_____________________");
            }

        }
        else
            System.out.println("\u001B[33mThere is no cards in your deck\u001B[0m");

        dui.print_deck_actions();
    }

    public void search_cards(Deck deck){
        try{
            if(!deck.getCards().isEmpty()){
                System.out.println("1) Search cards by ENERGY TYPE\n2) Search cards by NAME");
                String choice = console.nextLine();
                switch (choice){
                    case "1":
                        this.choose_type();
                        this.get_cards_by_energy(deck,console.nextLine());
                        break;
                    case "2":
                        System.out.println("Insert card name: ");
                        this.get_cards_by_name(deck,console.nextLine());

                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("\u001B[31mThis deck doesn't exist OR you did not type a number!\u001B[0m");
            search_cards(deck);
        }


    }

    private void get_cards_by_energy(Deck deck, String energy){
        int result= 0;
        for(Card card: deck.getCards()){
            if(card.getEnergy_type().equals(Card.getTypes()[Integer.valueOf(energy)])){
                ++result;
                System.out.println(card);
            }

        }
        if(result < 1)
            System.out.println("No card found");
        dui.print_deck_actions();
    }

    private void get_cards_by_name(Deck deck, String name){
        for(Card card: deck.getCards()){
            if (card.getName().equals(name))
                System.out.println(card);
        }
        dui.print_deck_actions();
    }



}
