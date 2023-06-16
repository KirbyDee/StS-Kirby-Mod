package theSorcerer.cards.fire;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSorcerer.actions.DamageMultipleEnemiesAction;
import theSorcerer.cards.DynamicCard;

import java.util.Arrays;
import java.util.Iterator;

public class PillarOfFlame extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE_PRIMARY = 8;
    private static final int UPGRADE_DAMAGE_PRIMARY = 2;
    private static final int DAMAGE_SECONDARY = 4;
    private static final int UPGRADE_DAMAGE_SECONDARY = 1;
    private int[] multiMagicNumber;
    // --- VALUES END ---

    public PillarOfFlame() {
        super(
                DynamicCard.InfoBuilder(PillarOfFlame.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ALL_ENEMY)
                        .damage(DAMAGE_PRIMARY)
                        .magicNumber(DAMAGE_SECONDARY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        MonsterGroup monsterGroup = AbstractDungeon.getCurrRoom().monsters;
        AbstractMonster randomMonster = monsterGroup.getRandomMonster(true);

        addToBot(
                new DamageAction(
                        randomMonster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE
                )
        );

        this.multiMagicNumber[monsterGroup.monsters.indexOf(randomMonster)] = -1;
        addToBot(
                new DamageMultipleEnemiesAction(
                        player,
                        this.multiMagicNumber,
                        this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        this.isMagicNumberModified = false;
        computeCardMagicNumberDamage();
    }

    @Override
    public void calculateCardDamage(AbstractMonster monster) {
        super.calculateCardDamage(monster);

        this.isMagicNumberModified = false;
        computeCardMagicNumberDamage();
    }

    private void computeCardMagicNumberDamage() {
        float[] tmp = new float[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
        Arrays.fill(tmp, (float) this.baseMagicNumber);

        Iterator<?> var5;
        AbstractPower p;
        for (int i = 0; i < tmp.length; ++i) {
            var5 = AbstractDungeon.player.relics.iterator();

            while (var5.hasNext()) {
                AbstractRelic r = (AbstractRelic) var5.next();
                tmp[i] = r.atDamageModify(tmp[i], this);
                if (this.baseMagicNumber != (int) tmp[i]) {
                    this.isMagicNumberModified = true;
                }
            }

            for (var5 = AbstractDungeon.player.powers.iterator(); var5.hasNext(); tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn, this)) {
                p = (AbstractPower) var5.next();
            }

            tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], this.damageTypeForTurn, this);
            if (this.baseMagicNumber != (int)tmp[i]) {
                this.isMagicNumberModified = true;
            }
        }

        for (int i = 0; i < tmp.length; ++i) {
            for (var5 = AbstractDungeon.player.powers.iterator(); var5.hasNext(); tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn, this)) {
                p = (AbstractPower) var5.next();
            }
        }

        for (int i = 0; i < tmp.length; ++i) {
            if (tmp[i] < 0.0F) {
                tmp[i] = 0.0F;
            }
        }

        this.multiMagicNumber = new int[tmp.length];

        for (int i = 0; i < tmp.length; ++i) {
            if (this.baseMagicNumber != (int) tmp[i]) {
                this.isMagicNumberModified = true;
            }

            this.multiMagicNumber[i] = MathUtils.floor(tmp[i]);
        }

        this.magicNumber = this.multiMagicNumber[0];
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE_SECONDARY);
        upgradeMagicNumber(UPGRADE_DAMAGE_PRIMARY);
    }

    public void clearPowers() {
        super.clearPowers();
        this.isMagicNumberModified = false;
    }
}
