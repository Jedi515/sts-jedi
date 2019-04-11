package mod.jedi.cards;

import mod.jedi.interfaces.CardAddedToDeck;

public abstract class AbstractCollectorCard
    extends CustomJediCard implements CardAddedToDeck
{
    public int threshold;

    public AbstractCollectorCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public abstract boolean ReplaceCardWithCollector();
}
