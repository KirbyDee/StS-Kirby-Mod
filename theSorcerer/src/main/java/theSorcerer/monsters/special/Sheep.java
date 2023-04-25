package theSorcerer.monsters.special;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.CustomSFXAction;
import theSorcerer.monsters.SorcererEnemyType;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.debuff.PolymorphPower;

import java.util.Random;

public class Sheep extends AbstractMonster {

    public static final String ID = "Sheep";

    private static final MonsterStrings monsterStrings;

    public static final String[] MOVES;

    public static final String[] DIALOG;

    private final AbstractMonster monster;

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(DynamicDungeon.makeID(Sheep.class));
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }

    public Sheep(
            AbstractMonster monster
    ) {
        super(
                "Sheep (" + monster.name + ")",
                ID,
                monster.maxHealth,
                0.0F,
                0.0F,
                160.0F,
                160.0F,
                "theSorcererResources/images/monsters/special/sheep.png"
        );
        this.monster = monster;
        this.type = SorcererEnemyType.SPECIAL;
        this.drawX = monster.drawX;
        this.drawY = monster.drawY;
        this.dialogX = 0.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        this.currentHealth = monster.currentHealth;
        super.addBlock(monster.currentBlock);
    }

    @Override
    public void takeTurn() {
        int index = AbstractDungeon.aiRng.random(2);
        String dialog = DIALOG[index];
        switch (index) {
            case 0:
                addToBot(new TalkAction(this, dialog));
                break;
            case 1:
                addToBot(getBaa());
                addToBot(new TalkAction(this, dialog));
                break;
            case 2:
            default:
                addToBot(getBaa());
                addToBot(new ShoutAction(this, dialog));
                break;
        }
    }

    private AbstractGameAction getBaa() {
        return new CustomSFXAction("SHEEP");
    }

    private void onDamage() {
        addToTop(getBaa());
        getPower(DynamicPower.getID(PolymorphPower.class)).onSpecificTrigger();
    }

    @Override
    public void damage(DamageInfo info) {
        onDamage();
        this.monster.damage(info);
    }

    @Override
    public void heal(int healAmount, boolean showEffect) {
        super.heal(healAmount, showEffect);
        this.monster.heal(healAmount, showEffect);
    }

    @Override
    public void die(boolean triggerRelics) {
        onDamage();
        this.monster.die(triggerRelics);
    }

    @Override
    protected void getMove(int num) {
        setMove((byte) 0, Intent.UNKNOWN);
    }

    @Override
    public void changeState(String stateName) {
        this.monster.changeState(stateName);
    }

    @Override
    public void increaseMaxHp(int amount, boolean showEffect) {
        super.increaseMaxHp(amount, showEffect);
        this.monster.increaseMaxHp(amount, showEffect);
    }

    @Override
    public void decreaseMaxHealth(int amount) {
        super.decreaseMaxHealth(amount);
        this.monster.decreaseMaxHealth(amount);
    }

    @Override
    public void updateAnimations() {
        super.updateAnimations();
        this.monster.updateAnimations();
    }

    @Override
    public void addBlock(int blockAmount) {
        super.addBlock(blockAmount);
        this.monster.addBlock(blockAmount);
    }

    @Override
    public void loseBlock(int amount, boolean noAnimation) {
        super.loseBlock(amount, noAnimation);
        this.monster.loseBlock(amount, noAnimation);
    }
}
