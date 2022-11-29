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
        return null;
    }
}
