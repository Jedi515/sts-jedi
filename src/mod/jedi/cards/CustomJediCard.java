package mod.jedi.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;

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
        super(id, name, imgCheck(id, img, type, color), cost, rawDescription, type, color, rarity, target);
        isSecondMNModified = false;
    }

    private static String imgCheck(String id, String img, CardType type, CardColor color)
    {
        String imgCheck = "resources/jedi/images/cards/" + color.toString().toLowerCase() + "/" + type.toString().toLowerCase() + "/" + id.substring(5) + ".png";
        if (Gdx.files.internal(imgCheck).exists()) return imgCheck;

        if ((img == null) || (!Gdx.files.internal(img).exists()))
        {
            switch (type)
            {
                case ATTACK:
                    img = "resources/jedi/images/cards/jedi_beta_attack.png";
                    break;
                case POWER:
                    img = "resources/jedi/images/cards/jedi_beta_power.png";
                    break;
                default:
                    img = "resources/jedi/images/cards/jedi_beta.png";
                    break;
            }
        }
        return img;
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
