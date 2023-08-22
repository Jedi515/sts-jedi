package jedi.relics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import jedi.jedi;
import jedi.screens.RelicSelectScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import jedi.util.TextureLoader;

import java.util.ArrayList;

public abstract class AbstractCommand
    extends AbstractJediRelic
{
    protected boolean relicSelected = true;
    protected RelicSelectScreen relicSelectScreen = new RelicSelectScreen();
    protected boolean fakeHover = false;
    protected AbstractRoom.RoomPhase roomPhase;
    protected boolean loseRelic = false;
    private AbstractRelic relic;    
    public static String ID = "jedi:command_";

    public AbstractCommand(String id, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture("jedi/images/relics/command.png"), TextureLoader.getTexture("resources/jedi/images/relics/outline/command.png"), tier, sfx);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.closeCurrentScreen();
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        roomPhase = AbstractDungeon.getCurrRoom().phase;
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        openRelicSelect();
    }

    protected void openRelicSelect() {

    }


    protected void openRelicSelect(ArrayList<AbstractRelic> relics) {
        relicSelected = false;
        if (!jedi.CommandHasCopy)
        {
            relics.removeIf((r) -> AbstractDungeon.player.hasRelic(r.relicId));
        }

        if (!jedi.CommandLocked)
        {
            relics.removeIf((r) -> UnlockTracker.isRelicLocked(r.relicId));
        }
        else
        {
            jedi.lockedRelics.clear();
            for (AbstractRelic r : relics)
            {
                if (UnlockTracker.isRelicLocked(r.relicId))
                {
                    jedi.lockedRelics.add(r.relicId);
                    UnlockTracker.lockedRelics.remove(r.relicId);
                }
            }
        }

        if (!jedi.CommandUnseen)
        {
            relics.removeIf((r) -> !UnlockTracker.isRelicSeen(r.relicId));
        }
        else
        {
            jedi.unseenRelics.clear();
            for (AbstractRelic r : relics)
            {
                if (!UnlockTracker.isRelicSeen(r.relicId))
                {
                    jedi.unseenRelics.add(r);
                    r.isSeen = true;
                }
            }
        }
        relicSelectScreen.open(relics);
    }


    public void update() {
        super.update();
        if (!relicSelected) {
            if (relicSelectScreen.doneSelecting()) {
                relicSelected = true;
                relic = relicSelectScreen.getSelectedRelics().get(0).makeCopy();
                switch (relic.tier) {
                    case COMMON:
                        AbstractDungeon.commonRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case UNCOMMON:
                        AbstractDungeon.uncommonRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case RARE:
                        AbstractDungeon.rareRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case SHOP:
                        AbstractDungeon.shopRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    case BOSS:
                        AbstractDungeon.bossRelicPool.removeIf(id ->  id.equals(relic.relicId));
                        break;
                    default:
                        System.out.println("JEDI MOD: Top 10 anime plot twists - command crashed because of this relic: " + relic.relicId);
                }
                loseRelic = true;
            } else {
                relicSelectScreen.update();
                if (!hb.hovered) {
                    fakeHover = true;
                }
                hb.hovered = true;
            }
        }
    }

    public void renderTip(SpriteBatch sb) {
        if (!relicSelected && fakeHover) {
            relicSelectScreen.render(sb);
        }

        if (fakeHover) {
            fakeHover = false;
            hb.hovered = false;
        } else {
            super.renderTip(sb);
        }

    }

    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);
        if (!relicSelected && !fakeHover) {
            boolean hideRelics = Settings.hideRelics;
            Settings.hideRelics = false;
            relicSelectScreen.render(sb);
            Settings.hideRelics = hideRelics;
        }

    }

    public void relicBS()
    {
        if (loseRelic && AbstractDungeon.effectsQueue.stream().noneMatch(eff -> eff instanceof ShowCardAndObtainEffect) && AbstractDungeon.effectList.stream().noneMatch(eff -> eff instanceof ShowCardAndObtainEffect))
        {
            AbstractDungeon.player.loseRelic(relicId);
            relic.instantObtain();
            AbstractDungeon.getCurrRoom().phase = roomPhase;
        }
    }
}
