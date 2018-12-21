package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PaperFaux
    extends CustomRelic
{
    public static final String ID = "jedi:papereegle";
    public static final String IMG_PATH = "resources/images/relics/beta_rock.png";

    public PaperFaux() {
        super(ID, new Texture(IMG_PATH), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new PaperFaux();
    }
}
