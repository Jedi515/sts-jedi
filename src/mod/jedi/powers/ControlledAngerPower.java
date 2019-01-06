package mod.jedi.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ControlledAngerPower
    extends AbstractPower
{
    public static final String POWER_ID = "jedi:controlledanger";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ControlledAngerPower(AbstractCreature owner)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.loadRegion("anger");
        this.type = PowerType.BUFF;
    }

    public void onUseCard(AbstractCard c, UseCardAction action)
    {
        if (c instanceof Anger)
        {
            this.flash();
            action.exhaustCard = true;
        }
    }
}
