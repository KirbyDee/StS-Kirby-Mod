package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.SiphonAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class Siphon extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int CARDS_TO_EXHAUST = 1;
    private static final int UPGRADE_CARDS_TO_EXHAUST = -2; // to set it to -1
    // --- VALUES END ---

    public Siphon() {
        super(
                DynamicCard.InfoBuilder(Siphon.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .magicNumber(CARDS_TO_EXHAUST)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new SiphonAction(
                        this.magicNumber
                )
        );
    }

    @Override
    public void upgradeValues() {
        upgradeMagicNumber(UPGRADE_CARDS_TO_EXHAUST);
    }
}
