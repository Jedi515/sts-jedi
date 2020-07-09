package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import mod.jedi.util.TextureLoader;

public class GhostBlades
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ghostblades";

    public GhostBlades()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
        cardToPreview = new Shiv();
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
