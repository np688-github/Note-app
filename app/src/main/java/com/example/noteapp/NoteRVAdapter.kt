package com.example.noteapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class NoteRVAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
):RecyclerView.Adapter<NoteRVAdapter.ViewHolder>(), Parcelable {
    private var allNotes = ArrayList<Note>()

    constructor(parcel: Parcel) : this(
        TODO("context"),
        TODO("noteClickInterface"),
        TODO("noteClickDeleteInterface")
    ) {

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.tv_title)
        val timeTV: TextView = itemView.findViewById(R.id.tv_timeStamp)
        val deleteIV: ImageView = itemView.findViewById(R.id.IVDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = allNotes[position].noteTitle
        holder.timeTV.text = "Last Updated :" + allNotes[position].timeStamp

        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteRVAdapter> {
        override fun createFromParcel(parcel: Parcel): NoteRVAdapter {
            return NoteRVAdapter(parcel)
        }

        override fun newArray(size: Int): Array<NoteRVAdapter?> {
            return arrayOfNulls(size)
        }
    }
}
interface NoteClickInterface {
    fun onNoteClick(note: Note)
}
interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}
