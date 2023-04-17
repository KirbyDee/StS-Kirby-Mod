package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.CardAbility;

public class UnseenHelper extends SorcererCard {

    private static final Logger LOG = LogManager.getLogger(UnseenHelper.class.getName());


    // --- VALUES START ---
    private static final int ENERGY_GAIN = 1;
    // --- VALUES END ---

    private boolean inDiscardPile = false;

    public UnseenHelper() {
        super(
                DynamicCard.InfoBuilder(UnseenHelper.class)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.NONE)
                        .magicNumber(ENERGY_GAIN)
                        .abilities(CardAbility.UNPLAYABLE)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.inDiscardPile = false;
        checkAndGainEnergy();
    }

    @Override
    public void update() {
        super.update();
        checkAndGainEnergy();
    }

    private void checkAndGainEnergy() {
        if (!this.inDiscardPile && AbstractDungeon.player.discardPile.group.contains(this)) {
            LOG.info("UnseenHelper newly in discard pile -> gain " + this.magicNumber + " Energy");
            this.inDiscardPile = true;
            addToTop(new GainEnergyAction(this.magicNumber));
        }
        else if (this.inDiscardPile && !AbstractDungeon.player.discardPile.group.contains(this)) {
            LOG.info("UnseenHelper newly NOT in discard pile -> lose " + this.magicNumber + " Energy");
            this.inDiscardPile = false;
            addToTop(new LoseEnergyAction(this.magicNumber));
        }
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardEntomb(this);
    }
}
