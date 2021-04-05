package mod.jedi.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mod.jedi.interfaces.onGenerateCardMidcombatInterface;
import mod.jedi.jedi;

public class ByrdBible
    extends AbstractJediRelic
    implements onGenerateCardMidcombatInterface
{
    public static final String ID = jedi.makeID(ByrdBible.class.getSimpleName());

    public ByrdBible()
    {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onCreateCard(AbstractCard card)
    {
        if (!grayscale)
        {
            grayscale = true;
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void atTurnStart()
    {
        grayscale = false;
    }
}
