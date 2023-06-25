package jedi.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.actions.ScryCallbackAction;
import jedi.cards.CustomJediCard;

public class PredictedStrike
    extends CustomJediCard
{
    public static final String ID = makeCardId(PredictedStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 2;

    public PredictedStrike()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        tags.add(CardTags.STRIKE);
        setDmg(7);
        setMN(2);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        addToBot(new ScryCallbackAction(magicNumber, list -> list.forEach(c -> addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT)))));
    }
}
