package info.nukoneko.cuc.android.kidspos.ui.main.storelist

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import info.nukoneko.cuc.android.kidspos.R
import info.nukoneko.cuc.android.kidspos.databinding.FragmentStoreListDialogBinding
import info.nukoneko.cuc.android.kidspos.entity.Store
import org.koin.android.viewmodel.ext.android.viewModel

class StoreListDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentStoreListDialogBinding
    private val myViewModel: StoreListViewModel by viewModel()
    private val listener = object : StoreListViewModel.Listener {
        override fun onDismiss() {
            dismiss()
        }

        override fun onShouldShowErrorDialog(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private val adapterListener = object : StoreListViewAdapter.Listener {
        override fun onStoreSelect(store: Store) {
            myViewModel.onSelect(store)
        }
    }

    private val adapter: StoreListViewAdapter by lazy {
        StoreListViewAdapter().also {
            it.listener = adapterListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_list_dialog, container, false)
        binding.setLifecycleOwner(this)
        myViewModel.listener = listener
        binding.viewModel = myViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setupRecyclerView()
        setupSubscriber()
    }

    override fun onResume() {
        super.onResume()
        myViewModel.onResume()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSubscriber() {
        myViewModel.getData().observe(this, Observer<List<Store>> { stores ->
            val newData = stores ?: emptyList()
            adapter.data = newData
        })
    }

    companion object {
        fun newInstance(): StoreListDialogFragment = StoreListDialogFragment()
    }
}
