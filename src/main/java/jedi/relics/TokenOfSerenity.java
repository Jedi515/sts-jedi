package jedi.relics;

public class TokenOfSerenity
    extends AbstractJediRelic
{
    public static final String ID = "jedi:tokenofserenity";
    public static final int effectiveness = 20;

    public TokenOfSerenity()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
