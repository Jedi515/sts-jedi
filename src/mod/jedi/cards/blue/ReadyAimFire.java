package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.LockOnPower;
import mod.jedi.cards.CustomJediCard;

public class ReadyAimFire
        extends CustomJediCard
{
    public static final String ID = "jedi:readyaimfire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public ReadyAimFire()
    {
        super(ID, NAME, "resources/jedi/images/cards/jedi_beta.png", COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new LockOnPower(mo, magicNumber), magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());

    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.upgradeMagicNumber(1);
        }
    }
    public AbstractCard makeCopy()
    {
        return new ReadyAimFire();
    }

}
