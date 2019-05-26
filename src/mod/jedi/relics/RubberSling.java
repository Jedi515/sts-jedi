package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mod.jedi.util.TextureLoader;

public class RubberSling
    extends CustomRelic
{
    public static final String ID = "jedi:rubbersling";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public boolean triggered = false;

    public RubberSling()
    {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart()
    {
        this.triggered = false;
    }

    @Override
    public void onManualDiscard()
    {
        if (!this.triggered)
        {
            this.triggered = true;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }
}
