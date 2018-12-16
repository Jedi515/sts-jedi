package mod.jedi.cards.curses;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Frostbite
    extends CustomCard
{

    public static final String ID = "jedi:frostbite";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = -2;
    public static final String IMG_PATH = "resources/images/cards/jedi_beta.png";

    public Frostbite()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        } else {
            AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
        }

    }

    public void triggerWhenDrawn()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null && !p.hasPower(ArtifactPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GainStrengthPower(p, this.magicNumber), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Frostbite();
    }

    public void upgrade() {
    }
}
