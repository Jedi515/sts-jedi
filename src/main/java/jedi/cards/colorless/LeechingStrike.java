package jedi.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.DrainAction;
import jedi.cards.CustomJediCard;

public class LeechingStrike
    extends CustomJediCard
{
    public static final String ID = "jedi:LeechingStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;

    public LeechingStrike()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        damage = baseDamage = 6;
        exhaust = true;
    }

    @Override
    public void upp()
    {
        upgradeDamage(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DrainAction(p, m, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE, this));
    }
}
