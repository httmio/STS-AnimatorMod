package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.MedicalKit;
import eatyourbeets.actions.ShuffleRandomGoblinAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.AnimatorCard_Status;
import eatyourbeets.cards.Synergies;

public class GoblinKing extends AnimatorCard_Status
{
    public static final String ID = CreateFullID(GoblinKing.class.getSimpleName());

    public GoblinKing()
    {
        super(ID, 1, CardRarity.RARE, CardTarget.NONE);

        Initialize(0,0, 6);

        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (!this.dontTriggerOnUseCard)
        {
            if (p.hasRelic(MedicalKit.ID))
            {
                this.useMedicalKit(p);
            }
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ShuffleRandomGoblinAction(3));
        }
    }
}