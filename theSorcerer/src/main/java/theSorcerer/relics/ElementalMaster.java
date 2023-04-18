package theSorcerer.relics;

// TODO: add power tip Elementless, also Heated, Chilled and Presence of mind
public class ElementalMaster extends DynamicRelic {

    public ElementalMaster() {
        super(
                ElementalMaster.class,
                RelicTier.UNCOMMON,
                LandingSound.MAGICAL
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
