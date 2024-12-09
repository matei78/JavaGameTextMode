//import org.example.entities.characters.Character;
//import org.example.entities.characters.Mage;
//import org.example.entities.characters.Rogue;
//import org.example.entities.characters.Warrior;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import Game.Account;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;



public class JsonInput {
        public static ArrayList<Account> deserializeAccounts() {
            String accountPath = "C:\\Users\\matei\\IdeaProjects\\Tema1\\src\\accounts.json";
            ArrayList<Account> accounts = new ArrayList<>();
            try {
                // Citirea fisierului JSON
                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader(accountPath);
                JSONObject obj = (JSONObject) parser.parse(reader);
                JSONArray accountsArray = (JSONArray) obj.get("accounts");

                // Iterarea prin conturile din JSON
                for (int i = 0; i < accountsArray.size(); i++) {
                    JSONObject accountJson = (JSONObject) accountsArray.get(i);

                    // Deserializarea datelor contului
                    String name = (String) accountJson.get("name");
                    String country = (String) accountJson.get("country");
                    int gamesNumber = Integer.parseInt((String) accountJson.get("maps_completed"));

                    // Deserializarea credentialelor
                    Credentials credentials = null;
                    try {
                        JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                        String email = (String) credentialsJson.get("email");
                        String password = (String) credentialsJson.get("password");
                        credentials = new Credentials(email, password);
                    } catch (Exception e) {
                        System.out.println("! This account doesn't have all credentials !");
                    }

                    // Deserializarea jocurilor favorite
                    SortedSet<String> favoriteGames = new TreeSet<>();
                    try {
                        JSONArray games = (JSONArray) accountJson.get("favorite_games");
                        for (int j = 0; j < games.size(); j++) {
                            favoriteGames.add((String) games.get(j));
                        }
                    } catch (Exception e) {
                        System.out.println("! This account doesn't have favorite games !");
                    }

                    // Deserializarea personajelor
                    ArrayList<Character1> characters = new ArrayList<>();
                    try {
                        JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                        for (int j = 0; j < charactersListJson.size(); j++) {
                            JSONObject charJson = (JSONObject) charactersListJson.get(j);
                            String cname = (String) charJson.get("name");
                            String profession = (String) charJson.get("profession");
                            String level = (String) charJson.get("level");
                            int lvl = Integer.parseInt(level);
                            Integer experience = ((Long) charJson.get("experience")).intValue();

                            Character1 newCharacter = null;
                            if (profession.equals("Warrior"))
                                newCharacter = new Warrior(cname, experience, lvl);
                            if (profession.equals("Rogue"))
                                newCharacter = new Rogue(cname, experience, lvl);
                            if (profession.equals("Mage"))
                                newCharacter = new Mage(cname, experience, lvl);
                            characters.add(newCharacter);
                        }
                    } catch (Exception e) {
                        System.out.println("! This account doesn't have characters !");
                    }

                    // Crearea obiectului Account
                    Account.Information information = new Account.Information(credentials, favoriteGames, name, country);
                    Account account = new Account(characters, gamesNumber, information);
                    accounts.add(account);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return accounts;
        }

        // Funcție pentru a salva conturile într-un fișier JSON
        public static void serializeAccounts(ArrayList<Account> accounts) {
            JSONArray accountsArray = new JSONArray();

            for (Account account : accounts) {
                JSONObject accountJson = new JSONObject();

                // Serializarea informațiilor contului
                accountJson.put("name", account.getInformation().getName());
                accountJson.put("country", account.getInformation().getCountry());
                accountJson.put("maps_completed", String.valueOf(account.getGamesNumber()));

                // Serializarea credentialelor
                JSONObject credentialsJson = new JSONObject();
                credentialsJson.put("email", account.getInformation().getCredentials().getEmail());
                credentialsJson.put("password", account.getInformation().getCredentials().getPassword());
                accountJson.put("credentials", credentialsJson);

                // Serializarea jocurilor favorite
                JSONArray favoriteGamesJson = new JSONArray();
                for (String game : account.getInformation().getFavoriteGames()) {
                    favoriteGamesJson.add(game);
                }
                accountJson.put("favorite_games", favoriteGamesJson);

                // Serializarea personajelor
                JSONArray charactersJson = new JSONArray();
                for (Character1 character : account.getCharacters()) {
                    JSONObject characterJson = new JSONObject();
                    characterJson.put("name", character.getName());
                    characterJson.put("profession", character.getProfession());
                    characterJson.put("level", String.valueOf(character.getLevel()));
                    characterJson.put("experience", character.getExperience());
                    charactersJson.add(characterJson);
                }
                accountJson.put("characters", charactersJson);

                accountsArray.add(accountJson);
            }

            // Scrierea fișierului JSON
            try (FileWriter file = new FileWriter("C:\\Users\\matei\\IdeaProjects\\Tema1\\accounts_output.json")) {
                file.write(accountsArray.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}

