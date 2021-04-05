package mod.jedi.cards.purple;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.actions.ScryCallbackAction;
import mod.jedi.cards.CustomJediCard;
import mod.jedi.jedi;

public class FortuneCookie
    extends CustomJediCard
{
    public static String ID = jedi.makeID(FortuneCookie.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 0;

    public FortuneCookie()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.NONE);
        setMN(2);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {
        addToBot(new ScryCallbackAction(magicNumber, list -> {if (!list.isEmpty()) addToBot(new DrawCardAction(1));}));
    }
}
