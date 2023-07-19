package jedi.cards.green;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cardMods.ModifyDamageOtherInterface;
import jedi.cards.CustomJediCard;

public class Precision
    extends CustomJediCard
    implements ModifyDamageOtherInterface
{
    public static final String ID = makeCardId(Precision.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = -2;

    public Precision()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 2;
        cardsToPreview = new Shiv();
    }

    @Override
    public boolean hasEnoughEnergy() {
        cantUseMessage = CardCrawlGame.languagePack.getCardStrings(Reflex.ID).EXTENDED_DESCRIPTION[0];
        return false;
    }


    @Override
    public float modifyDamageOther(float damage, AbstractCard card, AbstractMonster mo) {

        return damage + (card.cardID.equals(Shiv.ID) ? magicNumber : 0);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.cardID.equals(Shiv.ID)) upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    }

    @Override
    public void upgrade()
    {
        upgradeMagicNumber(2);
    }
}
