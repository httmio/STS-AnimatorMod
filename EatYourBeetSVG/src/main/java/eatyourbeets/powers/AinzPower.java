package eatyourbeets.powers;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import eatyourbeets.Utilities;

public class AinzPower extends AnimatorPower
{
    private int upgradedPowerStack;
    private AbstractPlayer player;
    private boolean upgraded;

    public AinzPower(AbstractPlayer owner, boolean upgraded)
    {
        super(owner, "Ainz");
        this.amount = 1;
        this.upgraded = upgraded;
        if (this.upgraded)
        {
            this.upgradedPowerStack = 1;
        }
        this.player = Utilities.SafeCast(this.owner, AbstractPlayer.class);
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        super.atStartOfTurnPostDraw();

        for(int i = 0; i < this.amount; i++)
        {
            AddPowerForm(i < upgradedPowerStack);
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        AinzPower other = Utilities.SafeCast(power, AinzPower.class);
        if (other != null && power.owner == target && other.upgraded)
        {
            this.upgradedPowerStack += 1;
        }

        super.onApplyPower(power, target, source);
    }

    private void AddPowerForm(boolean upgrade)
    {
        AbstractCard power;
        int roll = AbstractDungeon.miscRng.random(2);
        switch (roll)
        {
            case 0: power = new DemonForm(); break;
            case 1: power = new EchoForm(); break;
            case 2: power = new WraithForm(); break;

            default:
                throw new IndexOutOfBoundsException();
        }

        if (upgrade)
        {
            power.upgrade();
        }

        if (player.hand.size() >= BaseMod.MAX_HAND_SIZE)
        {
            AbstractDungeon.player.createHandIsFullDialog();
            player.discardPile.addToBottom(power);
        }
        else
        {
            player.hand.addToHand(power);
        }
    }
}
