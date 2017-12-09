// Copyright 2017 Pierre Talbot (IRCAM)

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

//     http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package upmc.pcg.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import com.google.gson.Gson;
import upmc.pcg.game.Deck;
import upmc.pcg.game.Game;
import upmc.pcg.game.Player;

public class GameUI {
  private final Game game = new Game();
  private final Scanner console = new Scanner(System.in);
  public static Player p1 = new Player();




    public void start() {
        print_welcome_msg();
        ArrayList<String> names = ask_players_names();
        getJsonDeck();
        print_choices();
        game.initialize(names);
        game.play();
    }

    private void print_welcome_msg() {
        System.out.println("Welcome to Pokedeck!");

    }

    private ArrayList<String> ask_players_names() { return new ArrayList<>(); }



    public void print_choices() {
        System.out.println("1) Create Deck\n2) Edit Deck");
        user_choices();
    }

    private void getJsonDeck(){
        for (final File fileEntry : new File("pokedeck\\data\\deck").listFiles()) {
            if (!fileEntry.isDirectory()) {
                Gson gson = new Gson();
                try {
                    Deck deck = gson.fromJson(new FileReader("pokedeck\\data\\deck\\"+fileEntry.getName()), Deck.class);
                    p1.add_deck(deck);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void user_choices() {
        String user_choice = console.nextLine();
        DeckUI deckUI = new DeckUI();

        switch (user_choice) {
            case "1":
                deckUI.create_deck();
                break;
            case "2":
                deckUI.edit_deck();
            default:
                System.out.println("Please choose a valid option!");
                print_choices();
                break;

        }
    }
}
