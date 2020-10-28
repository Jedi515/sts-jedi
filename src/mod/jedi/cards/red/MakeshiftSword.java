package mod.jedi.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.cards.CustomJediCard;
import mod.jedi.jedi;
import mod.jedi.powers.MakeshiftSwordPower;

public class MakeshiftSword
        extends CustomJediCard
{
    public static final String ID = jedi.makeID("MakeshiftSword");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public MakeshiftSword()
    {
        super(ID, NAME, null, COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        magicNumber = baseMagicNumber = 4;
    }

    @Override
    public void upgrade()
    {
        upgradeName();
        upgradeMagicNumber(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(p, p, new MakeshiftSwordPower(p, magicNumber), magicNumber));
    }
}