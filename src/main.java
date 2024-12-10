public class main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        try {
            game.run();
        }
        catch (InvalidCommandException e) {
            System.out.println("Invalid command");
        }
        if(game.gameOver == false)
            game.Play();
    }
}
