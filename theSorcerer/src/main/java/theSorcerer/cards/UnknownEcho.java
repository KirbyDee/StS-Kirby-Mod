package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.UnknownEchoAction;

public class UnknownEcho extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int CARDS_TO_DRAW = 2;
    private static final int UPGRADE_CARDS_TO_DRAW = 1;
    private static final int CARDS_TO_PUT_ON_DECK = 1;
    // --- VALUES END ---

    public UnknownEcho() {
        super(
                DynamicCard.InfoBuilder(UnknownEcho.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(CARDS_TO_DRAW)
                        .tags(SorcererCardTags.ELEMENTLESS)
                        .secondMagicNumber(CARDS_TO_PUT_ON_DECK)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DrawCardAction(player, this.magicNumber)
        );
        addToBot(
                new UnknownEchoAction(this.secondMagicNumber)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        boolean canUse = super.canUse(player, monster);
        if (!canUse) {
            return false;
        }
        else if (!DynamicDungeon.hasElementless()) {
            this.cantUseMessage = baseExtendedDescriptions[0];
            return false;
        }
        return true;
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_CARDS_TO_DRAW);
    }
}
