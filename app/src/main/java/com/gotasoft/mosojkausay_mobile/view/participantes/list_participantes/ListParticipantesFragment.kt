package com.gotasoft.mosojkausay_mobile.view.participantes.list_participantes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.estrelladelsur.apptecnico.map.MapDialog
import com.gotasoft.mosojkausay_mobile.StateData
import com.gotasoft.mosojkausay_mobile.TODOS
import com.gotasoft.mosojkausay_mobile.databinding.FragmentListParticipantesBinding
import com.gotasoft.mosojkausay_mobile.utils.PagListener
import com.gotasoft.mosojkausay_mobile.view.participantes.EditParticipante.EditParticipanteActivity
import com.gotasoft.mosojkausay_mobile.view.participantes.perfil_participante.PerfilParticipanteActivity
import com.gotasoft.mosojkausay_mobile.view.villa.VillaDialog

class ListParticipantesFragment: Fragment() {

    private val viewModel: ListParticipantesViewModel by viewModels()
    private var adapter: ListParticipantesAdapter? = null
    companion object {
        val TAG = ListParticipantesFragment::class.java.name
        private const val FROM = "FROM"
        private const val TO = "TO"
        private const val VILLA = "VILLA"
        private const val VALUE = "VALUE"
        private const val PAGE = "PAGE"
        private const val POS = "POS"
    }

    private var from = 0
    private var to = 100
    private var villa = ""
    private var value = ""
    private var isLoad: Boolean = false
    private var isLast: Boolean = false
    private var pag = 1
    private var positionRecycler: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentListParticipantesBinding.inflate(inflater, container, false)

        adapter = ListParticipantesAdapter(loc = {
            MapDialog.newInstance(it.latitud?:"", it.longitud?:"", it.nombre_completo?:"")
                .show(childFragmentManager, MapDialog.TAG)
        }, edit = {
            startActivity(
                Intent(requireContext(), EditParticipanteActivity::class.java)
                    .putExtra(EditParticipanteActivity.PART, it)
            )
        }, marcar = {
            val ref = it.referencia_familiar_telefono ?: ""
            val pad = it.padre_telefono ?: ""
            val mad = it.madre_telefono ?: ""
            ContactoDialog.newInstance(ref = ref, padre = pad, madre = mad)
                .show(childFragmentManager, ContactoDialog.TAG)
        }, perfil = {
            startActivity(
                Intent(requireContext(), PerfilParticipanteActivity::class.java)
                    .putExtra(PerfilParticipanteActivity.PART, it)
            )
        })
        val lm = LinearLayoutManager(requireContext())
        bind.recyclerParticipante.layoutManager = lm
        bind.recyclerParticipante.adapter = adapter
        val villaDialog = VillaDialog.newInstance {
            bind.textVillas.text = it.village
            changeData(bind)
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
        }
        bind.recyclerParticipante.addOnScrollListener(object : PagListener(lm) {
            override fun loadMoreItems() {
                pag++
                isLoad = true
                viewModel.getParticipantes(key = getKey(value), value = value,
                    village = villa,
                    from = from, to = to, page = pag
                )
            }

            override val isLastPage: Boolean
                get() = isLast
            override val isLoading: Boolean
                get() = isLoad

        })
        with(bind.textVillas) {
            text = TODOS
            setOnClickListener { villaDialog.show(childFragmentManager, VillaDialog.TAG) }
        }
        bind.imageCc.setOnClickListener {
            changeData(bind)
            from = 0
            to = 5
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
            changeColor(Edades.CC, bind)
        }
        bind.imageNsp.setOnClickListener {
            changeData(bind)
            from = 6
            to = 11
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
            changeColor(Edades.NSP, bind)
        }
        bind.imageMqmc.setOnClickListener {
            changeData(bind)
            from = 12
            to = 15
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
            changeColor(Edades.MQMC, bind)
        }
        bind.imagePacto.setOnClickListener {
            changeData(bind)
            from = 16
            to = 24
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
            changeColor(Edades.PACTO, bind)
        }
        bind.imagePatrocinio.setOnClickListener {
            changeData(bind)
            from = 0
            to = 100
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
            changeColor(Edades.PATROCINIO, bind)
        }
        bind.fabBuscarParticipante.setOnClickListener {
            changeData(bind)
            viewModel.getParticipantes(key = getKey(value), value = value,
                village = villa,
                from = from, to = to
            )
        }
        flows()
        changeColor(Edades.PATROCINIO, bind)

        //viewModel.getParticipantes(from = from, to = to)
        return bind.root
    }

    override fun onResume() {
        super.onResume()
        adapter?.arrayParticipantes?.clear()
        adapter?.notifyDataSetChanged()
        viewModel.getParticipantes(
            key = getKey(value), value = value,
            village = villa,
            from = from, to = to
        )
    }
    private fun flows() {
        lifecycleScope.launchWhenStarted {
            viewModel.listParticipantesState.collect {
                isLoad = false
                when(it) {
                    is StateData.Success -> {
                        val ini = adapter?.arrayParticipantes?.size ?: 0
                        val fin = ini + it.data.participantes.size
                        adapter?.arrayParticipantes?.addAll(it.data.participantes)
                        adapter?.notifyItemRangeInserted(ini, fin)
                        isLast = it.data.participantes.isEmpty()
                    }
                    is StateData.Error -> {

                    }
                    StateData.Loading -> {

                    }
                    StateData.None -> {

                    }
                }
            }
        }
    }
    private fun changeData(bind: FragmentListParticipantesBinding) {
        val auxVilla = bind.textVillas.text.toString()
        villa = if(auxVilla == TODOS) "" else auxVilla
        value = bind.editBuscarParticipante.text.toString()
        adapter?.arrayParticipantes?.clear()
        adapter?.notifyDataSetChanged()
    }

    private fun changeColor(edades: Edades, bind: FragmentListParticipantesBinding) {
        val color: Int
        val colorDark: Int
        val colorBackground: Int
        when(edades) {
            Edades.CC -> {
                color = Color.parseColor("#FF9000")
                colorDark = Color.parseColor("#D17804")
                colorBackground = Color.parseColor("#FFE8CA")
            }
            Edades.MQMC -> {
                color = Color.parseColor("#87BF36")
                colorDark = Color.parseColor("#608727")
                colorBackground = Color.parseColor("#CDDCB8")
            }
            Edades.NSP -> {
                color = Color.parseColor("#6E2673")
                colorDark = Color.parseColor("#511655")
                colorBackground = Color.parseColor("#B39DB5")
            }
            Edades.PACTO -> {
                color = Color.parseColor("#207BBC")
                colorDark = Color.parseColor("#185F91")
                colorBackground = Color.parseColor("#99B1C3")
            }
            Edades.PATROCINIO -> {
                color = Color.parseColor("#C60917")
                colorDark = Color.parseColor("#980813")
                colorBackground = Color.parseColor("#DDB9BB")
            }
        }
        with(requireActivity() as AppCompatActivity) {
            window.statusBarColor = colorDark
            supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        }
        bind.root.setBackgroundColor(colorBackground)

    }
    enum class Edades {
        CC, MQMC, NSP, PACTO, PATROCINIO
    }

    private fun getKey(value: String): String {
        return if(value.isNotEmpty()) {
            try {
                val aux = value.toLong()
                "child_number"
            } catch (ex: Exception) {
                "nombre_completo"
            }
        } else {
            "nombre_completo"
        }
    }
}