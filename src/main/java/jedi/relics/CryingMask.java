package jedi.relics;

import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;

public class CryingMask
    extends AbstractJediRelic
{
    public static final String ID = "jedi:cryingmask";
    public boolean canBuySecond = true;
    public static final SmilingMask mask = new SmilingMask();
    public static int purgeCost;

    public CryingMask()
    {
        super(ID, RelicTier.SHOP, LandingSound.FLAT);
        description = DESCRIPTIONS[0] + FontHelper.colorString(mask.name, "y");
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2] + FontHelper.colorString(mask.name, "y") + DESCRIPTIONS[3]));
        initializeTips();
    }

    public boolean canSpawn() {
        return f40();
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if (!canBuySecond)
        {
            ShopScreen.purgeCost = purgeCost;
        }
        canBuySecond = true;
    }

    @Override
    public int getPrice()
    {
        return super.getPrice() / 2;
    }

    //More info under patches/CryingMaskPatch
}
