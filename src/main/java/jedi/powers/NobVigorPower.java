package jedi.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class NobVigorPower
    extends AbstractJediPower
{
    public static final String POWER_ID = makeID(NobVigorPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME =  powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NobVigorPower(AbstractCreature owner, int setAmount) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, setAmount);
        loadRegion("anger");
    }

    public void onUseCard(AbstractCard c, UseCardAction action)
    {
        if (c.type == AbstractCard.CardType.SKILL) addToTop(new ApplyPowerAction(owner, owner,
                owner instanceof AbstractMonster ? new MonsterVigorPower(owner, amount) : new VigorPower(owner, amount)
                ));
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }
}
