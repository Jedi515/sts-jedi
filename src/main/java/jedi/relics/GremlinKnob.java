package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GremlinKnob
    extends AbstractJediRelic
{
    public static final String ID = "jedi:gremlinknob";
    public GremlinKnob() {
        super(ID, RelicTier.RARE,LandingSound.FLAT);
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m)
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (c.type == AbstractCard.CardType.SKILL)
        {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
        }
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}
