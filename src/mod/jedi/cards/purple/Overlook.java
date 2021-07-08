package mod.jedi.cards.purple;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import mod.jedi.actions.ScryCallbackAction;
import mod.jedi.cards.CustomJediCard;

public class Overlook
    extends CustomJediCard
{
    public static final String ID = makeCardId(Overlook.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public Overlook()
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
        addToBot(new ScryCallbackAction(magicNumber, list ->
        {
            if (list.stream().anyMatch(c -> c.type == CardType.ATTACK))
            {
                addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
            }
            else
            {
                addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
            }
        }));
    }
}
