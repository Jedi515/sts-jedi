package jedi.patches;


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import jedi.relics.TokenOfGlory;
import jedi.relics.TokenOfMystery;
import jedi.relics.TokenOfSerenity;
import jedi.relics.TokenOfWealth;

import java.util.ArrayList;

public class TokenPatches
{
    private static float INCREMENT = 2.0F;

    @SpirePatch(clz = AbstractDungeon.class, method = "generateRoomTypes")
    public static class MapGenTokenPatch
    {
        @SpireInsertPatch(locator = Locator.class, localvars = {"shopCount", "restCount", "eliteCount", "eventCount"})
        public static void Insert(ArrayList<AbstractRoom> roomList, int availableRoomCount, @ByRef int[] shopCount, @ByRef int[] restCount, @ByRef int[] eliteCount, @ByRef int[] eventCount)
        {
            if (AbstractDungeon.player.hasRelic(TokenOfGlory.ID))
            {
                eliteCount[0] = (int)Math.ceil(eliteCount[0] * INCREMENT * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfGlory.ID)).count());
            }
            if (AbstractDungeon.player.hasRelic(TokenOfMystery.ID))
            {
                eventCount[0] = (int)Math.ceil(eventCount[0] * INCREMENT * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfMystery.ID)).count());
            }
            if (AbstractDungeon.player.hasRelic(TokenOfSerenity.ID))
            {
                restCount[0] = (int)Math.ceil(restCount[0] * INCREMENT * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfSerenity.ID)).count());
            }
            if (AbstractDungeon.player.hasRelic(TokenOfWealth.ID))
            {
                shopCount[0] = (int)Math.ceil(shopCount[0] * INCREMENT * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfWealth.ID)).count());
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "eventRoomChance");
            int[] lineMatch = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            lineMatch[0]++;
            return lineMatch;
        }
    }

//    @SpirePatch(clz = RestOption.class, method = SpirePatch.CONSTRUCTOR)
//    public static class SerenityTextPatch
//    {
//        @SpireInsertPatch(locator = SerenityTextLocator.class, localvars = {"healAmt"})
//        public static void Insert(RestOption __instance, boolean active, @ByRef int[] healAmt)
//        {
//            if (!ModHelper.isModEnabled(NightTerrors.ID) && AbstractDungeon.player.hasRelic(TokenOfSerenity.ID))
//            {
//                healAmt[0] = (int)((float)AbstractDungeon.player.maxHealth * 0.2F);
//            }
//        }
//    }
//
//    private static class SerenityTextLocator extends SpireInsertLocator
//    {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
//        {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasBlight");
//            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//        }
//    }
//
//    @SpirePatch(clz = CampfireSleepEffect.class, method = SpirePatch.CONSTRUCTOR)
//    public static class SerenityHealPatch
//    {
//        @SpireInsertPatch(locator = SerenityHealLocator.class, localvars = {"healAmount"})
//        public static void Insert(CampfireSleepEffect __instance, @ByRef int[] healAmount)
//        {
//            if (!ModHelper.isModEnabled(NightTerrors.ID) && AbstractDungeon.player.hasRelic(TokenOfSerenity.ID))
//            {
//                healAmount[0] = (int)((float)AbstractDungeon.player.maxHealth * 0.2F);
//            }
//        }
//    }
//
//    private static class SerenityHealLocator extends SpireInsertLocator
//    {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
//        {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
//            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//        }
//    }
//
//    @SpirePatch(clz = ShopScreen.class, method = "init")
//    public static class WealthPatch
//    {
//        public static void Postfix(ShopScreen __instance, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards)
//        {
//            if (AbstractDungeon.player.hasRelic(TokenOfWealth.ID))
//            {
//                ShopScreen.actualPurgeCost -= TokenOfWealth.effectiveness * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfWealth.ID)).count();
//                ShopScreen.actualPurgeCost = Math.max(ShopScreen.actualPurgeCost, 0);
//            }
//        }
//    }
//
//    @SpirePatch(clz = ShopScreen.class, method = "purgeCard")
//    public static class WealthPatch2
//    {
//        public static void Postfix()
//        {
//            if (AbstractDungeon.player.hasRelic(TokenOfWealth.ID))
//            {
//                ShopScreen.actualPurgeCost -= TokenOfWealth.effectiveness * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfWealth.ID)).count();
//                ShopScreen.actualPurgeCost = Math.max(ShopScreen.actualPurgeCost, 0);
//            }
//        }
//    }
//
//    @SpirePatch(clz = EventHelper.class, method = "roll", paramtypez = {Random.class})
//    public static class MysteryPatch
//    {
//        public static void Postfix(Random eventRng)
//        {
//            if (AbstractDungeon.player.hasPower(TokenOfGlory.ID))
//            ReflectionHacks.setPrivateStatic(EventHelper.class, "MONSTER_CHANCE",
//                    ((float)ReflectionHacks.getPrivateStatic(EventHelper.class, "MONSTER_CHANCE")) + 0.05F * AbstractDungeon.player.relics.stream().filter(r -> r.relicId.equals(TokenOfGlory.ID)).count());
//        }
//    }
}
