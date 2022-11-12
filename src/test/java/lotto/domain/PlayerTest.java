package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lotto.helper.factory.stub.StubWinningLotto;
import lotto.helper.util.LottoResultTestUtils;
import lotto.helper.util.PlayerTestUtils;
import lotto.util.number.LottoNumberFactory;
import lotto.util.ranking.LottoRanking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PlayerTest {

    @Nested
    @DisplayName("LottoPurchaseAmount lottoPurchaseAmount를 매개변수로 받는 생성자는")
    class LottoPurchaseAmountConstructorTest {

        @ParameterizedTest
        @CsvSource(
                value = {
                    "1000:1",
                    "2000:2",
                    "5000:5",
                    "10000:10"
                },
                delimiter = ':'
        )
        @DisplayName("만약 lottoPurchaseAmount가 주어지면 그 크기만큼 Lotto를 생성하고 초기화한 Player를 반환한다.")
        void success_test(String input, int size) {
            Player player = new Player(new LottoPurchaseAmount(input));

            List<Lotto> playerLottos = PlayerTestUtils.findPlayerLottos(player);

            assertThat(playerLottos.size()).isSameAs(size);
        }
    }

    @Nested
    @DisplayName("calculateLottoRanking 메소드는")
    class CalculateLottoRankingMethodTest {

        private final LottoNumber dummyBonusNumber = LottoNumberFactory.numberOf(1);

        @ParameterizedTest
        @MethodSource("lotto.domain.argument.PlayerTestArgument#calculateLottoGradeArgument")
        @DisplayName("만약 당첨 번호와 보너스 번호와 LottoResult가 주어지면 LottoResult에 당첨 결과를 계산한다.")
        void success_test(List<LottoRanking> rankings, String amountInput, Map<LottoRanking, Integer> expectedMap) {
            LottoResult lottoResult = new LottoResult();
            Player player = new Player(new LottoPurchaseAmount(amountInput));

            player.calculateLottoRanking(lottoResult, new StubWinningLotto(rankings), dummyBonusNumber);

            Map<LottoRanking, Integer> actualMap = LottoResultTestUtils.findLottoRankingResult(lottoResult);

            Arrays.stream(LottoRanking.values())
                    .forEach(lottoRanking ->
                            assertThat(actualMap.getOrDefault(lottoRanking, 0))
                                    .isSameAs(expectedMap.get(lottoRanking)));
        }
    }

    @Nested
    @DisplayName("toString 메소드는")
    class ToStringMethodTest {

        private final String purchaseLogFormat = "개를 구매했습니다.";

        @ParameterizedTest
        @ValueSource(strings = {"1000", "2000", "5000", "10000"})
        @DisplayName("만약 호출되면 List<Lotto>와 LottoPurchaseAmount를 정해진 규격에 따라 문자열로 반환한다.")
        void success_test(String input) {
            Player player = new Player(new LottoPurchaseAmount(input));

            String log = player.toString();

            assertThat(log).contains(purchaseLogFormat);
        }
    }
}