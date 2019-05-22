package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.hubris.screens.select.RelicSelectScreen;
import com.evacipated.cardcrawl.mod.hubris.vfx.ObtainRelicLater;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import mod.jedi.jedi;

public abstract class AbstractCommand
    extends CustomRelic
{
    protected boolean relicSelected = true;
    protected RelicSelectScreen relicSelectScreen = new RelicSelectScreen();
    protected boolean fakeHover = false;
    protected AbstractRoom.RoomPhase roomPhase;
    protected boolean loseRelic = false;
    public static String ID = "jedi:command_";

    public AbstractCommand(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
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
                AbstractRelic relic = this.relicSelectScreen.getSelectedRelics().get(0).makeCopy();
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
                        System.out.println("JEDI MOD: Top 10 anime plot twists - command was SOMEHOW called for a rarity that it doesn't support. I blame mods. If osmething crashed: " + relic.tier.toString());
                }
                AbstractDungeon.effectsQueue.add(0, new ObtainRelicLater(relic));
                AbstractDungeon.getCurrRoom().phase = roomPhase;
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
            this.relicSelectScreen.render(sb);
        }

    }

    public void relicBS()
    {
        if (loseRelic)
        {
            AbstractDungeon.player.loseRelic(this.relicId);

            if (jedi.CommandLocked)
            {
                UnlockTracker.lockedRelics.addAll(jedi.lockedRelics);
            }

            if (jedi.CommandUnseen)
            {
                for (AbstractRelic r : jedi.unseenRelics)
                {
                    r.isSeen = false;
                }
            }
        }
    }
}
