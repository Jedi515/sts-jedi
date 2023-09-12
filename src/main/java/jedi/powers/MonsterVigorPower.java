package jedi.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF;

public class MonsterVigorPower
    extends AbstractJediPower
{
    public static final String POWER_ID = makeID(MonsterVigorPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean endTurnDrop;

    public MonsterVigorPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID, NAME, BUFF, false, owner, amount);
        this.loadRegion("vigor");
        endTurnDrop = false;
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        endTurnDrop = true;
    }

    @Override
    public void atEndOfRound() {
        if (endTurnDrop) addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
