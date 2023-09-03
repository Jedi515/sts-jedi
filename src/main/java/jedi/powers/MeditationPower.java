package jedi.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.FocusPower;

public class MeditationPower
    extends AbstractJediPower
{
    public static final String POWER_ID = makeID(MeditationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MeditationPower(AbstractCreature owner, int setAmount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, setAmount);
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, amount), amount));
        addToBot(new RemoveNextOrbAction());
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
