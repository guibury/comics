package com.guilhermebury.comics.viewmodel

import androidx.lifecycle.Observer
import com.guilhermebury.comics.InstantExecutorExtension
import com.guilhermebury.comics.domain.mapper.toDomain
import com.guilhermebury.comics.domain.response.ItemModelResponse
import com.guilhermebury.comics.helper.fromJson
import com.guilhermebury.comics.repository.ComicsRepository
import com.guilhermebury.comics.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@Suppress("ClassName")
@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExperimentalCoroutinesApi
internal class ComicsViewModelTest {

    private val repository = mockk<ComicsRepository>()
    private val viewModel by lazy {
        ComicsViewModel(repository)
    }
    private val loadingObserver: Observer<Boolean> = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModel.isLoading.observeForever(loadingObserver)
    }

    @AfterEach
    fun tearDown() {
        viewModel.isLoading.removeObserver(loadingObserver)
        clearAllMocks()
    }

    @Nested
    inner class `GIVEN a getComics request WHEN response is success` {

        @BeforeEach
        fun setup() {
            coEvery { repository.getComics() } answers {
                Result.success(fromJson<ItemModelResponse>("mock/item_model_success.json").toDomain())
            }
        }

        @Test
        fun `THEN comicsList should be filled`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }

            coVerify(exactly = 1) { repository.getComics() }
            assertEquals("Spider-Man 01", viewModel.comicsList.value?.get(0)?.title ?: "")
            assertEquals("First friendly neighborhood comic", viewModel.comicsList.value?.get(0)?.description ?: "")
            assertEquals("http://images/spider_man.jpg", viewModel.comicsList.value?.get(0)?.image ?: "")

            assertEquals("Hulk 01", viewModel.comicsList.value?.get(1)?.title ?: "")
            assertEquals("First smash comic", viewModel.comicsList.value?.get(1)?.description ?: "")
            assertEquals("http://images/hulk.jpg", viewModel.comicsList.value?.get(1)?.image ?: "")

            assertEquals("Captain America 01", viewModel.comicsList.value?.get(2)?.title ?: "")
            assertEquals("First Avenger comic", viewModel.comicsList.value?.get(2)?.description ?: "")
            assertEquals("http://images/captain_america.jpg", viewModel.comicsList.value?.get(2)?.image ?: "")
        }

        @Test
        fun `THEN comicsVisibility should be true`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }
            coVerify(exactly = 1) { repository.getComics() }
            assertTrue(viewModel.comicsVisibility.value ?: false)
        }

        @Test
        fun `THEN errorText should not be filled`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }
            coVerify(exactly = 1) { repository.getComics() }
            assertNull(viewModel.errorText.value)
        }

        @Test
        fun `THEN errorVisibility should be false`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }
            coVerify(exactly = 1) { repository.getComics() }
            assertFalse(viewModel.errorVisibility.value ?: true)
        }
    }

    @Nested
    inner class `GIVEN a getComics request WHEN response is failure` {

        @BeforeEach
        fun setup() {
            coEvery { repository.getComics() } answers {
                Result.failure(fromJson("mock/default_error.json"))
            }
        }

        @Test
        fun `THEN comicsVisibility should be false`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }
            coVerify(exactly = 1) { repository.getComics() }
            assertFalse(viewModel.comicsVisibility.value ?: true)
        }

        @Test
        fun `THEN errorText should be filled`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }
            coVerify(exactly = 1) { repository.getComics() }
            assertEquals("No internet connection", viewModel.errorText.value)
        }

        @Test
        fun `THEN errorVisibility should be true`() {
            viewModel.getComics()

            verifyOrder {
                loadingObserver.onChanged(true)
                loadingObserver.onChanged(false)
            }
            coVerify(exactly = 1) { repository.getComics() }
            assertTrue(viewModel.errorVisibility.value ?: false)
        }
    }
}