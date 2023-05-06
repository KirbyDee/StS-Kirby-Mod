package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.buff.ElementalCharmPower;

public class ElementalCharm extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int NUMBER_OF_CHARMS = 1;
    // --- VALUES END ---

    public ElementalCharm() {
        super(
                DynamicCard.InfoBuilder(ElementalCharm.class)
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
                        new ElementalCharmPower(
                                player,
                                this.magicNumber
                        )
                )
        );
    }

    @Override
    protected void upgradeValues() {
        AbstractCardPatch.abilities.get(this).add(CardAbility.INNATE);
    }
}
