package com.pawan.propertyapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pawan.propertyapplication.databinding.ViewHolderMainBinding
import com.pawan.propertyapplication.network.model.Exclusion
import com.pawan.propertyapplication.network.model.Facility

class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var listOfExclusion: Any = Any()
    private var facilityList = listOf<Facility>()
    private var storeSelectedKeyValue = mutableMapOf<String, Int>()

    inner class MyViewHolder(val viewDataBinding: ViewHolderMainBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MyViewHolder {
        val binding =
            ViewHolderMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.MyViewHolder, position: Int) {
        val binding = holder.viewDataBinding
        val item = this.facilityList[position]
        binding.tvPropertyName.text = item.name

        for (i in 0..item.options.size - 1) {
            val context = holder.viewDataBinding.root.context
            val dynamicRadioButton = RadioButton(context)
            dynamicRadioButton.id = item.options[i].id.toInt()
            dynamicRadioButton.text = item.options[i].name

            var resourseId = "@drawable/" + item.options[i].icon
            if (item.options[i].icon.equals("no-room")) {
                resourseId = "@drawable/no_room"
            }
            val res = context.resources.getIdentifier(
                resourseId,
                null, context.packageName
            )
            val drawable = ContextCompat.getDrawable(context, res)
            dynamicRadioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            dynamicRadioButton.compoundDrawablePadding = 20
            binding.rbgPropertyType.addView(dynamicRadioButton)

            dynamicRadioButton.setOnClickListener {

                val selectedItem = this.facilityList[position]
                val selectedFacilityId = selectedItem.facility_id
                val selectedOptionId = dynamicRadioButton.id

                storeSelectedKeyValue.put(selectedFacilityId, selectedOptionId)
                Toast.makeText(
                    context,
                    "Facility Id=" + selectedFacilityId + " Option Id=" + selectedOptionId,
                    Toast.LENGTH_LONG
                ).show()

                val lst = listOf(listOfExclusion)[0] as ArrayList<java.util.ArrayList<Exclusion>>
                for (index in 0 until lst.size) {
                    val listPairToCheck = lst[index]
                    val firstPair = listPairToCheck[0]
                    val checkPair = listPairToCheck[1]


                    if (storeSelectedKeyValue.get(selectedFacilityId) == checkPair.options_id.toInt()
                        && selectedFacilityId.toInt() == checkPair.facility_id.toInt()
                        && storeSelectedKeyValue.containsValue(firstPair.options_id.toInt())
                        && storeSelectedKeyValue.containsKey(firstPair.facility_id)
                    ) {
                        Toast.makeText(context, "You can't select", Toast.LENGTH_SHORT).show()
                        dynamicRadioButton.isChecked = false
                        storeSelectedKeyValue.remove(selectedFacilityId)
                    }
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return this.facilityList.size
    }

    fun setData(
        newList: ArrayList<Facility>,
        exclusionList: ArrayList<java.util.ArrayList<Exclusion>>
    ) {
        val toDoDiffUtil = DiffUtils(facilityList, newList)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.facilityList = newList
        this.listOfExclusion = exclusionList
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}

class DiffUtils(
    private val oldList: List<Facility>,
    private val newList: List<Facility>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}