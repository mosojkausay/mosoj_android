package com.gotasoft.mosojkausay_mobile.view.mess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotasoft.mosojkausay_mobile.R
import com.gotasoft.mosojkausay_mobile.databinding.ItemMessBinding
import com.gotasoft.mosojkausay_mobile.model.entities.response.MensajeResponse

class MessAdapter(var arrayMess: ArrayList<MensajeResponse> = arrayListOf()): RecyclerView.Adapter<MessAdapter.MessViewHolder>() {
    inner class MessViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMessBinding.bind(itemView)
        fun bind(mensaje: MensajeResponse) {
            with(binding) {
                textAsuntoItemMess.text = mensaje.asunto
                textContenidoItemMess.text = mensaje.contenido
                val emisor = "${mensaje.emisor} el ${mensaje.fecha}"
                textEmisorItemMess.text = emisor
                fabDestItemMess.hide()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessViewHolder =
        MessViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mess, parent, false)
        )

    override fun onBindViewHolder(holder: MessViewHolder, position: Int) {
        val mess = arrayMess[position]
        holder.bind(mess)
    }

    override fun getItemCount(): Int  = arrayMess.size
}