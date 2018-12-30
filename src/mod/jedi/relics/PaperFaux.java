package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

// If you wonder how this works, go check paper patches in patches folder.

public class PaperFaux
    extends CustomRelic
{
    public static final String ID = "jedi:paperfaux";
    public static final String IMG_PATH = "resources/jedi/images/relics/beta_rock.png";

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
