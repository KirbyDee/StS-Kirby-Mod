package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.powers.buff.AwingShoutPower;
import theSorcerer.powers.buff.PastEmbracePower;

public class AwingShout extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int WEAK_VULNERABILITY_AMOUNT = 1;
    private static final int UPGRADE_WEAK_VULNERABILITY_AMOUNT = 1;
    // --- VALUES END ---

    public AwingShout() {
        super(
                DynamicCard.InfoBuilder(AwingShout.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.RARE)
                        .tags(SorcererCardTags.ELEMENTLESS)
                        .magicNumber(WEAK_VULNERABILITY_AMOUNT)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new AwingShoutPower(
                                player,
                                this.magicNumber
                        ),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_WEAK_VULNERABILITY_AMOUNT);
    }
}
