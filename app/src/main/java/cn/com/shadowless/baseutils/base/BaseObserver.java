package cn.com.shadowless.baseutils.base;

import androidx.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * The type Base observer.
 *
 * @param <T> the type parameter
 * @author sHadowLess
 */
public abstract class BaseObserver<T> implements Observer<T> {
    /**
     * The Disposable.
     */
    private Disposable disposable = null;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public abstract void onError(@NonNull Throwable e);

    @Override
    public void onComplete() {
        disposable.dispose();
    }
}