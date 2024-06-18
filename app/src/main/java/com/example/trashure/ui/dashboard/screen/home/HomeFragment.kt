package com.example.trashure.ui.dashboard.screen.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trashure.R
import com.example.trashure.data.api.config.ApiConfig
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.repository.Repository
import com.example.trashure.databinding.FragmentHomeBinding
import com.example.trashure.viewModelFactory.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory(
            Repository(
                ApiConfig.getApiService(getTokenUser()),
                UserPreference.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initUI()

        return root
    }

    private fun initUI() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.username.text = user.email
        }
    }

    private fun getTokenUser(): String {
        val sharedPreferences =
            requireActivity().getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_TOKEN, "") ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val USER_PREF = "user_preferences"
        private const val USER_TOKEN = "user_token"
    }
}