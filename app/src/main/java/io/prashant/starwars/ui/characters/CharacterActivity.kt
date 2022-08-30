package io.prashant.starwars.ui.characters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.prashant.starwars.R
import io.prashant.starwars.contract.CharactersContract
import io.prashant.starwars.data.local.Character
import io.prashant.starwars.databinding.ActivityCharactersBinding
import io.prashant.starwars.ui.base.RecyclerViewMargin
import io.prashant.starwars.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import java.net.UnknownHostException
import javax.inject.Inject

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity(), CharactersContract.View {

    private lateinit var viewBinding: ActivityCharactersBinding
    private lateinit var characterPagingAdapter: CharacterPagingAdapter
    private lateinit var characterLoaderStateAdapter: CharacterLoaderStateAdapter

    @Inject
    lateinit var presenter: CharactersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupToolbar()
        setupUi()
        presenter.fetchCharacters()
    }

    private fun setupToolbar() {
        setSupportActionBar(viewBinding.appToolbar.appToolbar)
        supportActionBar?.title = getString(R.string.characters)
    }

    private fun setupUi() {
        initErrorUi()
        initCharacterListUi()
    }

    override fun showCharacters(flowCharacters: Flow<PagingData<Character>>) {
        lifecycleScope.launchWhenCreated {
            flowCharacters.distinctUntilChanged().collectLatest {
                characterPagingAdapter.submitData(it)
            }
        }
    }

    private fun initErrorUi() {
        viewBinding.btnTryAgain.setOnClickListener {
            presenter.fetchCharacters()
        }
    }

    private fun initCharacterListUi() {
        characterPagingAdapter = CharacterPagingAdapter(this::onCharacterClick)
        characterPagingAdapter.addLoadStateListener(this::onLoadStateHandleActivityUi)
        characterLoaderStateAdapter = CharacterLoaderStateAdapter { characterPagingAdapter.retry() }
        with(viewBinding) {
            rvCharacters.apply {
                adapter = characterPagingAdapter.withLoadStateFooter(characterLoaderStateAdapter)
                layoutManager = LinearLayoutManager(this@CharacterActivity)
                addItemDecoration(
                    RecyclerViewMargin(
                        resources.getDimensionPixelSize(R.dimen.item_margin),
                        1
                    )
                )
            }
        }
    }

    private fun onLoadStateHandleActivityUi(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Error) {
            val errorText = getErrorText((loadState.refresh as LoadState.Error).error)
            viewBinding.tvError.text = errorText
            viewBinding.groupError.visibility = View.VISIBLE
        } else {
            viewBinding.groupError.visibility = View.GONE
        }

        if (loadState.refresh is LoadState.Loading) {
            viewBinding.tvLoading.visibility = View.VISIBLE
        } else {
            viewBinding.tvLoading.visibility = View.GONE
        }
    }

    private fun getErrorText(e: Throwable) =
        if (e is UnknownHostException) {
            getString(R.string.please_check_your_internet_connection)
        } else {
            getString(R.string.failed_to_load_characters)
        }


    private fun onCharacterClick(character: Character?) {
        if (character != null) {
            startCharacterDetailsActivity(character)
        } else {
            Toast.makeText(this, R.string.character_details_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCharacterDetailsActivity(character: Character?) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra(Constants.IntentKey.CHARACTER_DATA, character)
        startActivity(intent)
    }


}