package mod.jedi.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.jedi.cards.CustomJediCard;
import mod.jedi.interfaces.OnBeingScriedInterface;

public class RedIncense extends CustomJediCard
    implements OnBeingScriedInterface
{
    public static final String ID = makeCardId(RedIncense.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = -2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public RedIncense()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void triggerOnExhaust()
    {
        bruh();
    }

    public void triggerOnManualDiscard()
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

    @Override
    public void onBeingScried()
    {
        bruh();
    }
}
