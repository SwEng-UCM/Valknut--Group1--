package me.view;

public class Messages {
	
	// Battle Miscellaneous
	public static final String BATTLE_START = "NEW ENEMIES CROSS YOUR PATH. A NEW BATTLE STARTS";
	public static final String BATTLE_WIN = "THE HEROES WIN. ONE STEP CLOSER TO VALHALLA";
	public static final String BATTLE_LOSS = "THE HEROES LOSE. YOUR ADVENTURE ENDS HERE";
	public static final String BATTLE_ESCAPE = "THE HEROES ESCAPED THE FIGTH";
	public static final String ENEMY_TURN = "ENEMIES' TURN...";
	public static final String HERO_TURN = "HEROES' TURN...";
	public static final String HERO_WEAK = " is WEAK to ";
	public static final String HERO_RESISTANT = " is RESISTANT to ";
	public static final String DEFEND = " defends";

	// Item Miscellaneous
	public static final String NON_MATCHED_ITEM = "The types of the objects don't match. Try different";
	public static final String MATCHED_ITEM = "Complement added";
	public static final String EMPTY_INV = "Empty inventory";
	public static final String INVALID_ITEM = "\nInvalid Item --> ";
	public static final String USING_ITEM = "WRONG. Item already in use --> ";
	public static final String USED_ITEM = "Item used --> ";
	
	// Enemies attacks
	public static final String ICEGIANT_ATTACK = "The giant swings his club in your direction";
	public static final String FIREGIANT_ATTACK = "The flaming monster charges at you with its sword";
	public static final String SHAPESHIFTER_ATTACK = "In his animal form the shapeshifter slashes you with its claws";
	public static final String BERSERKER_ATTACK = "The warrior launches a barrage of punches and axe slashes at you";
	public static final String ELF_ATTACK = "The elf chanels the magic of the runes to harm you";
	public static final String DRAUGR_ATTACK = "The undead warrior picks up his weapon and lunges at you with a freezing glare";
	public static final String ENEMY_MISS = " miss: NO HERO FIGHTING"; // To be made into 2 different messages maybe
	
	// Bosses attacks
	public static final String THRYM_ATTACK = "Thrym attacks you with his gigantic axe, leaving a huge hole on the floor under you";
	public static final String THRYM_SP1_ATTACK = "Thrym throws Mjolnir at you, and it ricochets between you and your partners";
	public static final String THRYM_SP2_ATTACK = "Thrym commands Summarbrander and makes it slash you";
	public static final String THRYM_SP3_ATTACK = "Thrym throws Gungnir torwards you, and it doesn't stop until it reaches you";
	public static final String FAFNIR_ATTACK = "Fafnir sends you flying with a powerful wingbeat";
	public static final String FAFNIR_SP_ATTACK = "Fafnir makes fire rain down from the skies, burning you and everything near you";
	
	// Player Actions
	public static final String PLAYER_RUNS = "YOU ESCAPED TO FIGHT ANOTHER DAY, LUCKY HERO";
	public static final String PLAYER_RUNFAIL = "YOU FAILED IN YOUR ESCAPE";
	
	// Enemies Names
	public static final String ICEGIANT = "Ice Giant";
	public static final String FIREGIANT = "Fire Giant";
	public static final String SHAPESHIFTER = "Shapeshifter";
	public static final String BERSERKER = "Berserker";
	public static final String ELF = "Elf";
	public static final String DRAUGR = "Draugr";

	// Bosses names
	public static final String THRYM = "Thrym";
	public static final String FAFNIR = "Fafnir";
	public static final String SKOLL = "Sköll";
	public static final String HATI = "Hati";
	
	// Utils
	public static final String NEW_LINE = System.lineSeparator();
	public static final String ENTER_VV = "Enter valid value";
	public static final String PRESS_EN = "Press enter to continue...";

	//Story
	public static final String INTRO_LINES = """
                                                 For The Viking Warriors the greatest honor upon death was to enter VALHALLA, the Hall of Heroes. 
                                                 This is a majestic hall located in ASGARD and presided over by the god ODIN where our heroes will live untill RAGNAROK, 
                                                 when they will march out of its many doors to fight in aid of ODIN against the JOTNARE, the ice giants.""" ;
	
	public static final String CHOOSE = "CHOOSE YOUR HEROES";
	public static final String CHAPTER_ONE_BEGINING = "For your journey to Vahalla you will first need to find an appropiate weapon. These weapons can be obtained by defeating the most powerful enemies along your journey." + NEW_LINE + //
				NEW_LINE + //
				"There are rumors a tribe of GIANTS have taken refuge over a nearby forest. If you defeat them you may find clues to the location of a powerful foe whose weapon collection you can pillage." + NEW_LINE + //
				NEW_LINE + //
				"Good luck in your journey heroes!";
}