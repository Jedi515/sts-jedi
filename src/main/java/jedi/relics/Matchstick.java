package jedi.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Matchstick
    extends AbstractJediRelic
implements ClickableRelic
{
    public static final String ID = "jedi:matchstick";

    public Matchstick()
    {
        super(ID, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return CLICKABLE_DESCRIPTIONS()[0] +
        this.DESCRIPTIONS[0];

    }

    public void onRightClick() {
        if ((!isObtained) || (grayscale)) {
            return;
        }
        if ((AbstractDungeon.getCurrRoom() != null) && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT))
        {
            grayscale = true;
            flash();
            stopPulse();
            addToBot(new GainEnergyAction(2));
        }
    }

    public void atPreBattle()
    {
        grayscale = false;
        beginLongPulse();
    }

    public void onVictory()
    {
        grayscale = false;
        stopPulse();
    }

    public AbstractRelic makeCopy()
    {
        return new Matchstick();
    }
}
