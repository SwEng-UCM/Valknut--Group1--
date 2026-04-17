package me.control;

import java.util.List;

import me.command.Command;
import me.command.CommandFactory;
import me.model.AutonomousHero;
import me.model.Combat;
import me.model.CombatOption;
import me.model.Enemy;
import me.model.EnemyBuilder;
import me.model.Hero;
import me.model.HeroBuilder;
import me.model.items.DamageItem;
import me.model.items.HealingItem;
import me.model.items.ItemType;
import me.model.items.ResistanceItem;
import me.model.save.SaveGameData;
import me.model.save.SaveGameManager;
import me.view.AudioManager;
import me.view.CombatView;
import me.view.CtrlPanel;
import me.view.Messages;
import me.view.StoryView;

public class Controller {

    private static StoryView sv;
    private static CombatView cv;
    private static Controller instance;
    private Combat cb;
    private final CtrlPanel controlPanel;
    private int num_enemies;

    private Controller() {
        sv = StoryView.getInstance();
        cv = CombatView.getInstance();
        controlPanel = new CtrlPanel(this);
    }

    public void storyPrint(String s) {
        sv.printLine(s);
    }

    public void combatPrint(String s) {
        cv.printLine(s);
    }

    public int getNumEnemies() {
        return num_enemies;
    }

    public void run() {
        cb = initCmb();
        startGame();
    }

    public void startGame() {
        AudioManager.getInstance().stopMusic();
        AudioManager.getInstance().playMusic("resources/sounds/titleMusic.wav");
        controlPanel.onGameStart();
    }

    public void menuScreen() {
        controlPanel.showMainMenu();
    }

    public void charactersScreen() {
        controlPanel.onSelection();
    }

    public void setPreviousScreenToSettings(String s) {
        controlPanel.setPreviousScreenToSettings(s);
    }

    public void settingScreen() {
        controlPanel.settingScreen();
    }

    public void startMultiplayer() {
        AudioManager.getInstance().stopMusic();
        AudioManager.getInstance().playMusic("resources/sounds/internetMusic.wav");
        multiplayerScreen();
    }

    public void multiplayerScreen() {
        controlPanel.multiplayerScreen();
    }

    public void startStory() {
        sv.tellIntro();
    }

    public void tellFirstLinesChapterOne() {
        sv.clear();
        sv.tellFirstLinesChapterOne(cb.getHeroes().get(0), cb.getHeroes().get(1));
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }
    
    public List<Enemy> getEnemies(){
        return cb.getEnemies();
    }
    
    public List<Hero> getHeroes(){
        return cb.getHeroes();
    }

    public Combat initCmb() {
        Combat cmb = new Combat();
        cmb.addEnemy(firstEnemies(1));
        cmb.addEnemy(firstEnemies(2));
        cmb.addEnemy(firstEnemies(1));
        cmb.addEnemy(firstEnemies(2));
        num_enemies = 4;
        return cmb;
    }

    public Enemy firstEnemies(Integer i) {
        if (i == 1) {
            return EnemyBuilder.buildEnemy("Ice");
        } else {
            return EnemyBuilder.buildEnemy("Fire");
        }
    }

    /**
     * Returns the hero whose turn is currently active.
     *
     * @return the current hero or null if the turn is not valid
     */
    private Hero getCurrentHero() {
        if (cb == null || cb.getHeroes().isEmpty()) {
            return null;
        }

        if (cb.turn() == 1 && cb.getHeroes().size() >= 1) {
            return cb.getHeroes().get(0);
        }

        if (cb.turn() == 2 && cb.getHeroes().size() >= 2) {
            return cb.getHeroes().get(1);
        }

        return null;
    }

    /**
     * Resolves the combat option for the current hero.
     * Autonomous heroes choose their own action, while player-controlled heroes
     * use the option passed from the UI.
     *
     * @param currentHero the active hero
     * @param combatOption the numeric option selected by the user
     * @return the resolved combat option
     */
    private CombatOption resolveCombatOption(Hero currentHero, int combatOption) {
        if (currentHero == null) {
            return null;
        }

        if (currentHero.isAutonomous()) {
            AutonomousHero autonomousHero = (AutonomousHero) currentHero;
            return autonomousHero.selectAction();
        }

        return switch (combatOption) {
            case 1 -> CombatOption.ATTACK;
            case 2 -> CombatOption.DEFEND;
            case 3 -> CombatOption.USE_ITEM;
            case 4 -> CombatOption.RUN;
            case 5 -> CombatOption.STATS;
            default -> null;
        };
    }

    /**
     * Executes the enemy phase when both heroes have finished their turns.
     */
    private void executeEnemyTurn() {
        if (cb.turn() == 3) {
            cv.print(cb.enemyTurnToString());

            for (Enemy e : cb.getEnemies()) {
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }

            cb.setTurn(1);
            cv.printLine(cb.update());
        }
    }

    public boolean action(int combatOption, int target) {
        boolean finishedAction = false;

        cb.updateItems();

        Hero currentHero = getCurrentHero();
        CombatOption selectedOption = resolveCombatOption(currentHero, combatOption);

        // Build the command that corresponds to the selected action.
        Command command = CommandFactory.createCommand(cb, cv, currentHero, selectedOption, target);

        if (command != null) {
            finishedAction = command.execute();

            // Preserve the original behavior:
            // only commands that advance the turn should move combat forward.
            if (command.advancesTurn()) {
                cb.setTurn(cb.turn() + 1);
            }
        }

        executeEnemyTurn();
        controlPanel.onSelection();

        return finishedAction;
    }

    public void saveGame() {
        if (cb != null) {
            SaveGameData data = new SaveGameData(cb);
            SaveGameManager.saveGame(data);
        }
    }

    public void loadGame() {
        SaveGameData data = SaveGameManager.loadGame();

        if (data != null) {
            this.cb = data.getCombat();
            controlPanel.onCombat();
            System.out.println("Game loaded successfully.");
        }
    }

    public String selectCharacter(int i, int player) {
        StringBuilder sb = new StringBuilder();
        Hero new_hero;

        if (i == 0) {
            new_hero = HeroBuilder.buildHero("Freya");
            sb.append("GERSEMI");
        } else {
            new_hero = HeroBuilder.buildHero("Loki");
            sb.append("VÁLI");
        }

        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
        new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, ItemType.DAMAGE));

        cb.addHero(new_hero);
        new_hero.setCombat(cb);
        sb.append(Messages.NEW_LINE);

        return sb.toString();
    }

    /**
     * Starts a new game after character selection and opens the combat screen.
    */
    public void startSelectedGame() {
        tellFirstLinesChapterOne();
        controlPanel.onCombat();
    }

    public void exit() {
        controlPanel.onQuit();
    }
}