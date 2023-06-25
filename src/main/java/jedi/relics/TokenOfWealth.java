package jedi.relics;

public class TokenOfWealth
    extends AbstractJediRelic
{
    public static final String ID = "jedi:tokenofwealth";
    public static final int effectiveness = 20;

    public TokenOfWealth()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
