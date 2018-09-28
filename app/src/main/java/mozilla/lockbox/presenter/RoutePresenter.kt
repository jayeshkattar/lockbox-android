/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mozilla.lockbox.presenter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxkotlin.addTo
import mozilla.lockbox.R
import mozilla.lockbox.action.RouteAction
import mozilla.lockbox.flux.Presenter
import mozilla.lockbox.store.RouteStore
import mozilla.lockbox.view.FxALoginFragment
import mozilla.lockbox.view.ItemListFragment
import mozilla.lockbox.view.LockedFragment
import mozilla.lockbox.view.SettingFragment
import mozilla.lockbox.view.WelcomeFragment

class RoutePresenter(private val activity: AppCompatActivity, routeStore: RouteStore = RouteStore.shared) : Presenter() {
    private val welcome: WelcomeFragment by lazy { WelcomeFragment() }
    private val login: FxALoginFragment by lazy { FxALoginFragment() }
    private val itemList: ItemListFragment by lazy { ItemListFragment() }
    private val settingList: SettingFragment by lazy { SettingFragment() }
    private val lock: LockedFragment by lazy { LockedFragment() }

    init {
        routeStore.routes.subscribe { a -> route(a) }.addTo(compositeDisposable)
    }

    override fun onViewReady() {
        replaceFragment(this.welcome, false)
    }

    private fun replaceFragment(frag: androidx.fragment.app.Fragment, backable: Boolean = true) {
        val tx = activity.supportFragmentManager.beginTransaction()
        tx.replace(R.id.root_content, frag)
        if (backable) {
            tx.addToBackStack(null)
        }
        tx.commit()

        if (!backable) {
            clearBackStack()
        }
    }

    private fun clearBackStack() {
        val fm = activity.supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            val base = fm.getBackStackEntryAt(0)
            fm.popBackStackImmediate(base.id, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun route(action: RouteAction) {
        when (action) {
            RouteAction.LOGIN -> {
                replaceFragment(login)
            }
            RouteAction.WELCOME -> {
                replaceFragment(welcome, false)
            }
            RouteAction.ITEMLIST -> {
                replaceFragment(itemList, false)
            }
            RouteAction.SETTING_LIST -> {
                replaceFragment(settingList)
            }
            RouteAction.LOCK -> {
                replaceFragment(lock, false)
            }
            RouteAction.BACK -> {
                activity.supportFragmentManager.popBackStack()
            }
        }
    }
}
