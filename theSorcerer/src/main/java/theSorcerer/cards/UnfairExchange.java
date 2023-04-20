package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.PutCardFromDiscardPileToExhaustPileAction;
import theSorcerer.actions.PutCardFromExhaustPileToDiscardPileAndGiveFlashbackAction;
import theSorcerer.patches.cards.CardAbility;

public class UnfairExchange extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DISCARD = 2;
    private static final int UPGRADE_DISCARD = -1;
    private static final int EXHAUST = 1;
    // --- VALUES END ---

    public UnfairExchange() {
        super(
                DynamicCard.InfoBuilder(UnfairExchange.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.NONE)
                        .magicNumber(DISCARD)
                        .secondMagicNumber(EXHAUST)
                        .abilities(CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(
                new PutCardFromDiscardPileToExhaustPileAction(this.magicNumber)
        );
        addToBot(
                new PutCardFromExhaustPileToDiscardPileAndGiveFlashbackAction(this.secondMagicNumber)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        else if (AbstractDungeon.player.discardPile.group.size() < this.magicNumber) {
            this.cantUseMessage = baseExtendedDescriptions[0] +
                    baseExtendedDescriptions[1] +
                    baseExtendedDescriptions[3] +
                    this.magicNumber +
                    baseExtendedDescriptions[this.upgraded ? 4 : 5];
            return false;
        }
        else if (AbstractDungeon.player.exhaustPile.group.size() < this.secondMagicNumber) {
            this.cantUseMessage = baseExtendedDescriptions[0] +
                    baseExtendedDescriptions[2] +
                    baseExtendedDescriptions[3] +
                    this.secondMagicNumber +
                    baseExtendedDescriptions[4];
            return false;
        }
        return true;
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DISCARD);
    }
}
