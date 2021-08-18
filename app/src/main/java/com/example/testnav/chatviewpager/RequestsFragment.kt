package com.example.testnav.chatviewpager

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testnav.MaterialThemee
import com.example.testnav.R
import com.example.testnav.model.Preferences.SharedPreferences
import com.example.testnav.model.Request
import com.example.testnav.model.User
import com.example.testnav.model.Utils.OpenRequetAtivity
import com.example.testnav.view.CircularIndicator
import com.example.testnav.view.ShowRequests
import com.example.testnav.viewmodel.MainViewModel
import com.example.testnav.viewmodel.RoomViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.flow.collect

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class RequestsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    private var currentUser = QBUser()
    private var liveData = MutableLiveData<ArrayList<Request>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {
                MaterialTheme(
                    colors = if (isSystemInDarkTheme())
                        MaterialThemee.darkColor else MaterialThemee.lightColor
                ) {
                    ConstraintLayout(modifier = Modifier.background(MaterialTheme.colors.onBackground)) {
                        val List = remember { mutableStateOf(listOf<Request>()) }
                        val Active = remember { mutableStateOf(false) }
                        currentUser = SharedPreferences.GetCurrentUser()

                        lifecycleScope.launchWhenCreated {

                            roomViewModel.uiState.collect { uiState ->
                                context.let{ roomViewModel.EmitRequestsById(it, currentUser.id.toString()) }
                                when(uiState){
                                    is RoomViewModel.RequestByIdUiState.Success -> {
                                        Log.e(TAG, "${uiState.requests}")
                                        if(uiState.requests != null){
                                            List.value = uiState.requests!!.asReversed()
                                            Active.value = true
                                        }
                                    }
                                    is RoomViewModel.RequestByIdUiState.Error ->  Log.e(TAG, "${uiState.exception}")
                                }
                            }
                        }
                        when (Active.value) {
                            true -> {
                                if(!List.value.isEmpty()){
                                    ShowRequests(list = List.value, context = context)
                                }
                                else{
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center)
                                    {
                                        Text(
                                            modifier = Modifier.absolutePadding(top = 20.dp),
                                            text = "There Are Not Requests",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold)
                                    }

                                }
                            }
                            false -> {
                                CircularIndicator()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}