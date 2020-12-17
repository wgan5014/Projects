package ghost;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
    /**
     * Parse the JSON file to get a JSON object for later access of its attributes
     * @param   filename    String
     * @return  JSONObject wrapping JSON file
     */
    public static JSONObject initialParse (String filename) {
        
        JSONParser jsonParser = new JSONParser();

        JSONParser jp = new JSONParser();
        JSONObject res = null;

        try {
            FileReader f = new FileReader(filename);
            res = (JSONObject) jp.parse(f);
        }
        catch (FileNotFoundException fnfe) {
            System.out.printf("%s could not be found\n", filename);
            return null;
        }
        catch (IOException io) {
            System.out.printf("%s failed to be opened for reading\n", filename);
            return null;
        }
        catch (ParseException pe) {
            System.out.printf("%s failed to parse\n", filename);
            return null; 
        }
        return res;
    }

    /**
     * Parses the name of the map used for gameplay
     * @return String - the mapname
     * @param jo    JSONObject
     */
    public static String mapNameParse (JSONObject jo) {
        String mapName = (String) jo.get("map");
        return mapName;
    }

    /**
     * Parses the amount of lives specified in JSON file for the player
     * @return long amount of lives
     * @param jo    JSONObject
     */
    public static long livesParse (JSONObject jo) {
        Object tmp = jo.get("lives");
        long l = (Long) tmp;
        return l;
    }


    /**
     * Parses speed of nonstatic entities
     * @param   jo  JSONObject wrapping json config file
     * @return long speed
     */
    public static long speedParse (JSONObject jo) {
        Object tmp = jo.get("speed");
        long l = (Long) tmp;
        return l;
    }

    /**
     * Parse the modelengths of the ghost from json file
     * @param jo    JSONObject which wraps json config file
     * @return long[] - holds modelengths
     */
    public static long[] modeLengths (JSONObject jo) {
        JSONArray arr = (JSONArray) jo.get("modeLengths");
        long[] res = new long[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            res[i] = (Long) arr.get(i);
        }
        return res;
    }

    public static long frightenedLength (JSONObject jo) {
        Object tmp = jo.get("frightened");
        long l = (Long) tmp;
        return l;
    }

    /**
     * Reads in the specified map filename, and parses it into a multi-dimensional array of Cell objects
     * and also prepares certain attributes of App e.g. ghosts, walls, fruits etc.
     * @param filename  String
     * @param app   App object
     * @return Cell[][] map
     */
    public static Cell[][] parseTxtMap(String filename, App app) {
        long wakaSpeed = speedParse(initialParse("config.json"));
        long wakaLives = livesParse(initialParse("config.json"));
        long[] modelengths = modeLengths(initialParse("config.json"));
        long frightLength = frightenedLength(initialParse("config.json"));
        Scanner s;
        try {
            s = new Scanner(new File(filename));
        }
        catch (FileNotFoundException fnfe) {
            System.out.printf("%s was not found", filename);
            return null; 
        }
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNextLine()) {
            lines.add(s.nextLine());
        }

        String[] res = lines.toArray(new String[lines.size()]);

        Cell[][] map = new Cell[res.length][]; // create new board - amount of rows as mapLines String[]

        for (int i = 0; i < res.length; i++) {
            map[i] = new Cell[res[i].length()]; // create new columns - amt. of columns as current String[] row
            for (int j = 0; j < res[i].length(); j++) {
                // parse non-empty cells
                switch (res[i].charAt(j)) {
                    case '1':
                        map[i][j] = new Horizontal(app.loadImage("./src/main/resources/horizontal.png"), j*16, i*16);
                        app.getGame().getWalls().add(map[i][j]);
                        break;
                    case '2':
                        map[i][j] = new Vertical(app.loadImage("./src/main/resources/vertical.png"), j*16, i*16);
                        app.getGame().getWalls().add(map[i][j]);
                        break;
                    case '3':
                        map[i][j] = new UpLeft(app.loadImage("./src/main/resources/upLeft.png"), j*16, i*16);
                        app.getGame().getWalls().add(map[i][j]);
                        break;
                    case '4':
                        map[i][j] = new UpRight(app.loadImage("./src/main/resources/upRight.png"), j*16, i*16);
                        app.getGame().getWalls().add(map[i][j]);
                        break;
                    case '5':
                        map[i][j] = new DownLeft(app.loadImage("src/main/resources/downLeft.png"), j*16, i*16);
                        app.getGame().getWalls().add(map[i][j]);
                        break;
                    case '6':
                        map[i][j] = new DownRight(app.loadImage("src/main/resources/downRight.png"), j*16, i*16);
                        app.getGame().getWalls().add(map[i][j]);
                        break;
                    case '7': // fruit
                        map[i][j] = new Fruit(app.loadImage("src/main/resources/fruit.png"), j*16, i*16);
                        app.getGame().getAllFruits().add(map[i][j]);
                        break;
                    case '8': // super-fruit
                        map[i][j] = new Superfruit(app.loadImage("src/main/resources/superfruit.png"), j*16, i*16);
                        app.getGame().getAllFruits().add(map[i][j]);
                        break;
                    case 'p': // player
                        Waka tmp = new Waka(app.loadImage("src/main/resources/playerLeft.png"), j*16, i*16 - 5,  wakaSpeed);
                        tmp.setLives((int)wakaLives);
                        app.getGame().setWaka(tmp);
                        break;
                    case '0': //empty cell
                        break;
                    case 'a': // ambusher
                        // PImage display, int x, int y, int offx, int offy, long vel
                        Ambush a = new Ambush(app.loadImage("./src/main/resources/ambusher.png"), j*16, i*16 - 5, wakaSpeed);
                        app.getGame().addGhost(a);
                        break;
                    case 'c': // chaser
                        Chaser c = new Chaser(app.loadImage("./src/main/resources/chaser.png"), j*16, i*16 - 5, wakaSpeed);
                        app.getGame().addGhost(c);
                        break; 
                    case 'i': // ignorant
                        Ignorant ign = new Ignorant(app.loadImage("./src/main/resources/ignorant.png"), j*16, i*16 - 5, wakaSpeed);
                        app.getGame().addGhost(ign);
                        break;
                    case 'w': // whim
                        Whim w = new Whim(app.loadImage("./src/main/resources/whim.png"), j*16, i*16 - 5, wakaSpeed);
                        app.getGame().addGhost(w);
                        break;
                    default:
                        System.out.println("Invalid character.");
                        System.exit(0);
                }
            }
        }
        if (app.getGame().getWaka() == null) {
            System.out.println("No player present.");
            System.exit(0);
        }
        if (app.getGame().getGhosts().size() > 0) {
            Ghost.setModelengths(modelengths);
            Ghost.setFrightLength( (int) frightLength);
        }
        return map;
    }
}