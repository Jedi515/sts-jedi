package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.util.TextureLoader;

public class CrownOfSimplicity
    extends CustomRelic
{
    public static final String ID = "jedi:crownofsimplicity";
    public static final String IMG_PATH = "resources/jedi/images/relics/crownofsimplicity.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);

    public CrownOfSimplicity()
    {
        super(ID, IMG, RelicTier.RARE, LandingSound.FLAT);
    }


    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ((card.rarity == AbstractCard.CardRarity.BASIC || card.hasTag(BaseModCardTags.BASIC_STRIKE) || card.hasTag(BaseModCardTags.BASIC_DEFEND)) && !card.purgeOnUse && !card.freeToPlayOnce)
        {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.freeToPlayOnce = true;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
        }
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public CustomRelic makeCopy()
    {
        return new CrownOfSimplicity();
    }
}
