package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.util.TextureLoader;

public class ScrapMetal
    extends CustomRelic
{
    public static final String ID = "jedi:scrapmetal";
    public static final String IMG_PATH = "resources/jedi/images/relics/beta_rock.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);

    public ScrapMetal() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.CLINK);
        this.counter = 0;
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m)
    {
        if (c instanceof Shiv)
        {
            this.counter++;
        }

        if (this.counter == 15)
        {
            this.flash();
            this.counter = 0;
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), 1));
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new ScrapMetal();
    }

}
