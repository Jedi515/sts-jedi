package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Matchstick
    extends CustomRelic
implements ClickableRelic
{
    public static final String ID = "jedi:matchstick";
    private boolean usedThisCombat = false;

    public Matchstick()
    {
        super("jedi:matchstick", new Texture("resources/images/relics/matchstick.png"), AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return CLICKABLE_DESCRIPTIONS()[0] +
        this.DESCRIPTIONS[0];

    }

    public void onRightClick() {
        if ((!this.isObtained) || (this.usedThisCombat)) {
            return;
        }
        if ((AbstractDungeon.getCurrRoom() != null) && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT))
        {
            this.usedThisCombat = true;
            flash();
            stopPulse();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
        }
    }

    public void atPreBattle()
    {
        this.usedThisCombat = false;
        beginLongPulse();
    }

    public void onVictory()
    {
        stopPulse();
    }

    public AbstractRelic makeCopy()
    {
        return new Matchstick();
    }
}
