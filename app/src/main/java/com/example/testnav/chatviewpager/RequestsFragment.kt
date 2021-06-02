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
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.testnav.MaterialThemee
import com.example.testnav.R
import com.example.testnav.model.User
import com.example.testnav.model.Utils.OpenRequetAtivity
import com.example.testnav.view.CircularIndicator
import com.example.testnav.view.ShowRequests
import com.example.testnav.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    ): View? {

        return ComposeView(requireContext()).apply {
            var auth = FirebaseAuth.getInstance()
            var user: FirebaseUser = auth.currentUser!!
            //viewModel.ReceivedMessageRequest(user.uid).observe(viewLifecycleOwner, Observer { user ->

            setContent {
                MaterialTheme(
                    colors = if (isSystemInDarkTheme())
                        MaterialThemee.darkColor else MaterialThemee.lightColor
                ) {
                    val ListUser = ArrayList<User>()
                    var Active = remember { mutableStateOf(false) }
                    viewModel.EmitReceivedRequest(user.uid).observe(viewLifecycleOwner, Observer {
                        ListUser.clear()
                        for (i in it) {
                            ListUser.add(i)
                            if (!ListUser.isEmpty()) {
                                Active.value = true
                            }
                        }


                    })
                    ConstraintLayout(modifier = Modifier.background(MaterialTheme.colors.onBackground)) {

                        when (Active.value) {
                            true -> {
                                ShowRequests(list = ListUser, context = context)
                            }
                            false -> {
                                CircularIndicator()
                            }
                        }


                    }

                }
                //})
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