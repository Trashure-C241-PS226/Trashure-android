package com.example.trashure.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.trashure.R
import com.example.trashure.ui.onboarding.screens.FirstScreen
import com.example.trashure.ui.onboarding.screens.SecondScreen
import com.example.trashure.ui.onboarding.screens.ThirdScreen
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val dotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)

        return view
    }
}