package com.example.trashure.ui.dashboard.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trashure.databinding.FragmentProfileBinding
import com.example.trashure.viewModelFactory.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun initUI() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.tvEmailProfile.text = user.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}