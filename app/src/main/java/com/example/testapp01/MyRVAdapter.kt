package com.example.testapp01


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp01.db.utils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MyRVAdapter(private val ctxt : Fragment, private var data:MutableList<Drink>):RecyclerView.Adapter<MyRVAdapter.MyViewHolder>(){

    lateinit var anotherViewHolder:MyViewHolder

    /*###############################################
    * -----         M A N D A T O R Y          -----*
    * =============================================*/
    //-----------------------------------------------
    //-----------------------------------------------
    class MyViewHolder(val view: View, // default constructor, viewholder elements initialized
                              val name:TextView=view.findViewById(R.id.drinkName),
                              val desc:TextView=view.findViewById(R.id.drinkDesc),
                              val del:ImageButton=view.findViewById(R.id.drinkDel),
                              val edit:ImageButton=view.findViewById(R.id.drinkEdit),
                              val chkBx:CheckBox=view.findViewById(R.id.drinkChkBx)
                            ):RecyclerView.ViewHolder(view)
    //-----------------------------------------------
    // Create a new view, which defines the UI of the list item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder{

        val viewHolder= MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false))

        if (ctxt.javaClass==FragmentFav::class.java){
            Log.d("favFrag", "onCreateViewHolder: it holds tru")
            viewHolder.edit.visibility=(View.INVISIBLE)
            viewHolder.del.visibility=(View.INVISIBLE)
        }

        anotherViewHolder=viewHolder

        return viewHolder
    }
    //-----------------------------------------------
    // actual interactions with view; ie setting and changing of the view / listeners
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (id, name, descr, fav)=data[position]
        holder.name.text=name; holder.desc.text= descr
        holder.chkBx.isChecked=fav
        handleListeners(holder, position)
    }
    //-----------------------------------------------
    override fun getItemCount()= data.size
    //-----------------------------------------------





    /*###############################################
    * -----         C U S T O M A R Y          -----*
    * =============================================*/
    fun setDrinksData(drinkData: List<Drink> ){
        data= drinkData as MutableList<Drink>
        notifyDataSetChanged()
    }
    //-----------------------------------------------





    /*###############################################
    * -----   c o n v e n i e n c e   f u n    -----*
    * =============================================*/
    //-----------------------------------------------
    //-----------------------------------------------
    private fun handleListeners(h:MyViewHolder, position: Int){
        val mainActivity=(ctxt.activity as MainActivity)
        h.view.setOnClickListener{ DialogueUtility(mainActivity, "My Drink", R.drawable.drink_icon, data[position] ).show()}
        h.del.setOnClickListener{ handleDelListener(mainActivity,position);}
        h.edit.setOnClickListener{ handleEditListener(h, mainActivity, position)  }
        handleChkBxListener(h, mainActivity, position)
    }
    //-----------------------------------------------
    private fun handleEditListener(h: MyViewHolder, mainActivity: MainActivity, position: Int) {
//        Toast.makeText(h.view.context, "Editing row "+(position+1), Toast.LENGTH_SHORT).show()
        DialogueUtility(mainActivity, data, position, "Edit Drink", R.drawable.edit_drink_icon).show()
    }
    //-----------------------------------------------
    private fun handleChkBxListener(h:MyViewHolder, mainActivity: MainActivity, position: Int){
        h.chkBx.setOnClickListener{
            if (mainActivity.drinkViewModel.favDrinks?.value?.size!! <=1 && ctxt.javaClass==FragmentFav::class.java) {
                mainActivity.tvBuffer.text="You haven't marked any favourite drinks. " +
                "\nCheck out the all drinks tab to choose some favourites !"
                mainActivity.tvBuffer.visibility=View.VISIBLE
            }
//            Log.d("t1", "chkbx enterd:+ ${h.chkBx.isChecked}")
            data[position].fav=h.chkBx.isChecked
            GlobalScope.launch {
                mainActivity.drinkViewModel.upd(data[position])
            }
        }
    }
    //-----------------------------------------------
    private fun handleDelListener(mainActivity: MainActivity, position: Int) {
        if (mainActivity.drinkViewModel.mAllDrinks?.value?.size!! <=1) {
            mainActivity.tvBuffer.text="You have no drinks to list here. " +
            "\nTap the button on the top right to add a new drink !"
            mainActivity.tvBuffer.visibility=View.VISIBLE
        }
        GlobalScope.launch { mainActivity.drinkViewModel.del(data[position]); }
    }
}

