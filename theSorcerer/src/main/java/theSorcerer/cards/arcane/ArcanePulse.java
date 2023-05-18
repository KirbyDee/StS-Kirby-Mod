package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.ArcanePulseAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class ArcanePulse extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 2;
    // --- VALUES END ---

    public ArcanePulse() {
        super(
                DynamicCard.InfoBuilder(ArcanePulse.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .modifiers(CardModifier.EXHAUST, CardModifier.ETHEREAL)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ArcanePulseAction(
                        player,
                        monster,
                        this.upgraded
                )
        );
    }
}
