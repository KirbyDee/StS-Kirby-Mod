package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.buff.InfernophoenixPower;
public class Infernophoenix extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 2;
    // --- VALUES END ---

    public Infernophoenix() {
        super(
                DynamicCard.InfoBuilder(Infernophoenix.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.SELF)
                        .tags(CardTags.HEALING)
                        .abilities(CardAbility.AUTO)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        new InfernophoenixPower(p)
                )
        );
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardFuturity(this);
    }
}
