package jedi.relics;

public class MainCommand
    extends AbstractJediRelic
{
    public static final String ID = "jedi:maincommand";

    public MainCommand()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
