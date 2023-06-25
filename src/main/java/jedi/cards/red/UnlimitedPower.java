package jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import jedi.actions.VSFXLightningAction;
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
    private static int startDamage = 3;
    private static int startMagicNumber = 0;


    public UnlimitedPower()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = startMagicNumber;
        this.damage = this.baseDamage = startDamage;
        returnToHand = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (this.magicNumber > 0)
        {

            AbstractDungeon.actionManager.addToBottom(new VSFXLightningAction(p));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.HP_LOSS)));
        }
        AbstractDungeon.actionManager.addToBottom(new VSFXLightningAction(m));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        upgrade();
    }

    @Override
    public void upgrade()
    {
        upgradeDamage(1);
        if (darkSide)
        {
            darkSide = false;
            upgradeMagicNumber(2);
        }
        else
        {
            darkSide = true;
        }

        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
        if (this.magicNumber > 0)
        {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public boolean canUpgrade()
    {
        return !CardCrawlGame.isInARun() || AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public void triggerOnEndOfPlayerTurn()
    {
        this.upgraded = false;
        this.timesUpgraded = 0;
        this.name = NAME;
        this.darkSide = false;
        initializeTitle();
        this.rawDescription = DESCRIPTION;
        initializeDescription();
        this.damage = this.baseDamage = startDamage;
        this.magicNumber = this.baseMagicNumber = startMagicNumber;
    }


    public CustomCard makeCopy()
    {
        return new UnlimitedPower();
    }
}
