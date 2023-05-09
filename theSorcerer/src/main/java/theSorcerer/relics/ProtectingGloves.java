package theSorcerer.relics;

public class ProtectingGloves extends DynamicRelic {

    public ProtectingGloves() {
        super(
                ProtectingGloves.class,
                RelicTier.SHOP,
                LandingSound.SOLID
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip("Arcane", "Elemental Affinity");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
