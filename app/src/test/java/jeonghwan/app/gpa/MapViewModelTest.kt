
package jeonghwan.app.gpa

import io.mockk.coEvery
import io.mockk.mockk
import jeonghwan.app.entity.GenderType
import jeonghwan.app.entity.GpGeoPoint
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.entity.TombEntity
import jeonghwan.app.gpa.ui.screen.navi.map.MapViewModel
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.di.usecase.TombUseCaseInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MapViewModelTest {
    private lateinit var viewModel: MapViewModel
    private val personUseCase: PersonUseCaseInterface = mockk()
    private val tombUseCase: TombUseCaseInterface = mockk()
    val dummyTombEntity =
        TombEntity(
            key = 1,
            name = "Doe Tomb",
            location =
                GpGeoPoint(
                    latitude = 37.5665,
                    longitude = 126.9782,
                ),
        )

    // 더미 PersonEntity 생성
    val dummyPersonEntity =
        PersonEntity(
            key = 1,
            spouse = null,
            generator = 1,
            genderType = GenderType.Female,
            alive = true,
            clan = "Doe Clan",
            etc = "Some additional info",
            family = "Doe Family",
            name = "John Doe",
            tombKey = 1,
            dateDeath = null,
            father = null,
            mather = null,
        )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MapViewModel(personUseCase, tombUseCase)
    }

    @Test
    fun `loadTombData should update uiState on success`() =
        runTest {
            // Mocking the use case
            coEvery { tombUseCase.getTombAll() } returns Result.success(listOf(dummyTombEntity))
            coEvery { personUseCase.getPersonAll() } returns Result.success(listOf(dummyPersonEntity))

            // Call the function
            viewModel.loadTombData()

            // Verify the state is updated
            assert(!viewModel.uiState.value.isLoading)
            assert(viewModel.uiState.value.errorMessage == null)
            assert(viewModel.uiState.value.tombs.isNotEmpty()) // Tombs가 비어있지 않아야 함
        }

    @Test
    fun `loadTombData should update errorMessage on failure`() =
        runTest {
            // Mocking the use case to return failure
            coEvery { personUseCase.getPersonAll() } returns Result.success(listOf(dummyPersonEntity))
            coEvery { tombUseCase.getTombAll() } returns Result.failure(Exception("Network error"))

            // Call the function
            viewModel.loadTombData()

            // Verify the state is updated
            assert(!viewModel.uiState.value.isLoading)
            assert(viewModel.uiState.value.errorMessage == "Network error") // 오류 메시지가 설정되어야 함
        }

    @Test
    fun `toggleTombPopupWindowVisibility should toggle visibility`() {
        coEvery { tombUseCase.getTombAll() } returns Result.success(listOf(dummyTombEntity))
        coEvery { personUseCase.getPersonAll() } returns Result.success(listOf(dummyPersonEntity))

        // Call the function
        viewModel.loadTombData()

        // 초기 상태 설정
        viewModel.toggleTombPopupWindowVisibility(tombKey = 1)

        // Verify the visibility is toggled
        // 상태 검증 로직 추가
        assert(viewModel.uiState.value.tombs.any { it.tomb.key == 1 && it.isWindowVisible }) // 해당 Tomb의 isWindowVisible이 true여야 함
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }
}
