package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.debuff.IceAgePower;

public class IceAge extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int NO_BLOCK_GAIN_TURNS = 1;
    private static final int UPGRADE_NO_BLOCK_GAIN_TURNS = 1;
    // --- VALUES END ---

    public IceAge() {
        super(
                DynamicCard.InfoBuilder(IceAge.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .magicNumber(NO_BLOCK_GAIN_TURNS)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.getMonsters().monsters
                .forEach(m -> iceAgeUse(player, m));
    }

    private void iceAgeUse(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new RemoveAllBlockAction(
                        monster,
                        player
                )
        );
        addToBot(
                new ApplyPowerAction(
                        monster,
                        player,
                        new IceAgePower(
                                monster,
                                player,
                                this.magicNumber,
                                true
                        ),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_NO_BLOCK_GAIN_TURNS);
    }
}
