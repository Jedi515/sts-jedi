package mod.jedi.relics;

import basemod.helpers.CardPowerTip;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mod.jedi.interfaces.onGenerateCardMidcombatInterface;
import mod.jedi.jedi;

public class PaintBrush
    extends AbstractJediRelic
    implements onGenerateCardMidcombatInterface, ClickableRelic
{
    public static String ID = jedi.makeID(PaintBrush.class.getSimpleName());
    public CardPowerTip cTip = new CardPowerTip(null);

    public PaintBrush()
    {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription()
    {
        return CLICKABLE_DESCRIPTIONS()[0] + DESCRIPTIONS[0];
    }

    @Override
    public void onRightClick()
    {
        if (!grayscale && cTip.card != null)
        {
            grayscale = true;
            addToBot(new MakeTempCardInHandAction(cTip.card.makeStatEquivalentCopy()));
        }
    }

    @Override
    public void onVictory()
    {
        grayscale = false;
        cTip.card = null;
        tips.remove(cTip);
    }

    public void onCreateCard(AbstractCard c)
    {
        cTip.card = c.makeStatEquivalentCopy();
        if (!tips.contains(cTip)) tips.add(cTip);
    }
}
