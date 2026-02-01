import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.adapter.AgeRightNowVH
import com.example.agecalculator.adapter.BirthdayCountdownVH
import com.example.agecalculator.adapter.BornDayVH
import com.example.agecalculator.adapter.HoroscopeVH
import com.example.agecalculator.adapter.NextBirthdayVH
import com.example.agecalculator.databinding.*
import com.example.agecalculator.model.AgeCard


class AgeCardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<AgeCard>()

    fun submitList(list: List<AgeCard>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is AgeCard.AgeRightNow -> 0
            is AgeCard.BornDay -> 1
            is AgeCard.BirthdayCountdown -> 2
            is AgeCard.NextBirthday -> 3
            is AgeCard.Horoscope -> 4
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            0 -> AgeRightNowVH(
                CardAgeNowBinding.inflate(inflater, parent, false)
            )

            1 -> BornDayVH(
                CardBornDayBinding.inflate(inflater, parent, false)
            )

            2 -> BirthdayCountdownVH(
                CardBirthdayCountdownBinding.inflate(inflater, parent, false)
            )

            3 -> NextBirthdayVH(
                CardNextBirthdayBinding.inflate(inflater, parent, false)
            )

            else -> HoroscopeVH(
                HoroscopeBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is AgeCard.AgeRightNow -> (holder as AgeRightNowVH).bind(item)
            is AgeCard.BornDay -> (holder as BornDayVH).bind(item)
            is AgeCard.BirthdayCountdown -> (holder as BirthdayCountdownVH).bind(item)
            is AgeCard.NextBirthday -> (holder as NextBirthdayVH).bind(item)
            is AgeCard.Horoscope -> (holder as HoroscopeVH).bind(item)
        }
    }

    override fun getItemCount() = items.size
}
