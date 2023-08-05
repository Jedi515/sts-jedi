package jedi.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import jedi.actions.ShrinkRayAction;

//guess what, another patch on size
public class ShrinkRay
    extends AbstractJediRelic
{
    public static final String ID = "jedi:shrinkray";
    private static float MODIFIER_AMT = 0.1F;

    public ShrinkRay() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void atBattleStart() {
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ShrinkRayAction(MODIFIER_AMT));
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + (int)(MODIFIER_AMT * 100.0F) + this.DESCRIPTIONS[1];
    }
}
