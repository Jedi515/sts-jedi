package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import jedi.cards.CustomJediCard;

public class MarkOfDeath
        extends CustomJediCard
{
    public static final String ID = "jedi:markofdeath";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public MarkOfDeath()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LockOnPower(m, 99), 99));

    }

    protected void upp() {
        upgradeBaseCost(0);
    }

    public AbstractCard makeCopy()
    {
        return new MarkOfDeath();
    }
}
