package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.buff.PhoenixFeatherPower;

// TODO: ice variant needed
public class PhoenixFeather extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 2;
    // --- VALUES END ---

    public PhoenixFeather() {
        super(
                DynamicCard.InfoBuilder(PhoenixFeather.class)
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
        this.addToTop(
                new ApplyPowerAction(
                        p,
                        p,
                        new PhoenixFeatherPower(p)
                )
        );
    }

    @Override
    protected void upgradeValues() {
        AbstractCardPatch.abilities.get(this).add(CardAbility.FUTURITY);
    }
}
