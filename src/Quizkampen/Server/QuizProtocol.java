package Quizkampen.Server;

public class QuizProtocol {

    public static final int WAITING = 0;
    public static final int START_GAME = 1;
    public static final int CHOOSE_CATEGORY = 2;
    public static final int QUESTION_MODE = 3;
    public static final int SHOW_SCOREBOARD = 4;
    public static final int GAME_FINISHED = 5;

    private int state = START_GAME;

    public Response processInput(Response input) {
        Response output = null;
        System.out.println("in protocol " + input.getOperation());

        /*if (state == WAITING) {
            if (input.getOperation().equalsIgnoreCase("väntat klart")) {
                state = START_GAME;
            }
            else output = new Response("väntar på den andra spelaren");
            }
         */
        if (state == START_GAME) {
            output = new Response("starta spel");
            state = CHOOSE_CATEGORY;
            return output;
        } else if (state == CHOOSE_CATEGORY) {
            output = new Response("välj kategori");
            state = QUESTION_MODE;
            System.out.println("state satt till q-mode");
            return output;
        } else if (state == QUESTION_MODE) {
            output = new Response("svara");
            System.out.println("KOMMER HIT?");
            state = SHOW_SCOREBOARD;
            return output;
        } else if (state == SHOW_SCOREBOARD) {
            System.out.println("protkoll scoreboard");
            output = new Response("visa scoreboard");
            state = GAME_FINISHED;
            return output;
        } else if (state == GAME_FINISHED) {
            System.out.println("protkoll avslut");
            output = new Response("spelet avslutat");
            return output;
        }
        return null;
    }
}
