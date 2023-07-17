package jedi.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import jedi.relics.CryingMask;
import jedi.util.Wiz;

public class CryingMaskPatch
{
    @SpirePatch2(clz = ShopRoom.class, method = "updatePurge")
    @SpirePatch2(clz = ShopScreen.class, method = "updatePurge")
    public static class AllowSecondRemovalRoom
    {
        public static void Postfix()
        {
            CryingMask mask = (CryingMask) AbstractDungeon.player.getRelic(CryingMask.ID);
            if (mask == null) return;
            if (mask.canBuySecond && !AbstractDungeon.shopScreen.purgeAvailable)
            {
                mask.flash();
                AbstractDungeon.shopScreen.purgeAvailable = true;
                mask.canBuySecond = false;
                CryingMask.purgeCost = ShopScreen.purgeCost;
                ShopScreen.purgeCost = 0;
                ShopScreen.actualPurgeCost = 0;
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "purgeCard")
    public static class ReducePurgeCost
    {
        public static void Prefix()
        {
            CryingMask cm = (CryingMask) Wiz.adp().getRelic(CryingMask.ID);
            if (cm == null) return;
            if (AbstractDungeon.player.hasRelic(SmilingMask.ID) && !cm.canBuySecond)
            {
                AbstractDungeon.player.increaseMaxHp(5, true);
            }
            if (cm.canBuySecond)
            {
                ShopScreen.purgeCost -= 25;
            }
            else
            {
                CryingMask.purgeCost += 25;
            }
        }
    }
}
