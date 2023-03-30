package theSorcerer.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.buff.StrongAffinityPower;
import theSorcerer.powers.buff.StrongFireAffinityPower;

public class StrongFireAffinity extends StrongAffinityChoose {

    public StrongFireAffinity() {
        super(DynamicCard.InfoBuilder(StrongFireAffinity.class));
    }

    @Override
    protected StrongAffinityPower createStrongAffinityPower(
            AbstractPlayer player,
            int amount
    ) {
        return new StrongFireAffinityPower(
                player,
                amount
        );
    }
}
