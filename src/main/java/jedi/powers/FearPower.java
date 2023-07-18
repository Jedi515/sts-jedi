package jedi.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.interfaces.onGenerateCardMidcombatInterface;
import jedi.jedi;
import jedi.util.Wiz;

public class FearPower
    extends AbstractJediPower
    implements onGenerateCardMidcombatInterface
{

    public static final String POWER_ID = jedi.makeID(FearPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FearPower(AbstractCreature owner, int amt) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, amt);
    }

    public void onCreateCard(AbstractCard card)
    {
        addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atStartOfTurn() {
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
