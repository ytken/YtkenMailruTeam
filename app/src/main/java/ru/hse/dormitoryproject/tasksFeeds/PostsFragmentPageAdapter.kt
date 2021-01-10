package ru.hse.dormitoryproject.tasksFeeds

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PostsFragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    companion object {
        const val PAGE_NUMB = 3
    }

    override fun getItemCount(): Int {
        return PAGE_NUMB
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = Fragment()
        when (position) {
            0 -> fragment = FragmentAvailableTasksFeed.getNewInstance(position + 1)
            1 -> fragment = FragmentAcceptedTasksFeed.getNewInstance(position+1)
            2 -> fragment = FragmentYourTasksFeed.getNewInstance(position + 1)
        }
        return fragment;
    }
}