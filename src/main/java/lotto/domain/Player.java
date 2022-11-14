package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lotto.util.message.CommonMessageConst;
import lotto.util.number.LottoNumberConst;
import lotto.util.ranking.LottoRanking;

public class Player {

    private static final String LOTTO_COUNT_MESSAGE_FORMAT = "%d개를 구매했습니다.";

    private final LottoPurchaseAmount lottoPurchaseAmount;
    private final List<Lotto> myLottos;

    public Player(LottoPurchaseAmount lottoPurchaseAmount) {
        this.lottoPurchaseAmount = lottoPurchaseAmount;
        this.myLottos = initLottos();
    }

    private List<Lotto> initLottos() {
        BigInteger lottoAmount = lottoPurchaseAmount.calculatePurchaseLottoAmount();
        List<Lotto> myLottos = new ArrayList<>();

        while (isBiggerThanZero(lottoAmount)) {
            List<Integer> uniqueNumbers = Randoms.pickUniqueNumbersInRange(
                    LottoNumberConst.MIN_NUMBER_INT_VALUE,
                    LottoNumberConst.MAX_NUMBER_INT_VALUE,
                    LottoNumberConst.NUMBER_SIZE
            );

            myLottos.add(new Lotto(uniqueNumbers));
            lottoAmount = lottoAmount.subtract(BigInteger.ONE);
        }
        return myLottos;
    }

    private boolean isBiggerThanZero(BigInteger lottoAmount) {
        return lottoAmount.compareTo(BigInteger.ZERO) > 0;
    }

    public List<LottoRanking> calculateLottoRanking(Lotto winningLotto, LottoNumber bonusNumber) {
        final List<LottoRanking> rankings = new ArrayList<>();

        myLottos.forEach(lotto -> rankings.add(winningLotto.calculateLottoGrade(lotto, bonusNumber)));
        return rankings;
    }

    public BigDecimal calculateRevenuePercent(BigDecimal totalReward) {
        return lottoPurchaseAmount.calculateRevenuePercent(totalReward);
    }

    @Override
    public String toString() {
        StringBuilder playerMessage = new StringBuilder(CommonMessageConst.LINE_FEED);

        playerMessage
                .append(String.format(LOTTO_COUNT_MESSAGE_FORMAT, lottoPurchaseAmount.calculatePurchaseLottoAmount()))
                .append(CommonMessageConst.LINE_FEED);

        String myLottosMessage = myLottos.stream()
                .map(Lotto::toString)
                .collect(Collectors.joining(CommonMessageConst.LINE_FEED));
        playerMessage.append(myLottosMessage);

        return playerMessage.toString();
    }
}
