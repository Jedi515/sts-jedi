package jedi.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RubberSling
    extends AbstractJediRelic
{
    public static final String ID = "jedi:rubbersling";

    public RubberSling()
    {
        super(ID, RelicTier.COMMON, LandingSound.MAGICAL, RelicType.GREEN);
        this.description = DESCRIPTIONS[0];
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart()
    {
        grayscale = false;
    }

    @Override
    public void onManualDiscard()
    {
        if (!grayscale)
        {
            grayscale = true;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }
}
