package theSorcerer.monsters.special;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.monsters.SorcererEnemyType;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.debuff.PolymorphPower;

public class Sheep extends AbstractMonster {

    public static final String ID = "Sheep";

    private static final MonsterStrings monsterStrings;

    public static final String[] MOVES;

    public static final String[] DIALOG;

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(DynamicDungeon.makeID(Sheep.class));
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }

    private static final int HP = 1;

    public Sheep(
            AbstractMonster monster
    ) {
        super(
                "Sheep (" + monster.name + ")",
                ID,
                HP,
                0.0F,
                0.0F,
                160.0F,
                160.0F,
                "theSorcererResources/images/monsters/special/sheep.png"
        );
        this.type = SorcererEnemyType.SPECIAL;
        this.drawX = monster.drawX;
        this.drawY = monster.drawY;
        this.dialogX = 0.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        this.maxHealth = HP;
        this.currentHealth = HP;
    }

    @Override
    public void takeTurn() {
        DynamicDungeon.addToBot(
                new TalkAction(
                        this,
                        DIALOG[AbstractDungeon.aiRng.random(1)]
                )
        );
    }

//    @Override
//    public void update() {
//        super.update();
//        this.animY = MathUtils.cosDeg((float)(System.currentTimeMillis() / 6L % 360L)) * 6.0F * Settings.scale;
//    }

    private void onDamage() {
        getPower(DynamicPower.getID(PolymorphPower.class)).onSpecificTrigger();
    }

    @Override
    public void damage(DamageInfo info) {
        onDamage();
    }

    @Override
    public void die() {
        onDamage();
    }

    @Override
    public void die(boolean triggerRelics) {
        onDamage();
    }

    @Override
    protected void getMove(int num) {
        setMove((byte) 0, Intent.UNKNOWN);
    }
}
