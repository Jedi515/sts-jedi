package mod.jedi.cards;

import basemod.abstracts.CustomCard;

public abstract class AbstractCollectorCard
    extends CustomCard
{
    public int threshold;

    public AbstractCollectorCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public abstract void onAddedToMasterDeck();

    public abstract boolean ReplaceCardWithCollector();
}
