package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import mod.jedi.actions.VSFXLightningAction;

public class UnlimitedPower
    extends CustomCard
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

    public static final String IMG_PATH = "resources/jedi/images/cards/red/unlimitedpower.png";


    public UnlimitedPower()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 0;
        this.damage = this.baseDamage = 3;
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

        this.upgrade();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));

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
        if (!CardCrawlGame.isInARun())
        {
            return true;
        }

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            return true;
        }
        else
        {
            return false;
        }
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
        this.magicNumber = this.baseMagicNumber = this.startMagicNumber;
    }


    public CustomCard makeCopy()
    {
        return new UnlimitedPower();
    }
}
