package mod.jedi.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mod.jedi.interfaces.onGenerateCardMidcombatInterface;
import mod.jedi.jedi;

public class StarAurum
    extends AbstractJediRelic
    implements onGenerateCardMidcombatInterface
{
    public static final String ID = jedi.makeID(StarAurum.class.getSimpleName());
    public static final int blockGain = 8;
    public boolean triggered;

    public StarAurum()
    {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onCreateCard(AbstractCard card)
    {
        if (!triggered)
        {
            triggered = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            beginLongPulse();
        }
    }

    @Override
    public void onPlayerEndTurn()
    {
        if (triggered)
        {
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockGain));
            stopPulse();
            triggered = false;
        }
    }

    @Override
    public void onVictory()
    {
        stopPulse();
        triggered = false;
    }

    public String getUpdatedDescription()
    {
        return String.format(DESCRIPTIONS[0], blockGain);
    }

}
