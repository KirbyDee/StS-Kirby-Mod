package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class AngerManagement extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int STRENGTH_LOSS = 8;
    private static final int UPGRADE_STRENGTH_LOSS = 2;
    // --- VALUES END ---

    public AngerManagement() {
        super(
                DynamicCard.InfoBuilder(AngerManagement.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(STRENGTH_LOSS)
                        .modifiers(CardModifier.FUTURITY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.GOLD_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        }
        else {
            addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.GOLD_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }

        AbstractDungeon.getCurrRoom().monsters.monsters.forEach(m -> applyStrengthDebuff(player, m));
        AbstractDungeon.getCurrRoom().monsters.monsters.stream()
                .filter(m -> !m.hasPower("Artifact"))
                .forEach(m -> applyStrengthBuff(player, m));
    }

    private void applyStrengthDebuff(final AbstractPlayer player, final AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        monster,
                        player,
                        new StrengthPower(monster, -this.magicNumber),
                        -this.magicNumber,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    private void applyStrengthBuff(final AbstractPlayer player, final AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        monster,
                        player,
                        new GainStrengthPower(monster, this.magicNumber),
                        this.magicNumber,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    @Override
    public void triggerOnFuturity() {
        superFlash();
        DynamicDungeon.applyElementless();
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_STRENGTH_LOSS);
    }
}
