package info.nukoneko.cuc.android.kidspos.ui.main.storelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.nukoneko.cuc.android.kidspos.R
import info.nukoneko.cuc.android.kidspos.databinding.FragmentStoreListDialogBinding
import info.nukoneko.cuc.android.kidspos.model.entity.Store
import org.koin.android.viewmodel.ext.android.viewModel

class StoreListDialogFragment : DialogFragment() {
    private val myViewModel: StoreListViewModel by viewModel()

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
        val binding: FragmentStoreListDialogBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_store_list_dialog,
                        container, false)
        binding.lifecycleOwner = this
        binding.viewModel = myViewModel
        setupSubscriber()
        setupList(binding.recyclerView)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onResume() {
        super.onResume()
        myViewModel.onResume()
    }

    private fun setupList(list: RecyclerView) {
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        list.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSubscriber() {
        myViewModel.dismissView.observe(this, Observer {
            dismiss()
        })
        myViewModel.presentErrorAlert.observe(this, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
        myViewModel.data.observe(this, Observer { stores ->
            adapter.data = stores ?: emptyList()
        })
    }

    companion object {
        fun newInstance(): StoreListDialogFragment = StoreListDialogFragment()
    }
}
