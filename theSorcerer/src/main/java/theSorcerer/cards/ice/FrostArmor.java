package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public class FrostArmor extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // --- VALUES END ---

    public FrostArmor() {
        super(
                DynamicCard.InfoBuilder(FrostArmor.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .abilities(CardAbility.FLASHBACK)
                        .block(BLOCK)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}
