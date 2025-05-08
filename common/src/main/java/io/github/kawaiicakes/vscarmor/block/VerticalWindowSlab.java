package io.github.kawaiicakes.vscarmor.block;

public class VerticalWindowSlab extends AbstractWindowSlab {
    public VerticalWindowSlab(Properties settings) {
        super(
                settings
                        .noOcclusion()
                        .isValidSpawn(AbstractWindowBlock::ezPredicateLol)
                        .isRedstoneConductor(AbstractWindowBlock::ezPredicate)
                        .isViewBlocking(AbstractWindowBlock::ezPredicate)
                        .isSuffocating(AbstractWindowBlock::ezPredicate)
        );
    }
}
