package mod.jedi.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import mod.jedi.patches.CompendiumMidgamePatches;

import static mod.jedi.patches.CompendiumMidgamePatches.paper;

public class CardLibraryMidgameButton
{
    public Hitbox hb;
    public float x, y;
    public boolean render = false;

    public CardLibraryMidgameButton()
    {
        hb = new Hitbox(64 * Settings.xScale, 64 * Settings.scale);
        x = 1630F * Settings.xScale;
        y = Settings.OPTION_Y + 250 * Settings.scale;
        hb.move(x, y);
    }

    public void update()
    {
        hb.update();

        if (InputHelper.justClickedLeft && hb.hovered && !render)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            hb.clickStarted = true;
            render = true;
        }


        if (render)
        {
            if (InputHelper.pressedEscape || InputHelper.justClickedLeft && (CardCrawlGame.mainMenuScreen.cardLibraryScreen.button.hb.hovered || AbstractDungeon.overlayMenu.cancelButton.hb.hovered))
            {
                render = false;
            }

            CardCrawlGame.mainMenuScreen.cardLibraryScreen.update();
        }
    }

    public void render(SpriteBatch sb)
    {

        sb.setColor(Color.WHITE);
        if (!render)
        {
            sb.draw(CompendiumMidgamePatches.cardImg, hb.x, hb.y, hb.width, hb.height);
            hb.render(sb);
        }
        else
        {
            sb.draw(paper, 0, 0, paper.getWidth(), paper.getHeight());
            CardCrawlGame.mainMenuScreen.cardLibraryScreen.render(sb);
        }
    }
}
