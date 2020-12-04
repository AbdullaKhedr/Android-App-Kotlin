package qu.cmps312.lingosnacks.ui.games

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.leader_board_fragment.*
import kotlinx.coroutines.launch
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel

class LeaderBoardFragment : Fragment(R.layout.leader_board_fragment) {
    private val packageViewModel by activityViewModels<PackageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leaderBoardRv.apply {
            lifecycleScope.launch {
                adapter = LeaderBoardAdapter(packageViewModel.getLeaderBoard())
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}