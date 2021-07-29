package com.example.testnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.example.testnav.view.Profile

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FriendsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
            setContent {
                MaterialTheme(
                    colors = if (isSystemInDarkTheme())
                        MaterialThemee.darkColor else MaterialThemee.lightColor
                ) {
                    ConstraintLayout(modifier = Modifier.background(MaterialTheme.colors.onBackground))
                    {
                        Profile()
                        val numbers = (0..20).toList()

                        /*LazyVerticalGrid(
                            cells = GridCells.Fixed(3)
                        ) {
                            items(numbers.size) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    //Text(text = "Number")
                                    //Text(text = "  $it")
                                }
                            }
                        }*/
                    }
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FriendsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}