package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import mod.jedi.util.TextureLoader;

public class OminousLoanNote
    extends CustomRelic
{
    public static final String ID = "jedi:ominousloannote";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public boolean paidOff = false;

    public OminousLoanNote()
    {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public void onEquip()
    {
        int goldGiven = 475 + AbstractDungeon.relicRng.random(50);
        AbstractDungeon.effectList.add(new RainingGoldEffect(goldGiven));
        AbstractDungeon.player.gainGold(goldGiven);
        this.counter = (int)(goldGiven * 1.25F);
        this.paidOff = false;
    }

    public String getUpdatedDescription()
    {
        if (this.paidOff) return DESCRIPTIONS[1];

        return DESCRIPTIONS[0];
    }

    public int getPrice()
    {
        return 0;
    }
}
