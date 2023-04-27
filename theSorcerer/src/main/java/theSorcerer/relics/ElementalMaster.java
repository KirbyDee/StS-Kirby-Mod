package theSorcerer.relics;

import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.powers.buff.PresenceOfMindPower;
import theSorcerer.powers.debuff.ElementlessPower;

public class ElementalMaster extends DynamicRelic {

    public ElementalMaster() {
        super(
                ElementalMaster.class,
                RelicTier.UNCOMMON,
                LandingSound.MAGICAL
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip(ElementlessPower.class);
        addTip("Elemental Card", "Elemental Affinity");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
