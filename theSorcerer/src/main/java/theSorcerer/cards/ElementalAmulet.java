package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ElementalAmuletPower;

public class ElementalAmulet extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int NUMBER_OF_CHARMS = 1;
    // --- VALUES END ---

    public ElementalAmulet() {
        super(
                DynamicCard.InfoBuilder(ElementalAmulet.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(NUMBER_OF_CHARMS)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new ElementalAmuletPower(
                                player,
                                this.magicNumber
                        )
                )
        );
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardInnate(this);
    }
}
