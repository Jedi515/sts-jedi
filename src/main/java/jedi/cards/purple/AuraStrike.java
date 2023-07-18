package jedi.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cardMods.ModifyDamageOtherInterface;
import jedi.cards.CustomJediCard;

public class AuraStrike
        extends CustomJediCard
    implements ModifyDamageOtherInterface
{
    public static final String ID = makeCardId(AuraStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;


    public AuraStrike()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 2;
        baseDamage = damage = 7;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public float modifyDamageOther(float dmg, AbstractCard card, AbstractMonster mo) {
        return dmg+magicNumber;
    }

    @Override
    public void upp()
    {
        upgradeMagicNumber(1);
        upgradeDamage(2);
    }
}
