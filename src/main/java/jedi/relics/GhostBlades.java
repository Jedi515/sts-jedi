package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;

public class GhostBlades
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ghostblades";

    public GhostBlades()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, RelicType.GREEN);
        this.description = DESCRIPTIONS[0];
        cardToPreview = new Shiv();
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
