package com.lohanna.catalogodefilmes.ui.movies.view.search

import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.ColorRes
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lohanna.catalogodefilmes.R
import com.lohanna.catalogodefilmes.databinding.AlertDialogMovieDetailsBinding
import com.lohanna.catalogodefilmes.databinding.FragmentSearchBaseLayoutBinding
import com.lohanna.catalogodefilmes.ui.base.adapter.ItemClickListener
import com.lohanna.catalogodefilmes.ui.base.model.ItemModel
import com.lohanna.catalogodefilmes.ui.util.ViewUtils.removeLinksUnderline
import com.lohanna.catalogodefilmes.ui.component.Loading
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel
import com.lohanna.catalogodefilmes.ui.movies.viewModel.MoviesSearchViewModel
import com.lohanna.catalogodefilmes.ui.movies.viewModel.MoviesSearchViewModelImp
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesSearchFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentSearchBaseLayoutBinding
    private lateinit var adapter: MoviesSearchAdapter

    private val layoutViewModel: MoviesSearchLayoutViewModel by viewModels<MoviesSearchLayoutViewModelImp>()
    private val dataViewModel: MoviesSearchViewModel by viewModels<MoviesSearchViewModelImp>()

    private var lastDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBaseLayoutBinding.inflate(
            inflater,
            container,
            false
        )
        setUI()
        return binding.root
    }

    private fun setUI() {
        getData()
        setMaterialToolbar()
        setAdapter()
        setObservers()
        setSearchView()
    }

    private fun getData() {
        Loading.show(requireContext())
        dataViewModel.getMoviesByTerm("Duna")
    }

    private fun setMaterialToolbar() {
        binding.toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
        binding.toolbar.setTitleTextColor(getColor(R.color.colorToolbarText))
        binding.toolbar.setNavigationIconTint(getColor(R.color.colorDetail))
        updateMaterialToolbarTitle()
    }

    private fun updateMaterialToolbarTitle() {
        binding.toolbar.title =
            getString(R.string.toolbar_title, dataViewModel.movies.value?.size ?: 0)
    }

    private fun setAdapter() {
        adapter = MoviesSearchAdapter()
        adapter.listener = this
        binding.rvContainer.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvContainer.adapter = adapter
    }

    private fun setObservers() {
        layoutViewModel.layout.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        dataViewModel.movies.observe(viewLifecycleOwner) {
            Loading.dismiss()
            updateMaterialToolbarTitle()
            it?.let {
                layoutViewModel.createLayout(it)
            }
        }

        dataViewModel.movieDetails.observe(viewLifecycleOwner) {
            Loading.dismiss()
            it?.let { showDetails() }
        }

        dataViewModel.error.observe(viewLifecycleOwner) {
            Loading.dismiss()
            it?.let {
                dataViewModel.cleanLoadedData()
                adapter.update(
                    listOf(MoviesUIModel.ErrorItem(textMessage = it))
                )
            }
        }
    }

    override fun onItemClicked(item: ItemModel) {
        when (item) {
            is MoviesUIModel.MovieItem -> {
                Loading.show(requireContext())
                dataViewModel.getMovieDetails(item.movie.imdbID)
            }
        }
    }

    private fun setSearchView() {
        val searchView =
            binding.toolbar.menu.findItem(R.id.item_search).actionView as SearchView

        searchView.queryHint = getString(R.string.search_view_hint_text)

        searchView.setTextColor(R.color.colorToolbarText)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { dataViewModel.getMoviesByTerm(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ((newText?.length ?: 0) >= 1) {
                    newText?.let { dataViewModel.getMoviesByTerm(it) }
                }
                return true
            }
        })
    }

    private fun SearchView.setTextColor(@ColorRes color: Int) {
        val searchTextView =
            this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        searchTextView.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
    }

    private fun showDetails() {
        lastDialog?.dismiss()

        val dialog = getBuilder().create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.yellowPrimary))

        lastDialog = dialog
    }

    private fun getBuilder(): AlertDialog.Builder {
        val data = dataViewModel.movieDetails.value

        val builder = AlertDialog.Builder(requireContext())

        val dialogBinding = AlertDialogMovieDetailsBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)

        val imageUrl = SpannableString(getString(R.string.view_image))
        imageUrl.setSpan(
            URLSpan(data?.poster),
            0,
            imageUrl.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        dialogBinding.apply {
            Picasso.get()
                .load(data?.poster)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .resize(200, 200)
                .centerCrop()
                .into(ivPoster)

            tvMovieTitle.text = getString(
                R.string.movie_title,
                data?.title ?: "",
                data?.year ?: ""
            )

            tvGenre.text = getString(
                R.string.genre_and_country,
                data?.genre ?: "",
                data?.country ?: ""
            )

            val imdbRating = SpannableStringBuilder()
                .bold { append(getString(R.string.imdb_rating_label) + " ") }
                .append(data?.imdbRating ?: "")

            tvIMDBRating.text = imdbRating

            data?.poster?.let {
                tvPosterUrl.visibility = View.VISIBLE
                tvPosterUrl.text = imageUrl
                tvPosterUrl.movementMethod = LinkMovementMethod.getInstance()
                tvPosterUrl.removeLinksUnderline()
            }
        }

        builder.setPositiveButton(getString(R.string.btn_ok)) { _, _ -> }
        builder.setCancelable(false)

        return builder
    }

    private fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(requireContext(), color)
    }
}