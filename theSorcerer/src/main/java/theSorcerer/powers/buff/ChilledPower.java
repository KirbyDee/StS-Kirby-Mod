package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.powers.debuff.FrozenPower;

public class ChilledPower extends ElementEvolvePower<DexterityPower> {

    private static final Logger LOG = LogManager.getLogger(ChilledPower.class.getName());

    private static final String POWER_NAME = "ChilledPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public ChilledPower(
            final AbstractCreature owner,
            final int affinityAmount
    ) {
        super(owner, affinityAmount, POWER_ID, IceAffinityPower.POWER_ID);
    }

    protected DexterityPower createExtraPower(final int amount) {
        return new DexterityPower(this.owner, amount);
    }

    @Override
    protected String getExtraPowerId() {
        return DexterityPower.POWER_ID;
    }

    @Override
    protected AbstractCard.CardTags getAffinityLoseTag() {
        return SorcererCardTags.FIRE;
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChilledPower(this.owner, this.affinityAmount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // have to fully block an attack (currentBlock is the block after the attack happened)
        if (this.owner.currentBlock > 0 && this.affinityAmount > 0) {
            LOG.info("Fully blocked attack, apply Frozen amount " + this.affinityAmount);
            addToBot(
                    new ApplyPowerAction(
                            info.owner,
                            this.owner,
                            new FrozenPower(info.owner, this.affinityAmount, true)
                    )
            );
        }

        return damageAmount;
    }
}

