package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.powers.FirewallPower;

public class Firewall
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(Firewall.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;

    public Firewall()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    protected void upp()
    {
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION + DESCRIPTION;
            initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster)
    {
        if (!p.hasPower(FirewallPower.POWER_ID))
        {
            addToBot(new ApplyPowerAction(p, p, new FirewallPower(p)));
        }
    }
}
