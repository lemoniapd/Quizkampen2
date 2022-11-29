package Quizkampen.Server;

public class QuizProtocol {

    public static final int WAITING = 0;
    public static final int START_GAME = 1;
    public static final int CHOOSE_CATEGORY = 2;
    public static final int QUESTION_MODE = 3;
    public static final int SHOW_SCOREBOARD = 4;
    public static final int GAME_FINISHED = 5;

    private int state = WAITING;

    public String processInput(String input) {
        String output = null;

        if (state == WAITING) {
            output = "väntar på den andra spelaren";
            if (input.startsWith("väntat klart")) {
                state = START_GAME;
            }
        } else if (state == START_GAME) {
            output = "starta spel";
            state = CHOOSE_CATEGORY;
        } else if (state == CHOOSE_CATEGORY) {
            output = "välj kategori";
            state = QUESTION_MODE;
        } else if (state == QUESTION_MODE) {
            output = "svara på frågor";
            state = SHOW_SCOREBOARD;
        } else if (state == SHOW_SCOREBOARD) {
            output = "visa scoreboard";
            state = GAME_FINISHED;
        } else if (state == GAME_FINISHED) {
            output = "spelet avslutat";
        }
        return output;
    }
}
