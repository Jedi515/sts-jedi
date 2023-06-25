package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import jedi.cards.CustomJediCard;
import jedi.jedi;

public class DC
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(DC.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;

    public DC()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.SPECIAL, CardTarget.ENEMY);
        setDmg(5);
        setMN(3);
        exhaust = true;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
            initializeDescription();
            selfRetain = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void applyPowers()
    {
        int tempDmg = baseDamage;
        baseDamage += magicNumber * AbstractDungeon.player.orbs.stream().filter(o -> o instanceof Lightning).count();
        super.applyPowers();
        baseDamage = tempDmg;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        int tempDmg = baseDamage;
        baseDamage += magicNumber * AbstractDungeon.player.orbs.stream().filter(o -> o instanceof Lightning).count();
        super.calculateCardDamage(mo);
        baseDamage = tempDmg;
        isDamageModified = baseDamage != damage;
    }
}
