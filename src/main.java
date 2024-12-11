public class main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        //boolean quit = false;
        while (game.quitProgram == false) {

            try {
                game.run();
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command");
            }
            if (game.gameOver == false)
                game.Play("Main");
            game.w = null;
            game.m = null;
            game.r = null;
            //game.gameOver = false;
        }
    }
}
