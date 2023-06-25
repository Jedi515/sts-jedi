package jedi.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

    public CryingMask()
    {
        super(ID, RelicTier.SHOP, LandingSound.FLAT);
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(mask.name, "y");
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2] + FontHelper.colorString(mask.name, "y") + DESCRIPTIONS[3]));
        this.initializeTips();
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if (!canBuySecond)
        {
            ShopScreen.purgeCost += 25;
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
