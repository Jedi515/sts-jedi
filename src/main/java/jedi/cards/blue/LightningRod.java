package jedi.cards.blue;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import jedi.cards.CustomJediCard;
import jedi.powers.LightningRodPower;
import jedi.jedi;
import jedi.util.Wiz;

public class LightningRod
    extends CustomJediCard
{
    public static final String ID = jedi.makeID(LightningRod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public LightningRod() {
        super(ID, NAME, 1, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    protected void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new LightningRodPower(m, magicNumber));
        addToBot(new ChannelAction(new Lightning()));
    }
}
