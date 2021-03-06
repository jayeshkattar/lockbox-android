/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mozilla.lockbox.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_fxa_login.view.*
import mozilla.lockbox.R
import mozilla.lockbox.presenter.FxALoginPresenter
import mozilla.lockbox.presenter.FxALoginViewProtocol

class FxALoginFragment : BackableFragment(), FxALoginViewProtocol {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = FxALoginPresenter(this)
        val view = inflater.inflate(R.layout.fragment_fxa_login, container, false)
        setupBackable(view, android.R.drawable.ic_menu_close_clear_cancel)
        return view
    }

    override val logMeInClicks: Observable<Unit>
        get() = view!!.logMeInButton.clicks()
}
