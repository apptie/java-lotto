package lotto.domain.argument;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lotto.helper.util.TestArgumentUtils;
import lotto.util.ranking.LottoRanking;
import org.junit.jupiter.params.provider.Arguments;

public final class LottoStoreTestArgument {

    private LottoStoreTestArgument() {
    }

    private static final int RANKING_FIRST_INDEX = 0;
    private static final int RANKING_SECOND_INDEX = 1;
    private static final int RANKING_THIRD_INDEX = 2;
    private static final int RANKING_FOURTH_INDEX = 3;
    private static final int RANKING_FIFTH_INDEX = 4;

    static Stream<Arguments> calculateLottoResultArgument() {
        return Stream.of(
            Arguments.of(
                List.of(LottoRanking.RANKING_FIFTH,
                    LottoRanking.RANKING_FOURTH,
                    LottoRanking.RANKING_THIRD), "3000",
                TestArgumentUtils.createArgumentMap(List.of(0, 0, 1, 1, 1))),
            Arguments.of(
                List.of(LottoRanking.RANKING_FIFTH,
                    LottoRanking.RANKING_FIRST,
                    LottoRanking.RANKING_THIRD,
                    LottoRanking.RANKING_FIFTH,
                    LottoRanking.RANKING_SECOND), "5000",
                TestArgumentUtils.createArgumentMap(List.of(1, 1, 1, 0, 2))));
    }
}
