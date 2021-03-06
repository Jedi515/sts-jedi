package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.actions.CustomDiscoveryAction;
import mod.jedi.jedi;

public class StudyFrost
    extends CustomCard
{
    public static final String ID = "jedi:studyfrost";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public StudyFrost()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardLibrary.getAllCards())
        {
            for (String keyword : GameDictionary.FROST.NAMES)
            {
                if (c.keywords.contains(keyword))
                {
                    group.addToBottom(c.makeStatEquivalentCopy());
                    break;
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new CustomDiscoveryAction(group, 3));
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
