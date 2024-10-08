package com.gotasoft.mosojkausay_mobile.view.noticia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.model.entities.response.NoticiaPublicadaResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.NoticiaShowRes
import com.gotasoft.mosojkausay_mobile.repositories.NoticiaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NoticiaViewModel(private val noticiaRepository: NoticiaRepository = NoticiaRepository()): ViewModel() {
    private val _noticias: MutableStateFlow<StateData<ArrayList<NoticiaPublicadaResponse>>> = MutableStateFlow(StateData.None)
    val noticias: StateFlow<StateData<ArrayList<NoticiaPublicadaResponse>>> get() = _noticias

    fun getNoticias(value: String = "",
                    items: Int = 10,
                    page: Int = 1) {
        _noticias.value = StateData.Loading
        viewModelScope.launch {
            noticiaRepository.getNoticias(value, items, page)
                .catch { _noticias.value = StateData.Error(it) }
                .collect { _noticias.value = StateData.Success(it) }
        }
    }

    private val _notShow: MutableStateFlow<StateData<NoticiaShowRes>> = MutableStateFlow(StateData.None)
    val notShow: StateFlow<StateData<NoticiaShowRes>> get() = _notShow

    fun showNoticia(id: Int) {
        _notShow.value = StateData.Loading
        viewModelScope.launch {
            noticiaRepository.showNoticia( id)
                .catch { _notShow.value = StateData.Error(it) }
                .collect { _notShow.value = StateData.Success(it) }

        }
    }
}