import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {

    public static class Information {
        public Credentials credentials;
        public SortedSet<String> favoriteGames;
        public String name;
        public String country;
        public Information(Credentials credentials, SortedSet<String> favoriteGames, String name, String country) {
            this.credentials = credentials;
            this.favoriteGames = favoriteGames;
            this.name = name;
            this.country = country;
        }
        public String getName() {
            return name;
        }
        public String getCountry() {
            return country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }
    }
    public Information information;
    public ArrayList<Character1> characters;
    public int no_games;
    public Account( ArrayList<Character1> characters, int no_games, Information information) {
        this.information = information;
        this.characters = characters;
        this.no_games = no_games;
    }

    public Information getInformation() {
        return information;
    }
    public int getGamesNumber() {
        return no_games;
    }
    public ArrayList<Character1> getCharacters() {
        return characters;
    }
}
