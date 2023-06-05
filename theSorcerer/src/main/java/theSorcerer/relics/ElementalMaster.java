package theSorcerer.relics;

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
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
