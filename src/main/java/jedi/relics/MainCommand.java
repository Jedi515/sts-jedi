package jedi.relics;

public class MainCommand
    extends AbstractJediRelic
{
    public static final String ID = "jedi:maincommand";

    public MainCommand()
    {
        super(ID,
                createTexture("jedi:command"),
                createOutline("jedi:command"),
                RelicTier.SPECIAL,
                LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
