package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import jedi.actions.AllEnemyApplyPowerAction;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.powers.TunnelVisionPower;

public class TunnelVision
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(TunnelVision.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public TunnelVision() {
        super(ID, NAME, 1, DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 0;
        baseSecondMN = secondMN = 1;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0)
        {
            addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mon) -> new LockOnPower(mon, magicNumber)));
        }
        addToBot(new ApplyPowerAction(p, p, new TunnelVisionPower(p, secondMN)));
    }
}
