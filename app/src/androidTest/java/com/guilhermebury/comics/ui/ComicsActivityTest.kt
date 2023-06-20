package com.guilhermebury.comics.ui

import android.content.Intent
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.guilhermebury.comics.BaseTest
import com.guilhermebury.comics.domain.mapper.toDomain
import com.guilhermebury.comics.domain.response.ItemModelResponse
import com.guilhermebury.comics.error.CODE_ERROR_DEFAULT
import com.guilhermebury.comics.error.ComicsError
import com.guilhermebury.comics.error.MESSAGE_ERROR_DEFAULT
import com.guilhermebury.comics.helper.fromJson
import com.guilhermebury.comics.repository.ComicsRepository
import com.guilhermebury.comics.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Suppress("ClassName")
class ComicsActivityTest : BaseTest() {

    private val repository = mockk<ComicsRepository>()
    val intent = Intent(getApplicationContext(), ComicsActivity::class.java)

    override val testDependenciesModules = module {
        factory { repository }
    }.toList()

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class given_a_getComics_request_when_response_is_success {
        @BeforeEach
        fun setup() {
            coEvery { repository.getComics() } answers {
                Result.success(fromJson<ItemModelResponse>("mock/item_model_success.json").toDomain())
            }
        }

        @Test
        fun then_check_if_screen_is_correctly_displayed() {
            onLaunch(intent) checkIf {
                comicsActivityOnSuccessIsCorrect()
            }
        }
    }

    @Nested
    inner class given_a_getComics_request_when_response_is_failure {
        @BeforeEach
        fun setup() {
            coEvery { repository.getComics() } answers {
                Result.failure(fromJson("mock/default_error.json"))
            }
        }

        @Test
        fun then_check_if_screen_is_correctly_displayed() {
            onLaunch(intent) checkIf {
                comicsActivityOnFailureIsCorrect()
            }
        }
    }
}