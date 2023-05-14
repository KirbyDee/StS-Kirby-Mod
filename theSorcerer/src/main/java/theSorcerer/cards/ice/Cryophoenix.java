package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.status.Frostbite;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.buff.CryophoenixPower;

public class Cryophoenix extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 2;
    // --- VALUES END ---

    public Cryophoenix() {
        super(
                DynamicCard.InfoBuilder(Cryophoenix.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.SELF)
                        .abilities(CardAbility.AUTO)
        );
        this.cardsToPreview = new Frostbite();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        new CryophoenixPower(p)
                )
        );
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardFuturity(this);
    }
}
