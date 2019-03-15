package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import mod.jedi.util.TextureLoader;

public class CryingMask
    extends CustomRelic
{
    public static final String ID = "jedi:cryingmask";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public boolean canBuySecond = true;
    public static final SmilingMask mask = new SmilingMask();

    public CryingMask()
    {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(mask.name, "y");
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2] + FontHelper.colorString(mask.name, "y") + DESCRIPTIONS[3]));
        this.initializeTips();
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if (!canBuySecond)
        {
            ShopScreen.purgeCost += 25;
        }
        canBuySecond = true;
    }
    //More info under patches/CryingMaskPatch
}
