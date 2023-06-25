package jedi.relics;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ArchwizardHat
    extends AbstractJediRelic
{
    public static final String ID = "jedi:archwizardhat";

    private static boolean triggered;
    public static final float efficiency = 1.25F;

    public ArchwizardHat()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        description = getUpdatedDescription();
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        triggered = card.type == AbstractCard.CardType.ATTACK && (card.costForTurn == EnergyPanel.totalCount || card.cost == -1) && EnergyPanel.totalCount != 0;
        if (triggered)
        {
            this.flash();
        }
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c)
    {
        if (((c.costForTurn == EnergyPanel.totalCount || c.cost == -1) && EnergyPanel.totalCount != 0))
        {
            return (int)Math.ceil(damage * efficiency);
        }
        return damage;
    }
}
