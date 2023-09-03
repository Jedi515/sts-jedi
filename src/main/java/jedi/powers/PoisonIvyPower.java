package jedi.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class PoisonIvyPower
    extends AbstractJediPower
{
    public static final String POWER_ID = makeID(PoisonIvyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME =  powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean notShiv;

    public PoisonIvyPower(AbstractCreature owner, int setAmount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, setAmount);
    }

    public void updateDescription()
    {
        if (amount == 1)
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    public void onUseCard(AbstractCard c, UseCardAction action)
    {
        notShiv = (c.type == AbstractCard.CardType.ATTACK) && !(c instanceof Shiv);
    }


    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if (target != owner && info.type == DamageInfo.DamageType.NORMAL && notShiv)
        {
            if (target.hasPower(PoisonPower.POWER_ID))
            {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), amount));
            }
        }
    }
}
