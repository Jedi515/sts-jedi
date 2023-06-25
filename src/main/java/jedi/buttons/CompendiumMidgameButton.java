package jedi.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import jedi.patches.CompendiumMidgamePatches;

import static jedi.patches.CompendiumMidgamePatches.paper;

public class CompendiumMidgameButton
{
    public Hitbox hb;
    public float x, y;
    public boolean renderRelics = false;

    public CompendiumMidgameButton()
    {
        hb = new Hitbox(64 * Settings.xScale, 64 * Settings.scale);
        x = 1530F * Settings.xScale;
        y = Settings.OPTION_Y + 250 * Settings.scale;
        hb.move(x, y);
    }

    public void update()
    {
        hb.update();

        if (InputHelper.justClickedLeft && hb.hovered && !renderRelics)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            hb.clickStarted = true;
            renderRelics = true;
        }


        if (renderRelics)
        {
            if (InputHelper.pressedEscape || InputHelper.justClickedLeft && (CardCrawlGame.mainMenuScreen.relicScreen.button.hb.hovered || AbstractDungeon.overlayMenu.cancelButton.hb.hovered))
            {
                renderRelics = false;
            }

            CardCrawlGame.mainMenuScreen.relicScreen.update();
        }
    }

    public void render(SpriteBatch sb)
    {

        sb.setColor(Color.WHITE);
        if (!renderRelics)
        {
            sb.draw(CompendiumMidgamePatches.relicImg, hb.x, hb.y, hb.width, hb.height);
            hb.render(sb);
        }
        else
        {
            sb.draw(paper, 0, 0, paper.getWidth(), paper.getHeight());
            CardCrawlGame.mainMenuScreen.relicScreen.render(sb);
        }
    }
}
