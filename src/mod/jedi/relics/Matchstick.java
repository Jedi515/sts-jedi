package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.jedi.util.TextureLoader;

public class Matchstick
    extends CustomRelic
implements ClickableRelic
{
    public static final String ID = "jedi:matchstick";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private boolean usedThisCombat = false;

    public Matchstick()
    {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.SOLID);
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
