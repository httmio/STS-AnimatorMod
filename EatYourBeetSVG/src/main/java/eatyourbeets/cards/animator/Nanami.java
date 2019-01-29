package eatyourbeets.cards.animator;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.red.Pummel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Nanami extends AnimatorCard
{
    public static final String ID = CreateFullID(Nanami.class.getSimpleName());

    public Nanami()
    {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        Initialize(0,4);

        AddSynergy(Synergies.Katanagatari);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        if (HasSynergy())
        {
            Pummel pummel = new Pummel();
            if (upgraded)
            {
                pummel.upgrade();
            }

            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE)
            {
                p.createHandIsFullDialog();
                p.discardPile.addToBottom(pummel);
            }
            else
            {
                p.hand.addToHand(pummel);
                pummel.flash();
            }
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeBlock(2);
        }
    }
}