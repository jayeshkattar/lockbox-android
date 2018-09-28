/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mozilla.lockbox.flux

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

abstract class Presenter {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun onViewReady() {
    }

    @CallSuper
    open fun onDestroy() {
        compositeDisposable.clear()
    }
}
