package mod.jedi.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractCollectorCard
    extends CustomCard
{
    public int threshold;

    public AbstractCollectorCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public void onAddedToMasterDeck()
    {

    }

    public boolean ReplaceCardWithCollector()
    {
        if (this.misc < 5)
        {
            threshold = 20;
        }

        if (this.misc >= 8)
        {
            threshold = 35;
        }

        if (this.misc >= 15)
        {
            threshold = 40;
        }

        if (this.misc >= 20)
        {
            threshold = 60;
        }

        return AbstractDungeon.cardRng.random(0,99) < threshold;
    }
}
