package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.DangerousTempoAction;
import theSorcerer.modifiers.CardModifier;

public class DangerousTempo extends SorcererCard {

    // --- VALUES START ---
    private static final int DRAW_CARD_AMOUNT = 3;
    private static final int UPGRADE_DRAW_CARD_AMOUNT = 1;
    private static final int ENERGY_GAIN = 2;
    // --- VALUES END ---

    public DangerousTempo() {
        super(
                DynamicCard.InfoBuilder(DangerousTempo.class)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.NONE)
                        .magicNumber(DRAW_CARD_AMOUNT)
                        .secondMagicNumber(ENERGY_GAIN)
                        .modifiers(CardModifier.UNPLAYABLE, CardModifier.FUTURITY)
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
                        this.secondMagicNumber,
                        this.upgraded
                )
        );
    }

    @Override
    public void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DRAW_CARD_AMOUNT);
    }
}
