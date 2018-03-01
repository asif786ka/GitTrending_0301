package com.githubrepo.mvp.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A {@link MvpPresenter} implmenetation that implements the <a href="https://en.wikipedia.org/wiki/Null_Object_pattern">null
 * object pattern</a> for the attached mvp view. So whenever the view gets detached from this
 * presenter by calling{@link #detachView(boolean)}, a new "null object" view gets dynamically
 * instantiated by using reflections and set as the current
 * view (instead of null). The new "null object" view simply does nothing. This avoids null pointer
 * exceptions and checks like {@code if (getView() != null)}
 */
public class MvpNullObjectBasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (null != view) {
            Type[] types =
                    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

            final Class<V> viewClass = (Class<V>) types[0];
            view = NoOp.of(viewClass);
        }
    }

    public V getView() {
        if (null == view) {
            throw new NullPointerException("MvpView reference is null. Have you called attachView()?");
        }
        return view;
    }
}
