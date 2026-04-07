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
	public static final String CHAPTER_ONE_MIDDLE = """
            Congratulations on your victory. You've now reached an intersection. Which path will you follow?"""  + NEW_LINE + 
            "You can see a dark forest on your right and a dry plain on your right. Where will you go?";
	
	public static final String CHAPTER_ONE_END = " "; //Pending review
	
	
	// Story chapter 2
	public static final String CHAPTER_TWO_BEGINING = """
            As you continue your journey in search of a legendary weapon to use in your quest to join Valhalla, you abruptly spot a 
            dense fog in the distance. You approach the mist, slowly but surely, remaining ever-so cautious until the scene is clear  
            enough to decipher the image that was before hidden: The place is a graveyard full of Dragurs. As bad as this may 
            seem, it presents an opportunity to ask for clues to the legendary weapon you’ve been searching.""" ;


	public static final String CHAPTER_TWO_MIDDLE = """
            As you approach the undead warriors, the fog becomes denser and the smell more putrid. A voice calls from beyond  
            your range of sight “Who goes there and what is your business in these lands?” “We are * and *, and we come in search   
            enough of clues to find a legendary weapon to carry to battle”. The voice abruptly bursts into laughter. “There are no 
            swords here, but there IS battle!” """ ;
	
	public static final String CHAPTER_TWO_END = """
            The last remaining Dragur falls to the ground, defeated. “Fine, you wanted a legendary weapon? You can go get it  
            yourselves. We heard the wolves of the forest talk about the new weapon Fenrir acquired, a most powerful blade it  
            would seem, judging by the way they talked about it. """ ;

	//Story chapter 3
	
	public static final String CHAPTER_THREE_BEGGINING = """
            With this new goal in mind you set forth towards your new destination. After many days of walking, a massive shadow   
            is cast upon you and a ferocious wind begins to push both of you back and forth. As you raise your sight towards the    
            sky, your worst fears evolve from imagination to reality, as the massive dragon lands in front of you, smashing the 
            ground and making it tremble from its sheer weight. The low tone of voice with which it speaks is on par with its size
            and appearance. “Would you look at that, it seems I won’t need to move any further to hunt today’s meal”. You ready 
            your weapons and throw a futile warning to the dragon “We are not afraid of you and we will give you a bigger fight
            than you can handle”. The dragon laughs “I very much doubt everything about what you just said, but give it your best shot”. """ ;
	
	public static final String CHAPTER_THREE_END = """
            With one last roar that shakes the skies, the dragon’s body collapses, producing a great cloud of dust. “Are you okay?”   
            “Yeah, I got scared for a moment because of the gas, but I feel just fine”. After the dust settles, you spot a claw and a 
            scale that seem to have fallen from the dragon during the battle. These will make good weapons until you achieve your goal. """ ;
	
	//Stoye chapter 4
	
	
	//Button Names
	public static final String ATTACK = "ATTACK";
	public static final String DEFEND_ACTION = "DEFEND";
	public static final String USE_ITEM = "USE ITEM";
	public static final String RUN = "RUN";
	public static final String STATS = "STATS";
	
}