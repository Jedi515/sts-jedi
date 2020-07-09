package mod.jedi.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;

@AutoAdd.Ignore
public abstract class CustomJediCard
    extends CustomCard
{
    public int secondMN;
    public int baseSecondMN;
    public boolean isSecondMNUpgraded;
    public boolean isSecondMNModified;

    public static String makeCardId(String ID)
    {
        return "jedi:" + ID;
    }

    public CustomJediCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        isSecondMNModified = false;
    }

    public void displayUpgrades()
    {
        super.displayUpgrades();
        if (isSecondMNUpgraded)
        {
            secondMN = baseSecondMN;
            isSecondMNModified = true;
        }
    }

    public void upgradeSecondMN(int amount)
    {
        baseSecondMN += amount;
        secondMN = baseSecondMN;
        isSecondMNUpgraded = true;
    }
}
