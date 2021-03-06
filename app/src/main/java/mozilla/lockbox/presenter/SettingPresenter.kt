/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mozilla.lockbox.presenter

import mozilla.lockbox.flux.Presenter

interface SettingViewProtocol

class SettingPresenter(view: SettingViewProtocol) : Presenter() {
    override fun onViewReady() {
    }
}