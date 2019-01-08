package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.util.TextureLoader;

// If you wonder how this works, go check paper patches in patches folder.

public class PaperFaux
    extends CustomRelic
{
    public static final String ID = "jedi:paperfaux";
    public static final String IMG_PATH = "resources/jedi/images/relics/paperfaux.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);

    public PaperFaux() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.CLINK);
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
