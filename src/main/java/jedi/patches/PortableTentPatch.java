package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.RestRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import jedi.relics.PortableTent;

import java.util.Arrays;
import java.util.Collections;

public class PortableTentPatch
{
    @SpireEnum public static EventHelper.RoomResult JEDI_REST;

    public static float REST_CHANCE = 0.05F;

    @SpirePatch(clz = EventHelper.class, method = "roll", paramtypez = {Random.class})
    public static class AddRestToEvent
    {
        public static SpireReturn<EventHelper.RoomResult> Prefix()
        {
            PortableTent pt = (PortableTent) AbstractDungeon.player.getRelic(PortableTent.ID);
            if (pt != null && pt.counter == -2)
            {
                pt.counter++;
                return SpireReturn.Return(JEDI_REST);
            }
            return SpireReturn.Continue();
        }

        @SpireInsertPatch(locator = Locator.class, localvars = {"possibleResults", "fillIndex"})
        public static void Insert(Random eventRng, EventHelper.RoomResult[] possibleResults, @ByRef int[] fillIndex)
        {
            if (AbstractDungeon.player.hasRelic(PortableTent.ID))
            {
                int restSize = (int)(PortableTentPatch.REST_CHANCE * 100.0F);
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom)
                {
                    restSize = 0;
                }
                Arrays.fill(possibleResults, Math.min(99, fillIndex[0]), (int)Math.min(100, fillIndex[0] + restSize), PortableTentPatch.JEDI_REST);
                fillIndex[0] += restSize;
            }
        }

        public static EventHelper.RoomResult Postfix(EventHelper.RoomResult __result, Random eventRng)
        {
            if (AbstractDungeon.player.hasRelic(PortableTent.ID))
            {
                if (__result == PortableTentPatch.JEDI_REST)
                {
                    PortableTentPatch.REST_CHANCE = 0.05F;
                }
                else
                {
                    PortableTentPatch.REST_CHANCE += 0.05F;
                }
            }

            return __result;
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, Collections.singletonList(finalMatcher), finalMatcher);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "generateRoom")
    public static class RoomGenerationPatch
    {
        public static SpireReturn Prefix(AbstractDungeon __instance, EventHelper.RoomResult roomType)
        {
            if (roomType == JEDI_REST)
            {
                return SpireReturn.Return(new RestRoom());
            }
            return SpireReturn.Continue();
        }
    }
}
