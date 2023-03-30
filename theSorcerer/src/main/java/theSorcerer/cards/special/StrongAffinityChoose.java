package theSorcerer.cards.special;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.buff.StrongAffinityPower;

public abstract class StrongAffinityChoose extends DynamicCard {

    public StrongAffinityChoose(
            DynamicCard.InfoBuilder builder
    ) {
        super(
                builder
                        .type(CardType.POWER)
                        .rarity(CardRarity.SPECIAL)
                        .build()
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        createStrongAffinityPower(player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    protected abstract StrongAffinityPower createStrongAffinityPower(AbstractPlayer player, int amount);
}
