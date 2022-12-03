package lotto.domain.game;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.Predicate;

public enum LottoRanking {
    NOTHING(new BigDecimal(0L),
            0L,
            correctBonus -> true),

    FIFTH(new BigDecimal(5_000L),
            3L,
            correctBonus -> true),

    FOURTH(new BigDecimal(50_000L),
            4L,
            correctBonus -> true),

    THIRD(new BigDecimal(1_500_000L),
            5L,
            correctBonus -> !correctBonus),

    SECOND(new BigDecimal(30_000_000L),
            5L,
            correctBonus -> correctBonus),

    FIRST(new BigDecimal(2_000_000_000L),
            6L,
            correctBonus -> true);

    private static final DecimalFormat rewardFormat = new DecimalFormat("(###,###원)");

    private final BigDecimal lottoRankingReward;
    private final long correctNumberCount;
    private final Predicate<Boolean> bonusNumberPredicate;

    LottoRanking(BigDecimal lottoRankingReward, long correctNumberCount,
            Predicate<Boolean> bonusNumberPredicate) {
        this.lottoRankingReward = lottoRankingReward;
        this.correctNumberCount = correctNumberCount;
        this.bonusNumberPredicate = bonusNumberPredicate;
    }

    public String findRewardMessage() {
        return rewardFormat.format(lottoRankingReward);
    }

    public BigDecimal calculateTotalTargetLottoRankingReward(int numberOfWins) {
        return lottoRankingReward.multiply(new BigDecimal(numberOfWins));
    }

    public boolean hasReward() {
        return this != NOTHING;
    }

    public static LottoRanking findLottoRanking(long correctNumberCount, boolean isCorrectBonusNumber) {
        return Arrays.stream(LottoRanking.values())
                .filter(lottoRanking -> lottoRanking.correctNumberCount == correctNumberCount)
                .filter(lottoRanking -> lottoRanking.bonusNumberPredicate.test(isCorrectBonusNumber))
                .findAny()
                .orElse(LottoRanking.NOTHING);
    }
}
