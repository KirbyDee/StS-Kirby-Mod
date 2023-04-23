package theSorcerer.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.DecayAction;
import theSorcerer.actions.PolymorphAction;
import theSorcerer.cards.special.Sheep;
import theSorcerer.patches.cards.CardAbility;

public class Polymorph extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int POLYMORPH_TURNS = 1;
    private static final int UPGRADE_POLYMORPH_TURNS = 1;
    // --- VALUES END ---

    public Polymorph() {
        super(
                DynamicCard.InfoBuilder(Polymorph.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .magicNumber(POLYMORPH_TURNS)
                        .abilities(CardAbility.EXHAUST)
        );

        this.cardsToPreview = new Sheep();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new PolymorphAction(
                        monster,
                        player,
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_POLYMORPH_TURNS);
    }
}
