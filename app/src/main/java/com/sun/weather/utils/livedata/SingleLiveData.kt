package com.sun.weather.utils.livedata

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.weather.utils.livedata.SafeObserver
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(
        owner: LifecycleOwner,
        observer: Observer<in T>,
    ) {
        if (hasActiveObservers()) {
            Log.d(
                "",
                "Multiple observers registered but only one will be notified of changes.",
            )
        }
        super.observe(
            owner,
            SafeObserver<T> { t ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            },
        )
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }
}

@MainThread
inline fun <T> LiveData<T>.observeLiveData(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit,
) {
    this.observe(owner, Observer { onChanged(it) })
}
