package jedi.relics;

public class PortableTent
    extends AbstractJediRelic
{
    public static final String ID = "jedi:arcanewood";

    public PortableTent()
    {
        super(ID, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        counter = -2;
    }

    public boolean canSpawn() {
        return f40();
    }
}
