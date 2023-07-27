package jedi.cards.red;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.VSFXLightningAction;
import jedi.cardMods.EtherealJediMod;
import jedi.cards.CustomJediCard;

public class UnlimitedPower
        extends CustomJediCard
{
    public static final String ID = "jedi:unlimitedpower";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 0;
    private boolean darkSide = false;
    private static final int startDamage = 3;
    private static final int startMagicNumber = 1;


    public UnlimitedPower()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = startMagicNumber;
        secondMN = baseSecondMN = 0;
        this.damage = this.baseDamage = startDamage;
        returnToHand = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (secondMN > 0)
        {

            addToBot(new VSFXLightningAction(p));
            addToBot(new DamageAction(p, new DamageInfo(p, secondMN, DamageInfo.DamageType.HP_LOSS)));
        }
        addToBot(new VSFXLightningAction(m));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        darkUpgrade();
    }

    public void darkUpgrade()
    {
        upgradeDamage(1);
        upgradeMagicNumber(1);
        if (darkSide)
        {
            darkSide = false;
            upgradeSecondMN(2);
        }
        else
        {
            darkSide = true;
        }
        if (!isEthereal && !upgraded) CardModifierManager.addModifier(this, new EtherealJediMod());
    }

    @Override
    public void triggerOnEndOfPlayerTurn()
    {
        this.darkSide = false;
        damage = baseDamage = startDamage;
        magicNumber = baseMagicNumber = startMagicNumber;
        secondMN = baseSecondMN = 0;
    }
}
