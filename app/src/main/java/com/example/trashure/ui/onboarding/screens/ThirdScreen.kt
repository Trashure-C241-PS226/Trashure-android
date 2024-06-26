package com.example.trashure.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.trashure.R

class ThirdScreen : Fragment() {

    private lateinit var finishButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_third_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        finishButton = view.findViewById(R.id.finish_button)
        finishButton.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_roleFragment)
            onBoardingFinished()
        }

        val skip = view.findViewById<TextView>(R.id.skip_text)
        skip.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_roleFragment)
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}