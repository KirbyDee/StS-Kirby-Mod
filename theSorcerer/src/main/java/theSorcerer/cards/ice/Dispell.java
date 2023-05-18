package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.actions.RandomPowerRemoveAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class Dispell extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int POWERS_TO_REMOVE = 1;
    private static final int UPGRADE_POWERS_TO_REMOVE = 1;
    // --- VALUES END ---

    public Dispell() {
        super(
                DynamicCard.InfoBuilder(Dispell.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .modifiers(CardModifier.EXHAUST)
                        .magicNumber(POWERS_TO_REMOVE)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new RandomPowerRemoveAction(
                        monster,
                        AbstractPower.PowerType.BUFF,
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_POWERS_TO_REMOVE);
    }
}
