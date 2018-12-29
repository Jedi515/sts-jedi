package mod.jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.actions.HatredAction;

public class Hate
    extends CustomCard
{
    public static final String ID = "jedi:hate";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/images/cards/jedi_beta_attack.png";


    public Hate()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = 7;
        this.exhaust = true;

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HatredAction(m, new DamageInfo(p, this.damage)));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeDamage(3);
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.exhaust = false;
        }
    }


    public CustomCard makeCopy()
    {
        return new Hate();
    }
}
