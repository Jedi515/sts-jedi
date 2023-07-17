package jedi.cards.blue;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.BruteforceAction;
import jedi.cards.CustomJediCard;

public class Bruteforce
        extends CustomJediCard
{
    public static final String ID = "jedi:bruteforce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;

    public Bruteforce()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 2;
        exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BruteforceAction(magicNumber));
    }


    @Override
    public void upp()
    {
        uDesc(UPGRADE_DESCRIPTION);
        exhaust = false;
    }
}
