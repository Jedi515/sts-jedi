package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mod.jedi.util.TextureLoader;

public class Catwich
    extends CustomRelic
{
    public static final String ID = "jedi:catwich";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private boolean triggered;

    public Catwich()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
    }

    public void onManualDiscard()
    {
        if (!this.triggered && AbstractDungeon.player.discardPile.getTopCard().canUpgrade())
        {
            this.triggered = true;
            this.flash();
            AbstractDungeon.player.discardPile.getTopCard().upgrade();
        }
    }

    public void atTurnStart()
    {
        this.triggered = false;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

}
