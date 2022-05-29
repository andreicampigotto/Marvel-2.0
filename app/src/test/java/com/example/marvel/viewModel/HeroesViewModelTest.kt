package com.example.marvel.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvel.model.Hero
import com.example.marvel.repository.MarvelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HeroesViewModelTest{

    @get:Rule
    var instantExecutingRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    var mainCoroutineRule = TestCoroutineDispatcher()

    lateinit var viewModel: HeroesViewModel

    @Mock
    lateinit var repository: MarvelRepository
    @Mock
    lateinit var heroesList:List<Hero>

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainCoroutineRule)
        viewModel = HeroesViewModel(repository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `return the list of hero from api`() =
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.fetchHeroes(0, true)).thenReturn(heroesList)
            viewModel.fetchHeroes(0, true)
            Assert.assertEquals(viewModel.heroes.value, heroesList)
        }

    @Test
    fun `return the list of hero from db`() =
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.fetchHeroes(0, false)).thenReturn(heroesList)
            viewModel.fetchHeroes(0, false)
            Assert.assertEquals(viewModel.heroes.value, heroesList)
        }

    @Test
    fun `return the list of hero from api by name`() =
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.fetchHeroesByName(0, "Spider", true)).thenReturn(heroesList)
            viewModel.fetchHeroesByName(0, "Spider", true)
            Assert.assertEquals(viewModel.heroes.value, null)
        }

    @Test
    fun `return the list of hero from db by name`() =
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.fetchHeroesByName(0, "Spider", false)).thenReturn(heroesList)
            viewModel.fetchHeroesByName(0, "Spider", false)
            Assert.assertEquals(viewModel.heroes.value, null)
        }
}