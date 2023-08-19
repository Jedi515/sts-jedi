package jedi.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.ScryCallbackAction;
import jedi.actions.ScrySeenCallbackAction;
import jedi.cards.CustomJediCard;
import jedi.jedi;

public class WavingIron
    extends CustomJediCard
{
    public static String ID = jedi.makeID(WavingIron.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public WavingIron()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY);
        setDmg(4);
        setBlock(5);
        setMN(2);
    }


    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(1);
            upgradeBlock(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        blck();
        addToBot(new ScrySeenCallbackAction(magicNumber, list -> list.stream().filter(c -> c.type == CardType.ATTACK).forEach(c -> dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT))));
    }
}
