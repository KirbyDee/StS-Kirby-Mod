package theSorcerer;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.cards.DynamicCard;
import theSorcerer.characters.TheSorcerer;
import theSorcerer.events.DynamicEvent;
import theSorcerer.events.ElementalBazarEvent;
import theSorcerer.events.ElementalCreaturesEvent;
import theSorcerer.events.FlashingCaveEvent;
import theSorcerer.potions.*;
import theSorcerer.relics.*;
import theSorcerer.util.IDCheckDontTouchPls;
import theSorcerer.util.TextureLoader;
import theSorcerer.variables.SecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 4 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault", and change to "yourmodname" rather than "thedefault".
// You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories, and press alt+c to make the replace case sensitive (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class KirbyDeeMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {

    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(KirbyDeeMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theSorcererDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    private static final String MODNAME = "KirbyDee Mod";

    private static final String MOD_ID = "KirbyDeeMod";
    private static final String AUTHOR = "KirbyDee";
    private static final String DESCRIPTION = "A mod for Slay the Spire feat. the Sorcerer.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color SORCERER_YELLOW = CardHelper.getColor(191.0f, 191.0f, 0.0f);
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theSorcererResources/images/512/bg_attack.png";
    private static final String SKILL_DEFAULT_GRAY = "theSorcererResources/images/512/bg_skill.png";
    private static final String POWER_DEFAULT_GRAY = "theSorcererResources/images/512/bg_power.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theSorcererResources/images/512/card_orb.png";
    private static final String CARD_ENERGY_ORB = "theSorcererResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theSorcererResources/images/1024/bg_attack.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theSorcererResources/images/1024/bg_skill.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theSorcererResources/images/1024/bg_power.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theSorcererResources/images/1024/card_orb.png";
    
    // Character assets
    private static final String THE_SORCERER_BUTTON = "theSorcererResources/images/charSelect/TheSorcererButton.png";
    private static final String THE_SORCERER_PORTRAIT = "theSorcererResources/images/charSelect/TheSorcererPortraitBG.png";
    public static final String THE_SORCERER_SHOULDER_1 = "theSorcererResources/images/char/theSorcerer/shoulder.png";
    public static final String THE_SORCERER_SHOULDER_2 = "theSorcererResources/images/char/theSorcerer/shoulder2.png";
    public static final String THE_SORCERER_CORPSE = "theSorcererResources/images/char/theSorcerer/corpse.png";
    public static final String BADGE_IMAGE = "theSorcererResources/images/Badge.png";
    public static final String THE_SORCERER_IMAGE = "theSorcererResources/images/char/theSorcerer/sorcerer.png";
    
    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(AbstractCard.CardType cardType, String name) {
        return makeCardPath(cardType.name().toLowerCase() + "/" + name + ".png");
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeUiPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public KirbyDeeMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("theSorcerer");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project) and press alt+c (or mark the match case option)
        // replace all instances of theDefault with yourModID, and all instances of thedefault with yourmodid (the same but all lowercase).
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        // It's important that the mod ID prefix for keywords used in the cards descriptions is lowercase!

        // 3. Scroll down (or search for "ADD CARDS") till you reach the ADD CARDS section, and follow the TODO instructions

        // 4. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheSorcerer.Enums.COLOR_YELLOW.toString());
        
        BaseMod.addColor(TheSorcerer.Enums.COLOR_YELLOW, SORCERER_YELLOW, SORCERER_YELLOW, SORCERER_YELLOW,
                SORCERER_YELLOW, SORCERER_YELLOW, SORCERER_YELLOW, SORCERER_YELLOW,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theSorcererDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("kirbyDeeMod", "theSorcererConfig", theSorcererDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = KirbyDeeMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        }
        else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        }
        else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = KirbyDeeMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = KirbyDeeMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        KirbyDeeMod kirbyDeeMod = new KirbyDeeMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheSorcerer.Enums.THE_SORCERER.toString());
        
        BaseMod.addCharacter(new TheSorcerer("the Sorcerer", TheSorcerer.Enums.THE_SORCERER),
                THE_SORCERER_BUTTON, THE_SORCERER_PORTRAIT, TheSorcerer.Enums.THE_SORCERER);
        
        receiveEditPotions();
        logger.info("Added " + TheSorcerer.Enums.THE_SORCERER.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("kirbyDeeMod", "theSorcererConfig", theSorcererDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Add the event
        BaseMod.addEvent(
                new AddEventParams.Builder(DynamicEvent.getID(ElementalCreaturesEvent.class), ElementalCreaturesEvent.class) // for this specific event
                        .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
                        .playerClass(TheSorcerer.Enums.THE_SORCERER) // Character specific event
                        .create()
        );
        BaseMod.addEvent(
                new AddEventParams.Builder(DynamicEvent.getID(FlashingCaveEvent.class), FlashingCaveEvent.class)
                        .dungeonIDs(Exordium.ID, TheBeyond.ID)
                        .playerClass(TheSorcerer.Enums.THE_SORCERER)
                        .create()
        );

        BaseMod.addEvent(
                new AddEventParams.Builder(DynamicEvent.getID(ElementalBazarEvent.class), ElementalBazarEvent.class)
                        .dungeonIDs(Exordium.ID, TheCity.ID, TheBeyond.ID, TheEnding.ID)
                        .playerClass(TheSorcerer.Enums.THE_SORCERER)
                        .create()
        );

        // =============== Glow =================

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_SORCERER".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(
                HeatedPotion.class,
                Color.SCARLET,
                Color.RED,
                Color.WHITE,
                DynamicPotion.getID(HeatedPotion.class),
                TheSorcerer.Enums.THE_SORCERER
        );
        BaseMod.addPotion(
                ChilledPotion.class,
                Color.NAVY,
                Color.BLUE,
                Color.WHITE,
                DynamicPotion.getID(ChilledPotion.class),
                TheSorcerer.Enums.THE_SORCERER
        );
        BaseMod.addPotion(
                KnowledgePotion.class,
                Color.OLIVE,
                Color.YELLOW,
                null,
                DynamicPotion.getID(KnowledgePotion.class),
                TheSorcerer.Enums.THE_SORCERER
        );
        BaseMod.addPotion(
                FlashbackPotion.class,
                Color.SKY,
                Color.CYAN,
                null,
                DynamicPotion.getID(FlashbackPotion.class),
                TheSorcerer.Enums.THE_SORCERER
        );
        BaseMod.addPotion(
                ArcanePotion.class,
                Color.WHITE,
                Color.WHITE,
                Color.BLACK,
                DynamicPotion.getID(ArcanePotion.class),
                TheSorcerer.Enums.THE_SORCERER
        );

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new ElementalConstruct(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BurningSoul(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new FreezingSoul(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new EnergizedSoul(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new ElementalMaster(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BottledGhost(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BottledLife(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BagOfIce(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BouncyCastle(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new ElementalPets(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new TreeOfLife(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new ProtectingGloves(), TheSorcerer.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new GameController(), TheSorcerer.Enums.COLOR_YELLOW);

        // This adds a relic to the Shared pool. Every character can find this relic.
//        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(ElementalConstruct.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(BurningSoul.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(FreezingSoul.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(EnergizedSoul.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(ElementalMaster.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(BottledGhost.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(BottledLife.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(BagOfIce.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(BouncyCastle.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(ElementalPets.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(TreeOfLife.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(ProtectingGloves.class));
        UnlockTracker.markRelicAsSeen(DynamicRelic.getID(GameController.class));
        // TODOO: which ones do we unlock?
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        //TODO: Rename the "DefaultMod" with the modid in your ModTheSpire.json file
        //TODO: The artifact mentioned in ModTheSpire.json is the artifactId in pom.xml you should've edited earlier
        new AutoAdd(MOD_ID) // ${project.artifactId}
            .packageFilter(DynamicCard.class) // filters to any class in the same package as DynamicCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Orb-Strings.json");

        // MonsterStrings
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                getModID() + "Resources/localization/eng/KirbyDeeMod-Monster-Strings.json");

        logger.info("Done editing strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/KirbyDeeMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static String makeKeywordID(String idText) {
        return getModID().toLowerCase() + ":" + idText;
    }
}
