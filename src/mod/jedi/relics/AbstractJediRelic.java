package mod.jedi.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import mod.jedi.util.TextureLoader;

@AutoAdd.Ignore
public abstract class AbstractJediRelic
    extends CustomRelic
{
    public AbstractCard cardToPreview;
    public AbstractJediRelic(String id, RelicTier tier, LandingSound sfx)
    {
        super(id, createTexture(id), tier, sfx);
        cardToPreview = null;
    }

    private static Texture createTexture(String ID)
    {
        if (Gdx.files.internal(makeImagePath(ID)).exists())
        {
            return TextureLoader.getTexture(makeImagePath(ID));
        }
        return TextureLoader.getTexture(makeImagePath("beta_rock"));
    }

    private static String makeImagePath(String ID)
    {
        return "resources/jedi/images/relics/" + ID.substring(5) + ".png";
    }

    @Override
    public void renderTip(SpriteBatch sb)
    {
        super.renderTip(sb);
        renderCardPreview(sb);
    }

    public void renderCardPreview(SpriteBatch sb)
    {
        if (cardToPreview != null)
        {
            if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW)
            {
                cardToPreview.current_x = Settings.WIDTH - 380 * Settings.scale;
                cardToPreview.current_y = Settings.HEIGHT * 0.6F;
            }
            else
            {
                if (this.currentX > (float) Settings.WIDTH * 0.75F)
                {
                    this.cardToPreview.current_x = InputHelper.mX - 420 * this.scale;
                } else
                {
                    this.cardToPreview.current_x = InputHelper.mX + 420 * this.scale;
                }
                this.cardToPreview.current_y = InputHelper.mY - 100 * this.scale;
            }
            this.cardToPreview.drawScale = scale * 0.8F;
            this.cardToPreview.render(sb);
        }
    }

    @Override
    public void renderBossTip(SpriteBatch sb)
    {
        super.renderBossTip(sb);
        renderCardPreview(sb);
    }
}
