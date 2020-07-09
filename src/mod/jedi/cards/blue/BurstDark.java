package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.jedi.cards.CustomJediCard;


public class BurstDark
        extends CustomJediCard
{
    public static final String ID = "jedi:BurstDark";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public BurstDark()
    {
        super(ID, NAME, "resources/jedi/images/cards/jedi_beta_attack.png", 2, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = 6;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        m = null;
        for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
        {
            if (!mon.isDeadOrEscaped())
            {
                if (m == null) m = mon;
                else
                {
                    if (m.currentHealth > mon.currentHealth) m = mon;
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Dark());
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeBaseCost(1);
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new BurstDark();
    }

}
