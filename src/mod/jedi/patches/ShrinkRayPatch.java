package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.relics.ShrinkRay;

@SpirePatch(clz = AbstractCreature.class, method = "loadAnimation")
public class ShrinkRayPatch {
    public static void Prefix(AbstractCreature __instance, String atlasUrl, String skeletonUrl,@ByRef float[] scale)
    {

        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null)
        {
            if (AbstractDungeon.player.hasRelic(ShrinkRay.ID) && !__instance.isPlayer)
            {
                scale[0] += 0.2F;
            }
        }
    }
}
