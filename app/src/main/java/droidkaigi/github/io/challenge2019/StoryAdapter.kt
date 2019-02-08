package droidkaigi.github.io.challenge2019

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.databinding.ItemStoryBinding
import droidkaigi.github.io.challenge2019.domain.mapper.StoryMapper


class StoryAdapter(
    var stories: MutableList<Item?>,
    private val onClickItem: ((Item) -> Unit)? = null,
    private val onClickMenuItem: ((Item, Int) -> Unit)? = null,
    var alreadyReadStories: Set<String>
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = stories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stories[position]

        if (item != null) {
            holder.binding.alreadyRead = false
            alreadyReadStories.forEach {id ->
                if (id.toLong() == item.id) {
                    holder.binding.alreadyRead = true
                }
            }
            holder.binding.story = StoryMapper().translate(item)
            holder.binding.root.setOnClickListener {
                onClickItem?.invoke(item)
            }
            holder.binding.menuButton.setOnClickListener {
                val popupMenu = PopupMenu(holder.binding.menuButton.context, holder.binding.menuButton)
                popupMenu.inflate(R.menu.story_menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    val menuItemId = menuItem.itemId
                    when (menuItemId) {
                        R.id.copy_url,
                        R.id.refresh -> {
                            onClickMenuItem?.invoke(item, menuItemId)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        } else {
            holder.binding.story = null
            holder.binding.root.setOnClickListener(null)
            holder.binding.menuButton.setOnClickListener(null)
        }
    }
}