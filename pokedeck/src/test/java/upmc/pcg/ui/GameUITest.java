package upmc.pcg.ui;


public class GameUITest {
    private GameUI gameUI;

    @org.junit.jupiter.api.Test
    void start() {
        gameUI.start();
    }

    @org.junit.jupiter.api.Test
    void print_choices() {
        gameUI.print_choices();
    }

    @org.junit.jupiter.api.Test
    void user_choices() {
        gameUI.user_choices();
    }


}
