import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chatgpttalk.R
import com.github.library.bubbleview.BubbleTextView

class AdapterList(private val context: Context, private val data: ArrayList<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            if (position % 2 == 0) {
                view = inflater.inflate(R.layout.user_messages, parent, false)
            } else {
                view = inflater.inflate(R.layout.gpt_messages, parent, false)
            }
        }
        var textView : BubbleTextView?
        val text = getItem(position)
        if (position % 2 == 0)
            textView = view!!.findViewById(R.id.message_user)
        else
            textView = view!!.findViewById(R.id.message_gpt)

        if (textView != null)
            textView.text = text

        return view
    }

    override fun getItem(position: Int): String = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = data.size
}