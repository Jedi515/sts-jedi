package jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import jedi.screens.RelicSelectScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import jedi.util.TextureLoader;

public abstract class AbstractCommand
    extends CustomRelic
{
    protected boolean relicSelected = true;
    protected RelicSelectScreen relicSelectScreen = new RelicSelectScreen();
    protected boolean fakeHover = false;
    protected AbstractRoom.RoomPhase roomPhase;
    protected boolean loseRelic = false;
    private AbstractRelic relic;    
    public static String ID = "jedi:command_";

    public AbstractCommand(String id, RelicTier tier, LandingSound sfx) {
        super(id, TextureLoader.getTexture("resources/jedi/images/relics/command.png"), TextureLoader.getTexture("resources/jedi/images/relics/outline/command.png"), tier, sfx);
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
        this.openRelicSelect();
    }

    protected void openRelicSelect() {

    }

    public void update() {
        super.update();
        if (!this.relicSelected) {
            if (this.relicSelectScreen.doneSelecting()) {
                this.relicSelected = true;
                relic = this.relicSelectScreen.getSelectedRelics().get(0).makeCopy();
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
                this.relicSelectScreen.update();
                if (!this.hb.hovered) {
                    this.fakeHover = true;
                }
                this.hb.hovered = true;
            }
        }
    }

    public void renderTip(SpriteBatch sb) {
        if (!this.relicSelected && this.fakeHover) {
            this.relicSelectScreen.render(sb);
        }

        if (this.fakeHover) {
            this.fakeHover = false;
            this.hb.hovered = false;
        } else {
            super.renderTip(sb);
        }

    }

    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);
        if (!this.relicSelected && !this.fakeHover) {
            boolean hideRelics = Settings.hideRelics;
            Settings.hideRelics = false;
            this.relicSelectScreen.render(sb);
            Settings.hideRelics = hideRelics;
        }

    }

    public void relicBS()
    {
        if (loseRelic && AbstractDungeon.effectsQueue.stream().noneMatch(eff -> eff instanceof ShowCardAndObtainEffect) && AbstractDungeon.effectList.stream().noneMatch(eff -> eff instanceof ShowCardAndObtainEffect))
        {
            AbstractDungeon.player.loseRelic(this.relicId);
            relic.instantObtain();
            AbstractDungeon.getCurrRoom().phase = roomPhase;
        }
    }
}
