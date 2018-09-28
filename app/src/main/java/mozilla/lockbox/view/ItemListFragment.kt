/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mozilla.lockbox.view

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import mozilla.lockbox.R
import mozilla.lockbox.adapter.ItemListAdapter
import mozilla.lockbox.model.ItemViewModel
import mozilla.lockbox.presenter.ItemListPresenter
import mozilla.lockbox.presenter.ItemListView

class ItemListFragment : CommonFragment(), ItemListView {
    private val compositeDisposable = CompositeDisposable()
    private val adapter = ItemListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = ItemListPresenter(this)
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        view.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_menu, null)
        view.toolbar.navigationClicks().subscribe { view.appDrawer.openDrawer(GravityCompat.START) }
                .addTo(compositeDisposable)

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        view.entriesView.layoutManager = layoutManager
        view.entriesView.adapter = adapter
        val decoration = androidx.recyclerview.widget.DividerItemDecoration(context, layoutManager.orientation)
        val decorationDrawable = context?.getDrawable(R.drawable.inset_divider)
        decorationDrawable?.let { decoration.setDrawable(it) }
        view.entriesView.addItemDecoration(decoration)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    // Protocol implementations
    override val drawerItemSelections: Observable<MenuItem>
        get() = view!!.navView.itemSelections()

    override fun closeDrawers() {
        view!!.appDrawer.closeDrawer(GravityCompat.START, false)
    }

    override fun updateItems(itemList: List<ItemViewModel>) {
        adapter.updateItems(itemList)
    }
}
