package com.example.testnav.chatviewpager

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testnav.R
import com.example.testnav.Utils
import com.example.testnav.model.Request
import com.example.testnav.model.Utils.OpenChatActivity
import com.example.testnav.view.ChatActivity
import com.example.testnav.view.CircularIndicator
import com.example.testnav.view.ShowRequests
import com.example.testnav.viewmodel.RoomViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.quickblox.chat.model.QBChatDialog
import kotlinx.coroutines.flow.collect

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val roomViewModel: RoomViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            //val user: FirebaseUser = auth.currentUser!!

                        roomViewModel.ReadDialogs().observe(this@MessageFragment.viewLifecycleOwner, Observer {


            setContent {
                val numbers = (1..10).toList()
                val List = remember { mutableStateOf(listOf<QBChatDialog>()) }
                val Active = remember { mutableStateOf(false) }

                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn {
                        itemsIndexed(items = it){ index, it ->
                            CardView(it)
                        }
                    }
                    /*lifecycleScope.launchWhenCreated {
                        roomViewModel.EmitDialogs()
                        roomViewModel.dialogUiState.collect { dialogUiState ->

                            when(dialogUiState) {
                                is RoomViewModel.DialogUiState.Success -> {
                                    List.value = dialogUiState.dialog!!
                                    Active.value = true
                                }
                                is RoomViewModel.DialogUiState.Error -> {

                                }
                            }

                        }
                    }*/
                    /*when (Active.value) {
                        true -> {

                            /*LazyColumn {
                                itemsIndexed(items = List.value){ index, List ->
                                    CardView(List)
                                }
                            }*/

                        }
                        false -> {
                            CircularIndicator()
                        }
                    }*/


                }
        }
                        })
    }

    }

    //@Preview
    @Composable
    fun CardView(dialog: QBChatDialog){
        Card(modifier = Modifier
            .preferredHeight(95.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable {

                context?.let {
                    OpenChatActivity(it, dialog)
                }

            }
        ){
            Row(modifier = Modifier.fillMaxWidth()
                .absolutePadding(left = 8.dp, right = 8.dp, top = 10.dp, bottom = 10.dp),
                Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageResource(R.drawable.profile),
                    modifier = Modifier
                        .preferredWidth(80.dp)
                        .preferredHeight(110.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop

                )
                Column(modifier = Modifier.absolutePadding(left = 8.dp)) {
                    Text(text = "${dialog.name}")
                    Text(text = "${dialog.lastMessage}")
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MessageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}