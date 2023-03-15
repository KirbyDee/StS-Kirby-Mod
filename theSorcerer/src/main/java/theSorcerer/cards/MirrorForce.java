package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.MirrorForceAction;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

public class MirrorForce extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    // --- VALUES END ---

    public MirrorForce() {
        super(
                DynamicCard.InfoBuilder(MirrorForce.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .abilities(CardAbility.EXHAUST, CardAbility.ETHEREAL)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new MirrorForceAction(
                        player,
                        monster
                )
        );
    }

    @Override
    protected void upgradeValues() {
        AbstractCardPatch.abilities.get(this).remove(CardAbility.ETHEREAL);
    }
}
