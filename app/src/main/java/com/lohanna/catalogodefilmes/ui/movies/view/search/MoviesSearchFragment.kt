package com.lohanna.catalogodefilmes.ui.movies.view.search

import android.content.DialogInterface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.lohanna.catalogodefilmes.ui.common.util.ViewUtils.removeLinksUnderline
import com.lohanna.catalogodefilmes.ui.component.Loading
import com.lohanna.catalogodefilmes.ui.movies.view.search.uiModel.MoviesUIModel
import com.lohanna.catalogodefilmes.ui.movies.viewModel.MoviesSearchViewModel
import com.lohanna.catalogodefilmes.ui.movies.viewModel.MoviesSearchViewModelImp
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesSearchFragment : Fragment(), ItemClickListener {
    private lateinit var binding: FragmentSearchBaseLayoutBinding
    private lateinit var adapter: MovieSearchAdapter

    private val layoutViewModel: MovieSearchLayoutViewModel by viewModels<MovieSearchLayoutViewModelImp>()
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
        setMaterialToolbarTitle()
        setAdapter()
        setObserver()
        searchView()
    }

    private fun getData() {
        Loading.show(requireContext())
        dataViewModel.getMoviesByTerm("Duna")
    }

    private fun setMaterialToolbarTitle() {
        binding.toolbar.title =
            getString(R.string.toolbar_title, dataViewModel.movies.value?.size ?: 0)
    }

    private fun setAdapter() {
        adapter = MovieSearchAdapter()
        adapter.listener = this
        binding.rvContainer.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        binding.rvContainer.adapter = adapter
    }

    private fun setObserver() {
        layoutViewModel.layout.observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        dataViewModel.movies.observe(viewLifecycleOwner) {
            Loading.dismiss()
            it?.let {
                setMaterialToolbarTitle()
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
                adapter.update(
                    listOf(MoviesUIModel.ErrorItem(textMessage = it))
                )
            }
        }
    }

    private fun searchView() {
        val searchView =
            binding.toolbar.menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // A fazer
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // A fazer
                return true
            }

        })
    }

    override fun onItemClicked(item: ItemModel) {
        when (item) {
            is MoviesUIModel.MovieItem -> {
                Loading.show(requireContext())
                dataViewModel.getMovieDetails(item.movie.imdbID)
            }
        }
    }

    private fun showDetails() {
        val data = dataViewModel.movieDetails.value

        val builder = AlertDialog.Builder(requireContext())

        val dialogBinding = AlertDialogMovieDetailsBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)

        val imdbRating = SpannableStringBuilder()
            .bold { append("IMDB Rating: ") }
            .append(data?.imdbRating ?: "")

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

            tvIMDBRating.text = imdbRating

            data?.poster?.let {
                tvPosterUrl.visibility = View.VISIBLE
                tvPosterUrl.text = imageUrl
                tvPosterUrl.movementMethod = LinkMovementMethod.getInstance()
                tvPosterUrl.removeLinksUnderline()
            }
        }

        builder.setPositiveButton("OK") { _, _ -> }
        builder.setCancelable(false)

        lastDialog?.dismiss()

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_primary))

        lastDialog = dialog
    }
}