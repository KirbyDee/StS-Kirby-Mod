package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.DangerousTempoAction;
import theSorcerer.patches.cards.CardAbility;

public class DangerousTempo extends SorcererCard {

    // --- VALUES START ---
    private static final int DRAW_CARD_AMOUNT = 4;
    private static final int UPGRADE_DRAW_CARD_AMOUNT = 1;
    private static final int ENERGY_GAIN = 2;
    private static final int UPGRADE_ENERGY_GAIN = 1;
    // --- VALUES END ---

    public DangerousTempo() {
        super(
                DynamicCard.InfoBuilder(DangerousTempo.class)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.NONE)
                        .magicNumber(DRAW_CARD_AMOUNT)
                        .secondMagicNumber(ENERGY_GAIN)
                        .abilities(CardAbility.UNPLAYABLE, CardAbility.FUTURITY)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    @Override
    public void triggerOnFuturity() {
        AbstractDungeon.player.hand.moveToExhaustPile(this);
        addToBot(
                new DangerousTempoAction(
                        this.magicNumber,
                        this.secondMagicNumber
                )
        );
    }

    @Override
    public void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DRAW_CARD_AMOUNT);
        upgradeSecondMagicNumber(UPGRADE_ENERGY_GAIN);
    }
}
