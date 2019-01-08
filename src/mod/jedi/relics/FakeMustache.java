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
    public static final String IMG_PATH = "resources/jedi/images/relics/fakemustache.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);


    public FakeMustache() {
        super(ID, IMG, RelicTier.SHOP,LandingSound.FLAT);
    }


    public void onSpendGold()
    {
        AbstractDungeon.player.heal(5);
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
