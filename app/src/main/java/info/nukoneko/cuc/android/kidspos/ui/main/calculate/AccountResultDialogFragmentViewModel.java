package info.nukoneko.cuc.android.kidspos.ui.main.calculate;

import android.databinding.ObservableInt;

public final class AccountResultDialogFragmentViewModel {
    private ObservableInt mPrice = new ObservableInt();
    private ObservableInt mReceiveMoney = new ObservableInt();

    AccountResultDialogFragmentViewModel(int price, int receiveMoney) {
        mPrice.set(price);
        mReceiveMoney.set(receiveMoney);
    }

    public ObservableInt getPrice() {
        return mPrice;
    }

    public ObservableInt getReceiveMoney() {
        return mReceiveMoney;
    }
}