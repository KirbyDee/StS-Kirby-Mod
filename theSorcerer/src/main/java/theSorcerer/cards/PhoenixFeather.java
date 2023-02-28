package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.buff.PhoenixFeatherPower;

public class PhoenixFeather extends SorcererCard {

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
                        .abilities(CardAbility.AUTO, CardAbility.FUTURITY, CardAbility.FIRE)
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
