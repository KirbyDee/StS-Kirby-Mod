package theSorcerer.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.buff.StrongAffinityPower;
import theSorcerer.powers.buff.StrongIceAffinityPower;

public class StrongIceAffinity extends StrongAffinityChoose {

    public StrongIceAffinity() {
        super(DynamicCard.InfoBuilder(StrongIceAffinity.class));
    }

    @Override
    protected StrongAffinityPower createStrongAffinityPower(
            AbstractPlayer player,
            int amount
    ) {
        return new StrongIceAffinityPower(
                player,
                amount
        );
    }
}
