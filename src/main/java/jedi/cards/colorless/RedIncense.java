package jedi.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.cards.CustomJediCard;
import jedi.interfaces.OnBeingScriedInterface;

public class RedIncense extends CustomJediCard
    implements OnBeingScriedInterface
{
    public static final String ID = makeCardId(RedIncense.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = -2;

    public RedIncense()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        setMN(2);
    }

    public void triggerOnExhaust()
    {
        bruh();
    }

    public void triggerOnManualDiscard()
    {
        bruh();
    }

    public void onBeingScried()
    {
        bruh();
    }

    private void bruh()
    {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        cantUseMessage = CardCrawlGame.languagePack.getCardStrings(Reflex.ID).EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(1);
    }
}
