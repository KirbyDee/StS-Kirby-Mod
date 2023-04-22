package theSorcerer.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;
import theSorcerer.cards.Defend_Sorcerer;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.Strike_Sorcerer;
import theSorcerer.cards.WellPrepared;
import theSorcerer.cards.fire.Scorch;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.ElementalConstruct;

import java.util.ArrayList;

import static theSorcerer.KirbyDeeMod.*;
import static theSorcerer.characters.TheSorcerer.Enums.COLOR_ORANGE;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class TheSorcerer extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(KirbyDeeMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_SORCERER;
        @SpireEnum(name = "SORCERER_ORANGE_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_ORANGE;
        @SpireEnum(name = "SORCERER_ORANGE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "theSorcererResources/images/char/defaultCharacter/orb/layer1.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer2.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer3.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer4.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer5.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer6.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer1d.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer2d.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer3d.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer4d.png",
            "theSorcererResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public TheSorcerer(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theSorcererResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theSorcererResources/images/char/defaultCharacter/Spriter/theSorcererAnimation.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_SORCERER_SHOULDER_2, // campfire pose
                THE_SORCERER_SHOULDER_1, // another campfire pose
                THE_SORCERER_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================  

        loadAnimation(
                THE_SORCERER_SKELETON_ATLAS,
                THE_SORCERER_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                NAMES[0],
                TEXT[0],
                STARTING_HP,
                MAX_HP,
                ORB_SLOTS,
                STARTING_GOLD,
                CARD_DRAW,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false
        );
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        //ironclad 75 (23/36/16)
        // 32 attacks
        // - common: 16
        // - uncommon: 11
        // - rare: 5
        // 14 power
        // - common: 0
        // - uncommon: 8
        // - rare: 6
        // 29 skills
        // - common: 7
        // - uncommon: 17
        // - rare: 5

        //silent 75 (23/33/19)
        // 28 attacks
        // - common: 11
        // - uncommon: 13
        // - rare: 4
        // 11 power
        // - common: 0
        // - uncommon: 6
        // - rare: 5
        // 36 skills
        // - common: 12
        // - uncommon: 14
        // - rare: 10

        //defect 75 (22/36/17)
        // 24 attacks
        // - common: 11
        // - uncommon: 8
        // - rare: 5
        // 14 power
        // - common: 0
        // - uncommon: 8
        // - rare: 6
        // 37 skills
        // - common: 11
        // - uncommon: 20
        // - rare: 6

        //watcher 75 (23/35/17)
        // 27 attacks
        // - common: 12
        // - uncommon: 12
        // - rare: 3
        // 12 power
        // - common: 0
        // - uncommon: 8
        // - rare: 4
        // 36 skills
        // - common: 11
        // - uncommon: 15
        // - rare: 10


        //sorcerer 49 (19/19/11) - 75
        // 15 attacks
        // - common: 9 -> ok?
        // - uncommon: 4 -> more
        // - rare: 2 -> more
        // 6 power
        // - common: 0 -> ok
        // - uncommon: 4 -> more
        // - rare: 3 -> more
        // 27 skills
        // - common: 10 -> ok?
        // - uncommon: 11 -> more
        // - rare: 6 -> more
        // relic:
        // - starter: 1
        //  - ElementalConstruct
        // - common: 1
        //  - ?
        // - uncommon: 2
        //  - Elemental Master
        //  - Bottled Ghost
        // - rare: 3
        //  - Bottled Life
        //  - ?
        //  - ?
        // - shop: 1
        //  - ?
        // - boss: 3
        //  - BurningSoul
        //  - FreezingSoul
        //  - EnergizedSoul
        // potions 3:
        // - common: 1
        //  - HeatedPotion
        //  - ChilledPotion
        //  - KnowledgePotion
        // - uncommon: 1
        //  - FlashbackPotion
        // - rare: 1
        //  - ArcanePotion

        logger.info("Begin loading starter Deck Strings");
        // 4 strikes
        retVal.add(Strike_Sorcerer.ID);
        retVal.add(Strike_Sorcerer.ID);
        retVal.add(Strike_Sorcerer.ID);
        retVal.add(Strike_Sorcerer.ID);
        // 4 defends
        retVal.add(Defend_Sorcerer.ID);
        retVal.add(Defend_Sorcerer.ID);
        retVal.add(Defend_Sorcerer.ID);
        retVal.add(Defend_Sorcerer.ID);
        // 1 common attack
//        retVal.add(DefaultCommonAttack.ID); // TODO: 1 basic attack
        // 1 common skill
//        retVal.add(DefaultCommonPower.ID); // TODO: 1 basic skill

//        retVal.add(Armaments.ID);
        retVal.add(DynamicCard.getID(WellPrepared.class));
        retVal.add(DynamicCard.getID(WellPrepared.class));
        retVal.add(DynamicCard.getID(WellPrepared.class));
        retVal.add(DynamicCard.getID(WellPrepared.class));
        retVal.add(DynamicCard.getID(WellPrepared.class));
        retVal.add(DynamicCard.getID(WellPrepared.class));

        // TODO: need to store tags / abilities? on file if you restart game? (CardCrawlGame.metricData?)
        // TODO: deck view is not showing correct tags? tags should be for the card itself and not just combat
        // TODO: upgrade view but for fire/ice morphose. use HandCardSelectScreen SpireReturn (https://github.com/kiooeht/ModTheSpire/wiki/SpireReturn)?

//        // common attack
//        retVal.add(Strike_Sorcerer.ID);
//        retVal.add(DynamicCard.getID(PillarOfFlame.class));
//        retVal.add(DynamicCard.getID(FrozenTomb.class));
//        retVal.add(DynamicCard.getID(Fireball.class));
//        retVal.add(DynamicCard.getID(Thaw.class));
//        retVal.add(DynamicCard.getID(FrostArrow.class)); // TODO
//        retVal.add(DynamicCard.getID(LostKnowledge.class));
//        retVal.add(DynamicCard.getID(SorcerersRaid.class));
//        retVal.add(DynamicCard.getID(LastResort.class));
//        // common skill
//        retVal.add(Defend_Sorcerer.ID);
//        retVal.add(DynamicCard.getID(IceBlock.class));
//        retVal.add(DynamicCard.getID(SparkingTendril.class));
//        retVal.add(DynamicCard.getID(CrystalProtection.class));
//        retVal.add(DynamicCard.getID(Congeal.class));
//        retVal.add(DynamicCard.getID(FireShield.class)); // TODO
//        retVal.add(DynamicCard.getID(MagicalCloak.class));
//        retVal.add(DynamicCard.getID(DiscardedDefense.class));
//        retVal.add(DynamicCard.getID(OneWithNothing.class));
//        retVal.add(DynamicCard.getID(Harmony.class));
//
//        // uncommon attack
//        retVal.add(DynamicCard.getID(MirrorForce.class));
//        retVal.add(DynamicCard.getID(Scorch.class));
//        retVal.add(DynamicCard.getID(Explosion.class));
//        retVal.add(DynamicCard.getID(ArcaneBarrage.class));
//        // uncommon skill
//        retVal.add(DynamicCard.getID(Dispell.class));
//        retVal.add(DynamicCard.getID(Cleanse.class));
//        retVal.add(DynamicCard.getID(BuriedAlive.class));
//        retVal.add(DynamicCard.getID(ForgottenConduit.class));
//        retVal.add(DynamicCard.getID(Tradeoff.class));
//        retVal.add(DynamicCard.getID(UnknownEcho.class));
//        retVal.add(DynamicCard.getID(FrostArmor.class));
//        retVal.add(DynamicCard.getID(Implosion.class));
//        retVal.add(DynamicCard.getID(ArcaneProtection.class));
//        retVal.add(DynamicCard.getID(ElementalStorm.class));
//        // uncommon power
//        retVal.add(DynamicCard.getID(UneducatedGuess.class));
//        retVal.add(DynamicCard.getID(WallOfFire.class));
//        retVal.add(DynamicCard.getID(PastEmbrace.class));
//        retVal.add(DynamicCard.getID(FrostShock.class));
//
//        // rare attack
//        retVal.add(DynamicCard.getID(Decay.class));
//        retVal.add(DynamicCard.getID(Ignite.class));
//        // rare skill
//        retVal.add(DynamicCard.getID(Renounce.class));
//        retVal.add(DynamicCard.getID(Elementmorphose.class));
//        retVal.add(DynamicCard.getID(WellPrepared.class));
//        retVal.add(DynamicCard.getID(UnseenHelper.class));
//        retVal.add(DynamicCard.getID(StrongAffinity.class));
//        retVal.add(DynamicCard.getID(Rethinking.class));
//        // rare power
//        retVal.add(DynamicCard.getID(Infernophoenix.class));
//        retVal.add(DynamicCard.getID(Cryophoenix.class));
//        retVal.add(DynamicCard.getID(CollateralDamage.class));

        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(DynamicRelic.getID(ElementalConstruct.class));

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_ORANGE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return KirbyDeeMod.SORCERER_ORANGE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Scorch(); // TODO
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheSorcerer(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return KirbyDeeMod.SORCERER_ORANGE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return KirbyDeeMod.SORCERER_ORANGE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
