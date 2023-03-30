package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.StrongAffinityAction;
import theSorcerer.patches.cards.CardAbility;

public class StrongAffinity extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = -1;
    // --- VALUES END ---

    public StrongAffinity() {
        super(
                DynamicCard.InfoBuilder(StrongAffinity.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.NONE)
                        .abilities(CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new StrongAffinityAction(
                        player,
                        this.upgraded,
                        this.freeToPlayOnce,
                        this.energyOnUse
                )
        );
    }
}
