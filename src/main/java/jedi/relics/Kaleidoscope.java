package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.HashSet;
import java.util.Set;

public class Kaleidoscope
    extends AbstractJediRelic
{
    public static final String ID = "jedi:kaleidoscope";
    private static Set<AbstractCard.CardColor> cardColors;

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public Kaleidoscope()
    {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
        cardColors = new HashSet<>();
    }

    @Override
    public void onEquip()
    {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            cardColors.add(c.color);
        }
        this.counter = cardColors.size();
    }

    @Override
    public void onMasterDeckChange()
    {
        cardColors.clear();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            cardColors.add(c.color);
        }
        this.counter = cardColors.size();
    }

    @Override
    public void atBattleStart()
    {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, cardColors.size()), cardColors.size()));
    }

}
