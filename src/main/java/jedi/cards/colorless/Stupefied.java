package jedi.cards.colorless;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cardMods.ModifyDamageOtherInterface;
import jedi.cards.CustomJediCard;

public class Stupefied extends CustomJediCard
    implements ModifyDamageOtherInterface
{
    public static final String ID = makeCardId(Stupefied.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;

    public Stupefied()
    {
        super(ID, NAME, "status/dazed", COST, DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        setMN(3);
        exhaust = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
    }


    public float modifyDamageOther(float damage, AbstractCard card, AbstractMonster mo){return damage-magicNumber;}
}
