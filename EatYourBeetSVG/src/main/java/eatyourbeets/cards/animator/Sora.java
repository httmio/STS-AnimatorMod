//package eatyourbeets.cards.animator;
//
//import com.megacrit.cardcrawl.cards.CardGroup;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import eatyourbeets.GameActionsHelper;
//import eatyourbeets.actions.SoraAction;
//import eatyourbeets.cards.AnimatorCard;
//import eatyourbeets.cards.Synergies;
//
//public class Sora extends AnimatorCard
//{
//    public static final String ID = CreateFullID(Sora.class.getSimpleName());
//
//    public Sora()
//    {
//        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ALL);
//
//        Initialize(0,0, 2);
//
//        SetSynergy(Synergies.NoGameNoLife);
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m)
//    {
//        GameActionsHelper.Special(new SoraAction(this.magicNumber));
//    }
//
//    @Override
//    public void upgrade()
//    {
//        if (TryUpgrade())
//        {
//            upgradeMagicNumber(1);
//        }
//    }
//}