package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.util.TextureLoader;

public class FakeMustache
    extends CustomRelic
{
    public static final String ID = "jedi:fakemustache";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);


    public FakeMustache() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP,LandingSound.FLAT);
    }


    public void onSpendGold()
    {
        AbstractDungeon.player.heal(5);
    }

    public void onEquip()
    {
        AbstractDungeon.player.heal(10);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new FakeMustache();
    }

}
