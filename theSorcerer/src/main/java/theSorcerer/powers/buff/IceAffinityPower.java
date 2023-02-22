package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.cards.SorcererCardTags;

public class IceAffinityPower extends ElementAffinityPower<ChilledPower> {

    public static final String POWER_NAME = "IceAffinityPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public IceAffinityPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(owner, amount, POWER_ID);
    }

    @Override
    public AbstractPower makeCopy() {
        return new IceAffinityPower(this.owner, this.amount);
    }

    @Override
    protected AbstractCard.CardTags getAffinityLoseTag() {
        return SorcererCardTags.FIRE;
    }

    @Override
    protected ChilledPower createEvolvePower() {
        return new ChilledPower(this.owner, this.amount);
    }
}
