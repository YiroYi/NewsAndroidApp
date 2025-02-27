package com.anushka.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.databinding.FragmentNewsBinding
import com.anushka.newsapiclient.presentation.adapter.NewsAdapter
import com.anushka.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
  private lateinit var viewModel: NewsViewModel
  private lateinit var fragmentNewsBinding: FragmentNewsBinding
  private lateinit var newsAdapter: NewsAdapter
  private var country = "US"
  private var page = 1
  private var isScrolling = false
  private var isLoading = false
  private var isLastPage = false
  private var pages = 0

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    return inflater.inflate(R.layout.fragment_news, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fragmentNewsBinding = FragmentNewsBinding.bind(view)
    viewModel = (activity as MainActivity).viewModel
    newsAdapter = (activity as MainActivity).newsAdapter
    newsAdapter.setOnItemClickListener { article ->
      // Use Safe Args for navigation
      val action = NewsFragmentDirections.actionNewsFragmentToInfoFragment(article)
      findNavController().navigate(action)
    }

    initRecyclerView()
    viewNewsList()
    setSearchView()
  }

  private fun viewNewsList() {
    viewModel.getNewsHeadLines(country, page)
    viewModel.newHeadLines.observe(viewLifecycleOwner, { response ->
      when (response) {
        is Resource.Success -> {
          hideProgressBar()
          response.data?.let {
            newsAdapter.differ.submitList(it.articles.toList())
            if (it.totalResults % 20 == 0) {
              pages = it.totalResults / 20
            } else {
              pages = it.totalResults / 20 + 1
            }

            isLastPage = page == pages
          }
        }

        is Resource.Error -> {
          hideProgressBar()
          Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
        }

        is Resource.Loading -> {
          showProgressBar()
        }
      }
    })
  }

  private fun initRecyclerView() {
    // newsAdapter = NewsAdapter()
    fragmentNewsBinding.rvNews.apply {
      adapter = newsAdapter
      layoutManager = LinearLayoutManager(activity)
      addOnScrollListener(this@NewsFragment.onScrollListener)
    }
  }

  private fun showProgressBar() {
    isLoading = true
    fragmentNewsBinding.progressBar.visibility = View.VISIBLE
  }

  private fun hideProgressBar() {
    isLoading = false
    fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
  }

  private val onScrollListener = object : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
      super.onScrollStateChanged(recyclerView, newState)
      if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
        isScrolling = true
      }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      super.onScrolled(recyclerView, dx, dy)
      val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
      val sizeOfTheCurrentList = layoutManager.itemCount
      val visibleItems = layoutManager.childCount
      val topPosition = layoutManager.findFirstVisibleItemPosition()

      val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
      val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

      if (shouldPaginate) {
        page++
        viewModel.getNewsHeadLines(country, page)
        isScrolling = false
      }
    }

  }

  private fun setSearchView() {
    fragmentNewsBinding.svNews.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
      override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.searchNews("us", query.toString(), page)
        viewSearchedNews()
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        MainScope().launch {
          delay(2000)
          viewModel.searchNews("us", newText.toString(), page)
          viewSearchedNews()

        }
        return false
      }
    })

    fragmentNewsBinding.svNews.setOnCloseListener(object: SearchView.OnCloseListener {
      override fun onClose(): Boolean {
        initRecyclerView()
        viewNewsList()
        return false
      }

    })
  }

  private fun viewSearchedNews() {
    viewModel.getNewsHeadLines(country, page)
    viewModel.searchedNews.observe(viewLifecycleOwner, { response ->
      when (response) {
        is Resource.Success -> {
          hideProgressBar()
          response.data?.let {
            newsAdapter.differ.submitList(it.articles.toList())
            if (it.totalResults % 20 == 0) {
              pages = it.totalResults / 20
            } else {
              pages = it.totalResults / 20 + 1
            }

            isLastPage = page == pages
          }
        }

        is Resource.Error -> {
          hideProgressBar()
          Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
        }

        is Resource.Loading -> {
          showProgressBar()
        }
      }
    })
  }
}