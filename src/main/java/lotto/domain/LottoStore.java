package lotto.domain;

import java.math.BigDecimal;

public class LottoStore {

    private final Player player;
    private Lotto winningLotto;

    public LottoStore(LottoPurchaseAmount lottoPurchaseAmount) {
        this.player = new Player(lottoPurchaseAmount);
    }

    public void createWinningLotto(String winningNumbers) {
        winningLotto = new Lotto(winningNumbers);
    }

    public LottoResult calculateLottoResult(LottoNumber bonusNumber) {
        return new LottoResult(player, winningLotto, bonusNumber);
    }

    public BigDecimal calculateRevenuePercent(BigDecimal totalReward) {
        return player.calculateRevenuePercent(totalReward);
    }

    public String findPlayerInfo() {
        return player.toString();
    }
}
