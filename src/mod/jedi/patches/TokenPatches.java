package mod.jedi.patches;


import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.NightTerrors;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mod.jedi.relics.TokenOfGlory;
import mod.jedi.relics.TokenOfMystery;
import mod.jedi.relics.TokenOfSerenity;
import mod.jedi.relics.TokenOfWealth;

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
                eliteCount[0] = (int)Math.ceil(eliteCount[0] * INCREMENT);
            }
            else if (AbstractDungeon.player.hasRelic(TokenOfMystery.ID))
            {
                eventCount[0] = (int)Math.ceil(eventCount[0] * INCREMENT);
            }
            else if (AbstractDungeon.player.hasRelic(TokenOfSerenity.ID))
            {
                restCount[0] = (int)Math.ceil(restCount[0] * INCREMENT);
            }
            else if (AbstractDungeon.player.hasRelic(TokenOfWealth.ID))
            {
                shopCount[0] = (int)Math.ceil(shopCount[0] * INCREMENT);
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

    @SpirePatch(clz = RestOption.class, method = SpirePatch.CONSTRUCTOR)
    public static class SerenityTextPatch
    {
        @SpireInsertPatch(locator = SerenityTextLocator.class, localvars = {"healAmt"})
        public static void Insert(RestOption __instance, boolean active, @ByRef int[] healAmt)
        {
            if (!ModHelper.isModEnabled(NightTerrors.ID) && AbstractDungeon.player.hasRelic(TokenOfSerenity.ID))
            {
                healAmt[0] = (int)((float)AbstractDungeon.player.maxHealth * 0.2F);
            }
        }
    }

    private static class SerenityTextLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasBlight");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpirePatch(clz = CampfireSleepEffect.class, method = SpirePatch.CONSTRUCTOR)
    public static class SerenityHealPatch
    {
        @SpireInsertPatch(locator = SerenityHealLocator.class, localvars = {"healAmount"})
        public static void Insert(CampfireSleepEffect __instance, @ByRef int[] healAmount)
        {
            if (!ModHelper.isModEnabled(NightTerrors.ID) && AbstractDungeon.player.hasRelic(TokenOfSerenity.ID))
            {
                healAmount[0] = (int)((float)AbstractDungeon.player.maxHealth * 0.2F);
            }
        }
    }

    private static class SerenityHealLocator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "init")
    public static class WealthPatch
    {
        public static void Postfix(ShopScreen __instance, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards)
        {
            if (AbstractDungeon.player.hasRelic(TokenOfWealth.ID))
            {
                __instance.applyDiscount(1.2F, true);
            }
        }
    }

    @SpirePatch(clz = EventHelper.class, method = "roll", paramtypez = {Random.class})
    public static class MysteryPatch
    {
        public static void Postfix(Random eventRng)
        {
            ReflectionHacks.setPrivateStatic(EventHelper.class, "MONSTER_CHANCE",
                    ((float)ReflectionHacks.getPrivateStatic(EventHelper.class, "MONSTER_CHANCE")) + 0.05F);
        }
    }
}
