package mod.jedi.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import mod.jedi.relics.CryingMask;

public class CryingMaskPatch
{
    @SpirePatch(clz = ShopScreen.class, method = "updatePurge")
    public static class AllowSecondRemovalScreen
    {
        public static void Postfix(ShopScreen __instance)
        {
            if (AbstractDungeon.player.hasRelic(CryingMask.ID))
            {
                CryingMask mask = (CryingMask) AbstractDungeon.player.getRelic(CryingMask.ID);
                if (mask.canBuySecond && !AbstractDungeon.shopScreen.purgeAvailable)
                {
                    mask.canBuySecond = false;
                    AbstractDungeon.shopScreen.purgeAvailable = true;
                }
            }
        }
    }

    @SpirePatch(clz = ShopRoom.class, method = "updatePurge")
    public static class AllowSecondRemovalRoom
    {
        public static void Postfix(ShopRoom __instance)
        {
            if (AbstractDungeon.player.hasRelic(CryingMask.ID))
            {
                CryingMask mask = (CryingMask) AbstractDungeon.player.getRelic(CryingMask.ID);
                if (mask.canBuySecond && !AbstractDungeon.shopScreen.purgeAvailable)
                {
                    mask.flash();
                    mask.canBuySecond = false;
                    AbstractDungeon.shopScreen.purgeAvailable = true;
                }
            }
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "purgeCard")
    public static class ReducePurgeCost
    {
        public static void Prefix()
        {
            if (AbstractDungeon.player.hasRelic(SmilingMask.ID) && AbstractDungeon.player.hasRelic(CryingMask.ID))
            {
                if (!((CryingMask) AbstractDungeon.player.getRelic(CryingMask.ID)).canBuySecond)
                {
                    AbstractDungeon.player.increaseMaxHp(5, true);
                }
            }
            ShopScreen.purgeCost -= 25;
        }
    }
}
