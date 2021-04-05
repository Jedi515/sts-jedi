package mod.jedi.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.actions.ScryCallbackAction;
import mod.jedi.cards.CustomJediCard;
import mod.jedi.jedi;

public class ApocalypseNow
    extends CustomJediCard
{
    public static String ID = jedi.makeID(ApocalypseNow.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public ApocalypseNow()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, AbstractCard.CardColor.PURPLE, AbstractCard.CardRarity.RARE, CardTarget.ALL_ENEMY);
        setDmg(16);
        setMN(10);
        isMultiDamage = true;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(5);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster)
    {
        addToBot(new ScryCallbackAction(magicNumber, list ->
        {
            if (list.size() > 7)
            {
                addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
        }));
    }
}
