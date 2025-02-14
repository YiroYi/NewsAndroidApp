package com.anushka.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.databinding.FragmentNewsBinding
import com.anushka.newsapiclient.presentation.adapter.NewsAdapter
import com.anushka.newsapiclient.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
  private lateinit var viewModel: NewsViewModel
  private lateinit var fragmentNewsBinding: FragmentNewsBinding
  private lateinit var newsAdapter: NewsAdapter
  private var country = "US"
  private var page = 1

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
    initRecyclerView()
    viewNewsList()
  }

  private fun viewNewsList() {
    viewModel.getNewsHeadLines(country, page)
    viewModel.newHeadLines.observe(viewLifecycleOwner, {
      response ->
      when(response) {
        is Resource.Success -> {
          hideProgressBar()
          response.data?.let {
            newsAdapter.differ.submitList(it.articles.toList())
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
    newsAdapter = NewsAdapter()
    fragmentNewsBinding.rvNews.apply {
      adapter = newsAdapter
      layoutManager = LinearLayoutManager(activity)
    }
  }

  private fun showProgressBar() {
    fragmentNewsBinding.progressBar.visibility = View.VISIBLE
  }

  private fun hideProgressBar() {
    fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
  }
}