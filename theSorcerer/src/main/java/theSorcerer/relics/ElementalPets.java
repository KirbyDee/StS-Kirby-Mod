package theSorcerer.relics;

public class ElementalPets extends DynamicRelic {

    public static final int AFFINITY_GAIN_PLUS = 1;

    public ElementalPets() {
        super(
                ElementalPets.class,
                RelicTier.RARE,
                LandingSound.MAGICAL
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip("Elemental Affinity");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AFFINITY_GAIN_PLUS + DESCRIPTIONS[1];
    }
}
