package kekify.io.hackteam;


import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    public static CompletableTransformer applyCompletableSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static <T> SingleTransformer<T, T> applySingleSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static <T> ObservableTransformer<T, T> applyObservableSchedulers() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applyOpBeforeAndAfter(
            Runnable before, Runnable after) {
        return tObservable -> tObservable.doOnComplete(after::run).doOnSubscribe(t-> before.run());
    }

    public static <T> ObservableTransformer<T, T> applyOpAfter(Runnable after) {
        return tObservable -> tObservable.doOnComplete(after::run);
    }
    public static <T> ObservableTransformer<T, T> applyShortDelay() {
        return tObservable -> tObservable
                .delay(0, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
    }
}
