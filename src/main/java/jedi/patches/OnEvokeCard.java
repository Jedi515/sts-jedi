package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import jedi.interfaces.onEvokeInterface;

public class OnEvokeCard
{

    @SpirePatch(clz = AbstractPlayer.class, method = "evokeOrb")
    @SpirePatch(clz = AbstractPlayer.class, method = "evokeWithoutLosingOrb")
    public static class OnEvokeLast
    {
        public static void Prefix(AbstractPlayer __instance)
        {
            if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(0) instanceof EmptyOrbSlot))
            {
                __instance.hand.group.stream().filter(c -> c instanceof onEvokeInterface).forEach(c -> ((onEvokeInterface) c).onEvoke(__instance.orbs.get(0)));
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "evokeNewestOrb")
    public static class OnEvokeFirst
    {
        public static void Prefix(AbstractPlayer __instance)
        {
            if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(__instance.orbs.size() - 1) instanceof EmptyOrbSlot))
            {
                __instance.hand.group.stream().filter(c -> c instanceof onEvokeInterface).forEach(c -> ((onEvokeInterface) c).onEvoke(__instance.orbs.get(__instance.orbs.size() - 1)));
            }
        }
    }
}
