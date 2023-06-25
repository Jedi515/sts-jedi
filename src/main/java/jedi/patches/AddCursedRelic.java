//package jedi.patches;
//
//import basemod.ReflectionHacks;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.Hitbox;
//import com.megacrit.cardcrawl.shop.ShopScreen;
//import com.megacrit.cardcrawl.shop.StoreRelic;
//import javassist.CannotCompileException;
//import javassist.CtBehavior;
//import jedi.jedi;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//
//public class AddCursedRelic
//{
//    @SpirePatch(clz = ShopScreen.class, method = "initRelics")
//    public static class AddToScreen
//    {
//        public static void Postfix(ShopScreen __instance)
//        {
//            ArrayList<StoreRelic> relics = (ArrayList<StoreRelic>)ReflectionHacks.getPrivate(__instance, ShopScreen.class, "relics");
//            if (!jedi.cursedRelics.isEmpty())
//            {
//                Collections.shuffle(jedi.cursedRelics);
//                StoreRelic toAdd = new StoreRelic(jedi.returnRandomCursedRelic(), 3, __instance);
//                toAdd.price = Math.round((float)toAdd.price * AbstractDungeon.merchantRng.random(0.95F, 1.05F));
//                relics.add(toAdd);
//            }
//        }
//    }
//
//    @SpirePatch(clz = StoreRelic.class, method = "update")
//    public static class UpdateRender
//    {
//        @SpireInsertPatch(locator = Locator.class)
//        public static void Insert(StoreRelic __instance, float rugY)
//        {
//            int slot = (int)ReflectionHacks.getPrivate(__instance, StoreRelic.class, "slot");
//            __instance.relic.currentX = 1000.0F * Settings.scale + 120.0F * slot * Settings.scale;
//        }
//    }
//
//    private static class Locator extends SpireInsertLocator
//    {
//        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
//        {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "move");
//            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//        }
//    }
//
//}
